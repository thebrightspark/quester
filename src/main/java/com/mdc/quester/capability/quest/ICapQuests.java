package com.mdc.quester.capability.quest;

import com.mdc.quester.capability.ICapability;
import com.mdc.quester.interfaces.IQuestTemplate;
import com.mdc.quester.registry.QuestData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import java.util.Set;

public interface ICapQuests extends ICapability {
    Set<IQuestTemplate> getCompletedQuests();

    boolean hasCompletedQuest(IQuestTemplate quest);

    boolean addCompletedQuest(IQuestTemplate quest, EntityPlayerMP player);

    void setPlayer(EntityPlayerMP player);
}
