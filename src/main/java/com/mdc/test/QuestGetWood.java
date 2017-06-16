package com.mdc.test;

import com.mdc.quester.templates.IQuestTemplate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class QuestGetWood implements IQuestTemplate<QuestGetWood>{
    @Override
    public String getName() {
        return "Get Wood";
    }

    @Override
    public boolean triggered(EntityPlayer player, World world, BlockPos pos) {

        return player.inventory.hasItemStack(new ItemStack(Item.getItemFromBlock(Blocks.LOG)));
    }
}
