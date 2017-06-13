package com.mdc.quester.event;

import com.mdc.quester.Quester;
import com.mdc.quester.capability.ICapability;
import com.mdc.quester.capability.quest.ICapQuests;
import com.mdc.quester.interfaces.IQuestTemplate;
import com.mdc.quester.player.QuesterCapability;
import com.mdc.quester.quests.QuestHelper;
import com.mdc.quester.registry.QuestRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.logging.log4j.Level;

@Mod.EventBusSubscriber
public class EventHandler {

    public static boolean smelted = false;

    @SubscribeEvent
    public static void addCapability(AttachCapabilitiesEvent<Entity> event){
        Entity entity = event.getObject();
        if(entity instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer)event.getObject();
            for(Capability<? extends ICapability> capability : QuesterCapability.getCapabilties()){
                ICapability icap = capability.getDefaultInstance();
                if(!player.hasCapability(QuesterCapability.QUESTS, null)){
                    event.addCapability(icap.getKey(), icap.getProvider());
                }
            }
        }
    }

    @SubscribeEvent
    public static void tickQuests(TickEvent.PlayerTickEvent event){
        if(event.player.world.isRemote) return;
        EntityPlayer player = event.player;
        World world = player.world;
        BlockPos pos = player.getPosition();
        for(IQuestTemplate quest : QuestRegistry.quests) {
            ICapQuests icap = player.getCapability(QuesterCapability.QUESTS, null);
            if(icap == null) continue;
            icap.setPlayer(player);
            if (!icap.hasCompletedQuest(quest, player) && quest.triggered(player, world, pos)) {
                QuestHelper.setCompletedQuest(quest, player, true);
                icap.addCompletedQuest(quest, (EntityPlayerMP)player);
                player.sendStatusMessage(new TextComponentString("Quest complete: " + QuestHelper.getCompletedQuest().getName()), true);
            }else if(icap.hasCompletedQuest(quest, player) && quest.triggered(player, world, pos)){
                return;
            }
        }
    }

    @SubscribeEvent
    public static void itemSmelted(PlayerEvent.ItemSmeltedEvent event){
        if(event.player.world.isRemote) return;
        if(event.smelting.getItem() == Items.IRON_INGOT){
            smelted = true;
        }
    }
}
