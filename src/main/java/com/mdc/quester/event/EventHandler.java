package com.mdc.quester.event;

import com.mdc.quester.Quester;
import com.mdc.quester.capability.ICapability;
import com.mdc.quester.capability.quest.ICapQuests;
import com.mdc.quester.templates.IQuestTemplate;
import com.mdc.quester.player.QuesterCapability;
import com.mdc.quester.quests.QuestHelper;
import com.mdc.quester.registry.QuestData;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Iterator;

@Mod.EventBusSubscriber
public class EventHandler {

    @SubscribeEvent
    public static void addCapability(AttachCapabilitiesEvent<Entity> event){
        Entity entity = event.getObject();
        if(entity instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer)event.getObject();
            for(Capability<? extends ICapability> capability : QuesterCapability.getCapabilties()){
                ICapability icap = capability.getDefaultInstance();
                if(!player.hasCapability(QuesterCapability.QUESTS, null)){
                    event.addCapability(icap.getKey(), icap.getProvider());
                    Quester.LOGGER.info("Quest capability added!");
                }
            }
        }
    }

    @SubscribeEvent
    public static void setQuestData(EntityJoinWorldEvent event){
        World world = event.getWorld();
        if(world.isRemote) return;
        if(event.getEntity() instanceof EntityPlayer){
            EntityPlayerMP player = (EntityPlayerMP)event.getEntity();
            ICapQuests icap = QuestHelper.INSTANCE.getQuestCapability(player);
            if(icap == null) return;
            Iterator<IQuestTemplate> iterator = QuestData.INSTANCE.quests.iterator();
            while(iterator.hasNext()) {
                IQuestTemplate quest = iterator.next();
                if (!icap.hasCompletedQuest(quest)) {
                    QuestHelper.INSTANCE.setIncompleteQuest(quest, player);
                } else {
                    Iterator<IQuestTemplate> it = icap.getCompletedQuests().iterator();
                    while(it.hasNext()){
                        IQuestTemplate temp = it.next();
                        QuestData.INSTANCE.setCompletedQuest(temp);
                        Quester.LOGGER.info("Quest already completed: " + temp.getName() + " by " + player.getName());
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void tickQuests(TickEvent.PlayerTickEvent event){
        if(event.player.world.isRemote) return;
        if(event.player instanceof EntityPlayerMP) {
            EntityPlayerMP player = (EntityPlayerMP) event.player;
            World world = player.world;
            BlockPos pos = player.getPosition();
            ICapQuests icap = QuestHelper.INSTANCE.getQuestCapability(player);
            if (icap == null) return;
            Iterator<IQuestTemplate> incompletedIt = QuestData.INSTANCE.incompletedQuests.iterator();
            while (incompletedIt.hasNext()) {
                IQuestTemplate quest = incompletedIt.next();
                if (!icap.hasCompletedQuest(quest) && quest.triggered(player, world, pos)) {
                    QuestHelper.INSTANCE.setCompletedQuest(quest, player);
                    player.sendStatusMessage(new TextComponentString("Quest complete: " + QuestHelper.INSTANCE.getCompletedQuest().getName()), true);
                    Quester.LOGGER.info("Quest completed: " + QuestHelper.INSTANCE.getCompletedQuest().getName() + " by: " + player.getName());
                }
            }
        }
    }
}
