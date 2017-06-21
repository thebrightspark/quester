package com.mdc.test;

import com.mdc.quester.templates.IQuestTemplate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class QuestGetCoal implements IQuestTemplate<QuestGetCoal>{
    private static boolean isTriggered = false;

    @Override
    public String getName() {
        return "Get Coal";
    }

    public static void setTriggered(boolean triggered){
        isTriggered = triggered;
    }

    @Override
    public boolean triggered(EntityPlayer player, World world, BlockPos pos) {
        return isTriggered;
    }
}
