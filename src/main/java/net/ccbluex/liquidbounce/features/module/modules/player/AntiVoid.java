package net.ccbluex.liquidbounce.features.module.modules.player;

import net.ccbluex.liquidbounce.event.*;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;

@ModuleInfo(name = "AntiVoid",description = "Anti you fall into the void or fall too long",category = ModuleCategory.PLAYER)
public class AntiVoid extends Module {
    private int fallDistance = 0;

    private final ListValue modeValue = new ListValue("Mode",new String[]{"MotionFlag"},"MotionFlag");
    private final FloatValue maxFallDistanceValue = new FloatValue("MaxFallDistance",0.6F,0.2F,3);
    private final FloatValue motionFlag_MotionYValue = new FloatValue("MotionFlag-MotionY",2,1,5);

    @EventTarget
    public void onUpdate(UpdateEvent updateEvent) {
        if (modeValue.get().equalsIgnoreCase("motionFlag")) {
            if (mc.theWorld.getBlockState(new BlockPos(
                    mc.thePlayer.posX,
                    mc.thePlayer.posY - 1,
                    mc.thePlayer.posZ
            )).getBlock() == Blocks.air) {
                fallDistance -= mc.thePlayer.motionY;
            } else {
                fallDistance = 0;
            }

            if (fallDistance > maxFallDistanceValue.get()) {
                mc.thePlayer.motionY = motionFlag_MotionYValue.get();
            }

        }
    }


}
