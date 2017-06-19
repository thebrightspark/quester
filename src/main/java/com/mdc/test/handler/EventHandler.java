package com.mdc.test.handler;

import com.mdc.quester.Quester;
import com.mdc.quester.capability.quest.ICapQuests;
import com.mdc.quester.quests.QuestHelper;
import com.mdc.quester.registry.QuestData;
import com.mdc.quester.templates.IQuestTemplate;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class EventHandler {
    @SubscribeEvent
    public static void breakBlock(BlockEvent.BreakEvent event){
        World world = event.getWorld();
        if(world.isRemote) return;
        Block block = event.getState().getBlock();
        if(block.getRegistryName().toString().equals(Blocks.COAL_ORE.getRegistryName().toString())){
            IQuestTemplate quest = QuestData.getQuestByName("Get Coal");
            assert quest != null;
            quest.setTriggered(true);
        }
    }

    @SubscribeEvent
    public static void killZombie(LivingDeathEvent event){
        int zombiesKilled = 0;
        if(event.getSource().getEntity() instanceof EntityPlayer) {
            EntityPlayerMP player = (EntityPlayerMP)event.getSource().getEntity();
            ICapQuests icap = QuestHelper.getQuestCapability(player);
            assert icap != null;
            if(event.getEntity() instanceof EntityZombie){
                EntityZombie zombie = (EntityZombie)event.getEntity();
                World world = zombie.world;
                if(world.isRemote) return;
                IQuestTemplate quest = QuestData.getQuestByName("Zombie Hunter");
                assert quest != null;
                if(icap.hasCompletedQuest(quest)) {
                    if (zombie.isDead) {
                        zombiesKilled++;
                        Quester.LOGGER.info("Zombies killed: " + zombiesKilled);
                        if (zombiesKilled >= 10) {
                            quest.setTriggered(true);
                        }
                    }
                }
            }
        }
    }
}
