package com.mdc.quester.quests;

import com.mdc.quester.capability.quest.ICapQuests;
import com.mdc.quester.interfaces.IQuestTemplate;
import com.mdc.quester.player.QuesterCapability;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.HashSet;
import java.util.Set;

public class QuestHelper {

    private static IQuestTemplate getLastCompletedQuest;

    public static void setCompletedQuest(IQuestTemplate quest, EntityPlayer player, boolean completed){
        if(completed) {
            getLastCompletedQuest = quest;
            if(!player.hasCapability(QuesterCapability.QUESTS, null)) return;
            ICapQuests icap = player.getCapability(QuesterCapability.QUESTS, null);
            if(icap == null) return;
            icap.addCompletedQuest(quest, (EntityPlayerMP)player);
        }
    }

    public static IQuestTemplate getCompletedQuest(){
        return getLastCompletedQuest;
    }

    public static boolean isQuestCompleted(IQuestTemplate quest, EntityPlayer player){
        if(!player.hasCapability(QuesterCapability.QUESTS, null)) return false;
        ICapQuests icap = player.getCapability(QuesterCapability.QUESTS, null);
        if(icap == null) return false;
        for(IQuestTemplate completedQuest : icap.completedQuest()){
            return completedQuest.getName().equalsIgnoreCase(quest.getName());
        }
        return false;
    }
}
