package com.mdc.test.handler;

import com.mdc.quester.Quester;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

@Mod.EventBusSubscriber
public class QuestHandler {

    public static ItemStack crafted = ItemStack.EMPTY;

    @SubscribeEvent
    public static void onCrafted(PlayerEvent.ItemCraftedEvent event){
        EntityPlayer player = event.player;
        World world = player.world;
        if(world.isRemote) return;
        ItemStack crafting = event.crafting;
        if(event.crafting.getItem().getRegistryName().equals(Blocks.PLANKS.getRegistryName())){
            crafted = crafting;
            Quester.LOGGER.info("Item being crafted: " + crafting);
        }
    }
}
