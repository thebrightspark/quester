package com.mdc.quester.quests;

import com.mdc.quester.capability.quest.ICapQuests;
import com.mdc.quester.registry.QuestData;
import com.mdc.quester.templates.IQuestTemplate;
import com.mdc.quester.player.QuesterCapability;
import net.minecraft.entity.player.EntityPlayerMP;

public class QuestHelper {

    private static IQuestTemplate getLastCompletedQuest;

    public static void setCompletedQuest(IQuestTemplate quest, EntityPlayerMP player){
        getLastCompletedQuest = quest;
        if(!player.hasCapability(QuesterCapability.QUESTS, null)) return;
        ICapQuests icap = player.getCapability(QuesterCapability.QUESTS, null);
        if(icap == null) return;
        icap.addCompletedQuest(quest, player);
        QuestData.setCompletedQuest(quest);
    }

    public static IQuestTemplate getCompletedQuest(){
        return getLastCompletedQuest;
    }
}
