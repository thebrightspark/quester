package com.mdc.quester.quests;

import com.mdc.quester.capability.quest.ICapQuests;
import com.mdc.quester.registry.QuestData;
import com.mdc.quester.templates.IQuestTemplate;
import com.mdc.quester.player.QuesterCapability;
import net.minecraft.entity.player.EntityPlayerMP;

public class QuestHelper {

    private static IQuestTemplate getLastCompletedQuest;

    public static ICapQuests getQuestCapability(EntityPlayerMP player){
        if(!player.hasCapability(QuesterCapability.QUESTS, null)) return null;
        ICapQuests icap = player.getCapability(QuesterCapability.QUESTS, null);
        if(icap == null)
            return null;
        else
            icap.setPlayer(player);
            return icap;
    }

    public static void setCompletedQuest(IQuestTemplate quest, EntityPlayerMP player){
        getLastCompletedQuest = quest;
        ICapQuests icap = QuestHelper.getQuestCapability(player);
        if(icap == null) return;
        icap.addCompletedQuest(quest, player);
        QuestData.setCompletedQuest(quest);
    }

    public static void setIncompleteQuest(IQuestTemplate quest, EntityPlayerMP player){
        getLastCompletedQuest = quest;
        ICapQuests icap = getQuestCapability(player);
        if(icap == null) return;
        icap.addIncompletedQuest(quest, player);
        QuestData.setIncompletedQuest(quest);
    }

    public static IQuestTemplate getCompletedQuest(){
        return getLastCompletedQuest;
    }
}
