package net.ccbluex.liquidbounce.features.module.modules.render;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.misc.AntiBot;
import net.ccbluex.liquidbounce.features.module.modules.misc.Teams;
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.Notification;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

import java.awt.*;

@ModuleInfo(name = "EnemyWarning",description = "Warning you when theres enemy around you",category = ModuleCategory.RENDER)
public class EnemyWarning extends Module {
    private final FloatValue rangeValue = new FloatValue("Range",10,0,30);

    private final IntegerValue radiusValue = new IntegerValue("Radius",100,0,150);
    private final IntegerValue colorRValue = new IntegerValue("ColorR",255,0,255);
    private final IntegerValue colorGValue = new IntegerValue("ColorG",80,0,255);
    private final IntegerValue colorBValue = new IntegerValue("ColorB",80,0,255);
    private final FloatValue lineWidthValue = new FloatValue("LineWidth",4,0,6);

    @EventTarget
    public void onRender2D(Render2DEvent event) {
        for (Entity entity : mc.theWorld.loadedEntityList) {
            if (entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) entity;
                if (!((Teams) LiquidBounce.moduleManager.getModule(Teams.class)).isInYourTeam(player) &&
                        !LiquidBounce.fileManager.friendsConfig.isFriend(player.getGameProfile().getName()) &&
                        !AntiBot.isBot(player) && player.getDistanceToEntity(mc.thePlayer) < rangeValue.get() && player != mc.thePlayer) {
                    ScaledResolution scaledResolution = new ScaledResolution(mc);
                    RenderUtils.drawBorderedRect(scaledResolution.getScaledWidth() / 2 - radiusValue.get()
                            ,scaledResolution.getScaledHeight() / 2 - radiusValue.get(),
                            scaledResolution.getScaledWidth() / 2 + radiusValue.get(),
                            scaledResolution.getScaledHeight() / 2 + radiusValue.get(),
                            lineWidthValue.get(),new Color(colorRValue.get(),colorGValue.get(),colorBValue.get()).getRGB(),
                            new Color(0,0,0,0).getRGB());
                }
            }
        }
    }
}
