package com.mdc.test;

import com.mdc.quester.interfaces.IQuestTemplate;
import com.mdc.test.handler.QuestHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class QuestGetPlanks implements IQuestTemplate<QuestGetPlanks> {
    @Override
    public String getName() {
        return "Get Planks";
    }

    @Override
    public boolean triggered(EntityPlayer player, World world, BlockPos pos) {
        ItemStack wood = new ItemStack(Item.getItemFromBlock(Blocks.PLANKS));
        return QuestHandler.crafted == wood;
    }
}
