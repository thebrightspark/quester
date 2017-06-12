package com.mdc.quester.examples;

import com.mdc.quester.interfaces.IQuestPageTemplate;
import com.mdc.quester.interfaces.IQuestTemplate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class QuestGetStone implements IQuestTemplate<QuestGetStone> {
    @Override
    public String getName() {
        return "get_stone";
    }

    @Override
    public IQuestPageTemplate<?> getPage() {
        return new QuestModPage();
    }

    @Override
    public boolean triggered(EntityPlayer player, World world, BlockPos pos) {
        return player.inventory.hasItemStack(new ItemStack(Item.getItemFromBlock(Blocks.STONE)));
    }
}
