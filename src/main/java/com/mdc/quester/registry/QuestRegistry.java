package com.mdc.quester.registry;

import com.mdc.quester.interfaces.IQuestPageTemplate;
import com.mdc.quester.interfaces.IQuestTemplate;

import java.util.HashSet;
import java.util.Set;

public class QuestRegistry {

    protected static int questid;
    public static Set<IQuestTemplate> quests = new HashSet<IQuestTemplate>();
    public static Set<IQuestPageTemplate> pages = new HashSet<IQuestPageTemplate>();

    public static <T extends IQuestTemplate<T>> IQuestTemplate<T> registerQuest(IQuestTemplate<T> quest, String name){
        questid = quest.getId();
        QuestData.setQuestTemplate(quest, name);
        QuestData.setQuestId(questid);
        quests.add(quest);
        return quest.getT(quest);
    }

    public static <T extends IQuestPageTemplate<T>> IQuestPageTemplate<T> registerQuestPage(IQuestPageTemplate<T> questPage, String name){
        QuestData.setQuestPageTemplate(questPage, name);
        return questPage;
    }
}
