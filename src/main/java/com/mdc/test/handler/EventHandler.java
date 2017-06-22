package com.mdc.test.handler;

import com.mdc.quester.Quester;
import com.mdc.quester.capability.quest.ICapQuests;
import com.mdc.quester.quests.QuestHelper;
import com.mdc.quester.registry.QuestData;
import com.mdc.quester.templates.IQuestTemplate;
import com.mdc.test.QuestGetCoal;
import com.mdc.test.QuestZombieHunter;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@Mod.EventBusSubscriber
public class EventHandler {
    public static int zombiesKilled = 0;

    @SubscribeEvent
    public static void breakBlock(BlockEvent.BreakEvent event){
        World world = event.getWorld();
        if(world.isRemote) return;
        Block block = event.getState().getBlock();
        if(block.getRegistryName().equals(Blocks.COAL_ORE.getRegistryName())){
            QuestGetCoal.setTriggered(true);
        }
    }

    @SubscribeEvent
    public static void killZombie(LivingDeathEvent event) {
        if(event.getEntity() instanceof EntityZombie){
            EntityZombie zombie = (EntityZombie)event.getEntity();
            World world = zombie.world;
            if(world.isRemote) return;
            Entity entity = event.getSource().getEntity();
            if (entity instanceof EntityPlayer) {
                zombiesKilled++;
                Quester.LOGGER.info("Zombies killed: " + zombiesKilled);
                if (zombiesKilled >= 10) {
                    QuestZombieHunter.setTriggered(true);
                }
            }
        }
    }
}
