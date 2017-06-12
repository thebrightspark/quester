package com.mdc.quester.registry;

import com.mdc.quester.interfaces.IQuestPageTemplate;
import com.mdc.quester.interfaces.IQuestTemplate;

import java.util.HashSet;
import java.util.Set;

public class QuestRegistry {

    public static Set<IQuestTemplate> quests = new HashSet<IQuestTemplate>();
    public static Set<IQuestPageTemplate> pages = new HashSet<IQuestPageTemplate>();

    public static <T extends IQuestTemplate<T>> IQuestTemplate<T> registerQuest(IQuestTemplate<T> quest){
        QuestData.setQuestTemplate(quest, quest.getName());
        quests.add(quest);
        return quest.getT(quest);
    }

    public static <T extends IQuestPageTemplate<T>> IQuestPageTemplate<T> registerQuestPage(IQuestPageTemplate<T> questPage, String name){
        QuestData.setQuestPageTemplate(questPage, name);
        pages.add(questPage);
        return questPage;
    }
}
