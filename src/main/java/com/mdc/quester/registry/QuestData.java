package com.mdc.quester.registry;

import com.mdc.quester.interfaces.IQuestPageTemplate;
import com.mdc.quester.interfaces.IQuestTemplate;

import java.util.*;

public class QuestData {
    private static Map<String, IQuestTemplate> quests = new HashMap<>();
    private static Map<String, IQuestPageTemplate> pages  = new HashMap<>();
    private static Set<IQuestTemplate> completedQuests = new HashSet<>();

    public static IQuestTemplate<?> getQuestTemplate(){
        for(IQuestTemplate quest : quests.values()){
            return quest;
        }
        return null;
    }

    public static IQuestPageTemplate<?> getPageTemplate(){
        for(IQuestPageTemplate page : pages.values()){
            return page;
        }
        return null;
    }

    static <T extends IQuestTemplate<T>> void setQuestTemplate(IQuestTemplate<T> template, String name){
        quests.put(name, template);
    }

    static <T extends IQuestPageTemplate<T>> void setQuestPageTemplate(IQuestPageTemplate<T> template, String name){
        pages.put(name, template);
    }

    public static Set<IQuestTemplate> getCompletedQuests(){
        return completedQuests;
    }
}
