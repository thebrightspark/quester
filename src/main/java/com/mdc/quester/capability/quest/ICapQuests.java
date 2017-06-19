package com.mdc.quester.capability.quest;

import com.mdc.quester.capability.ICapability;
import com.mdc.quester.templates.IQuestTemplate;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.List;
import java.util.Set;

public interface ICapQuests extends ICapability {
    Set<IQuestTemplate> getCompletedQuests();

    boolean hasCompletedQuest(IQuestTemplate quest);

    boolean addCompletedQuest(IQuestTemplate quest, EntityPlayerMP player);

    boolean addIncompletedQuest(IQuestTemplate quest, EntityPlayerMP player);

    void setPlayer(EntityPlayerMP player);
}
