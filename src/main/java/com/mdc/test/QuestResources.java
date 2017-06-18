package com.mdc.test;

import com.mdc.quester.templates.IQuestTemplate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Set;

public class QuestResources implements IQuestTemplate<QuestResources> {

    private static Set<Item> stone = new HashSet<>();
    private static Set<Item> coal = new HashSet<>();

    public static void addResources(Item item){
        if(item == Item.getItemFromBlock(Blocks.STONE)){
            Item s = Item.getItemFromBlock(Blocks.STONE);
            stone.add(s);
        }else if(item == Items.COAL){
            coal.add(item);
        }
    }

    @Override
    public String getName() {
        return "Get Resources";
    }

    @Override
    public boolean triggered(EntityPlayer player, World world, BlockPos pos) {
        return ((stone.size() == 15) && (coal.size() == 15));
    }
}
