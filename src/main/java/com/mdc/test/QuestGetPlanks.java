package com.mdc.test;

import com.mdc.quester.templates.IQuestTemplate;
import com.mdc.test.handler.QuestHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class QuestGetPlanks implements IQuestTemplate<QuestGetPlanks> {
    private static boolean isTriggered = false;

    @Override
    public String getName() {
        return "Get Planks";
    }

    public static void setTriggered(boolean triggered){
        isTriggered = triggered;
    }

    @Override
    public boolean triggered(EntityPlayer player, World world, BlockPos pos) {
        return isTriggered;
    }
}
