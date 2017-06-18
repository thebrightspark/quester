package com.mdc.test.handler;

import com.mdc.quester.Quester;
import com.mdc.test.QuestGetPlanks;
import com.mdc.test.QuestResources;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

@Mod.EventBusSubscriber
public class QuestHandler {

    @SubscribeEvent
    public static void onCrafted(PlayerEvent.ItemCraftedEvent event){
        EntityPlayer player = event.player;
        World world = player.world;
        if(world.isRemote) return;
        ItemStack crafting = event.crafting;
        if(crafting.getItem().getRegistryName().toString().equals(Blocks.PLANKS.getRegistryName().toString())){
            QuestGetPlanks.setTriggered(true);
        }
    }

    @SubscribeEvent
    public static void getResources(PlayerEvent.ItemPickupEvent event){
        EntityPlayer player = event.player;
        World world = player.world;
        if(world.isRemote) return;
        Item item = event.pickedUp.getEntityItem().getItem();
        if(item.getRegistryName().toString().equals(Blocks.STONE.getRegistryName().toString())){
            QuestResources.addResources(item);
        }else if(item.getRegistryName().toString().equals(Items.COAL.getRegistryName().toString())){
            QuestResources.addResources(item);
        }
    }
}
