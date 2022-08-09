/*
 * LiquidBounce Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge.
 * https://github.com/CCBlueX/LiquidBounce/
 */
package net.ccbluex.liquidbounce.ui.client

import net.ccbluex.liquidbounce.LiquidBounce

import net.ccbluex.liquidbounce.ui.font.Fonts
import net.ccbluex.liquidbounce.utils.ClientUtils
import net.minecraft.client.gui.GuiButton
import net.minecraft.client.gui.GuiScreen
import net.minecraftforge.fml.client.GuiModList
import org.lwjgl.input.Keyboard
import java.awt.Color
import kotlin.concurrent.thread

class GuiCustomize(private val prevGui: GuiScreen) : GuiScreen() {

    override fun initGui() {
        buttonList.add(GuiButton(0, width / 2 - 100, height / 4 + 48, "Forge Mods"))
        buttonList.add(GuiButton(1, width / 2 - 100, height / 4 + 48 + 25, "Scripts"))
        buttonList.add(GuiButton(2, width / 2 - 100, height / 4 + 48 + 50, "Rich Presence: ${if (LiquidBounce.clientRichPresence.showRichPresenceValue) "§aON" else "§cOFF"}"))
        buttonList.add(GuiButton(4,width / 2 - 100,height / 4 + 48 + 75,"BackGround"))
        buttonList.add(GuiButton(3, width / 2 - 100, height - 40, "Back"))
    }

    override fun actionPerformed(button: GuiButton) {
        when (val id = button.id) {
            0 -> mc.displayGuiScreen(GuiModList(this))
            1 -> mc.displayGuiScreen(GuiScripts(this))
            2 -> {
                val rpc = LiquidBounce.clientRichPresence
                rpc.showRichPresenceValue = when (val state = !rpc.showRichPresenceValue) {
                    false -> {
                        rpc.shutdown()
                        changeDisplayState(id, state)
                        false
                    }
                    true -> {
                        var value = true
                        thread {
                            value = try {
                                rpc.setup()
                                true
                            } catch (throwable: Throwable) {
                                ClientUtils.getLogger().error("Failed to setup Discord RPC.", throwable)
                                false
                            }
                        }
                        changeDisplayState(id, value)
                        value
                    }
                }
            }
            3 -> mc.displayGuiScreen(prevGui)
            4 -> mc.displayGuiScreen(GuiBackground(this))
        }
    }

    private fun changeDisplayState(buttonId: Int, state: Boolean) {
        val button = buttonList[buttonId]
        val displayName = button.displayString
        button.displayString = when (state) {
            false -> displayName.replace("§aON", "§cOFF")
            true -> displayName.replace("§cOFF", "§aON")
        }
    }

    override fun drawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        drawBackground(0)

        Fonts.fontBold180.drawCenteredString("Customize", width / 2F, height / 8F + 5F, Color(65,215,255).rgb, true)

        super.drawScreen(mouseX, mouseY, partialTicks)
    }

    override fun keyTyped(typedChar: Char, keyCode: Int) {
        if (Keyboard.KEY_ESCAPE == keyCode) {
            mc.displayGuiScreen(prevGui)
            return
        }

        super.keyTyped(typedChar, keyCode)
    }
}