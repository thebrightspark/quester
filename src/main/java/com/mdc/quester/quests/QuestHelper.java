package com.mdc.quester.quests;

import com.mdc.quester.capability.quest.ICapQuests;
import com.mdc.quester.registry.QuestData;
import com.mdc.quester.templates.IQuestTemplate;
import com.mdc.quester.player.QuesterCapability;
import net.minecraft.entity.player.EntityPlayerMP;

public class QuestHelper {

    private IQuestTemplate getLastCompletedQuest;

    public static final QuestHelper INSTANCE = new QuestHelper();

    public ICapQuests getQuestCapability(EntityPlayerMP player){
        if(!player.hasCapability(QuesterCapability.QUESTS, null)) return null;
        ICapQuests icap = player.getCapability(QuesterCapability.QUESTS, null);
        if(icap == null)
            return null;
        else
            icap.setPlayer(player);
            return icap;
    }

    public void setCompletedQuest(IQuestTemplate quest, EntityPlayerMP player){
        this.getLastCompletedQuest = quest;
        ICapQuests icap = this.getQuestCapability(player);
        if(icap == null) return;
        icap.addCompletedQuest(quest, player);
        QuestData.INSTANCE.setCompletedQuest(quest);
    }

    public void setIncompleteQuest(IQuestTemplate quest, EntityPlayerMP player){
        this.getLastCompletedQuest = quest;
        ICapQuests icap = getQuestCapability(player);
        if(icap == null) return;
        icap.addIncompletedQuest(quest, player);
        QuestData.INSTANCE.setIncompletedQuest(quest);
    }

    public IQuestTemplate getCompletedQuest(){
        return this.getLastCompletedQuest;
    }
}
