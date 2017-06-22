package com.mdc.quester.registry;

import com.mdc.quester.Quester;
import com.mdc.quester.templates.IQuestPageTemplate;
import com.mdc.quester.templates.IQuestTemplate;

public class QuestRegistry {

    public static <T extends IQuestTemplate<T>> IQuestTemplate<T> registerQuest(IQuestTemplate<T> quest){
        QuestData.INSTANCE.setQuestTemplate(quest);
        return quest.getT(quest);
    }

    public static <T extends IQuestPageTemplate<T>> IQuestPageTemplate<T> registerQuestPage(IQuestPageTemplate<T> questPage, String name){
        QuestData.INSTANCE.setQuestPageTemplate(questPage, name);
        return questPage;
    }
}
