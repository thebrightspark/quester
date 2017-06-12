package com.mdc.quester;

import com.mdc.quester.interfaces.IQuestPageTemplate;
import com.mdc.quester.interfaces.IQuestTemplate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class QuestGetWood implements IQuestTemplate<QuestGetWood> {

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public IQuestPageTemplate<QuestModPage> getPage() {
        return new QuestModPage();
    }

    public boolean triggered(EntityPlayer player, World world, BlockPos pos){
        return player.getHeldItemMainhand().getItem() == Item.getItemFromBlock(Blocks.LOG);
    }
}
