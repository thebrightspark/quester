package com.mdc.quester.registry;

import com.mdc.quester.Quester;
import com.mdc.quester.templates.IQuestPageTemplate;
import com.mdc.quester.templates.IQuestTemplate;

import java.util.*;

public class QuestData {
    public static Map<String, IQuestTemplate> quests = new HashMap<>();
    public static Map<String, IQuestPageTemplate> pages  = new HashMap<>();
    public static Set<IQuestTemplate> incompletedQuests = new HashSet<>();
    public static Set<IQuestTemplate> completedQuests = new HashSet<>();

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

    public static IQuestTemplate getQuestByName(String name){
        for(IQuestTemplate quest : quests.values()){
            if(quest.getName().equalsIgnoreCase(name)){
                return quest;
            }
        }
        return null;
    }

    public static void clearSet(Set<IQuestTemplate> set){
        set.clear();
    }

    static <T extends IQuestTemplate<T>> void setQuestTemplate(IQuestTemplate<T> template, String name){
        Quester.LOGGER.info("Registering quest: ");
        if(quests.size() == 0){
            quests.put(name, template);
            Quester.LOGGER.info("\t"+template.getName());
        }else{
            for(IQuestTemplate quest : quests.values()){
                if(template.getName().equalsIgnoreCase(quest.getName())){
                    throw new IllegalArgumentException("Cannot add quest as it is already added: " + template.getName() + " and " + quest.getName());
                }
                Quester.LOGGER.info("\t"+template.getName());
            }
            quests.put(name, template);
        }
    }

    public static void setIncompletedQuest(IQuestTemplate quest){
        Quester.LOGGER.info("Setting quests 'incomplete':");
        if(incompletedQuests.size() == 0){
            incompletedQuests.add(quest);
            Quester.LOGGER.info("\t"+quest.getName());
        }else{
            for(IQuestTemplate q : incompletedQuests){
                if(quest.getName().equalsIgnoreCase(q.getName())){
                    throw new IllegalArgumentException("Cannot add incomplete quest as it is already added to the set: " + quest.getName() + " and " + q.getName());
                }
                incompletedQuests.add(quest);
                Quester.LOGGER.info("\t"+quest.getName());
            }
        }
    }

    public static void setCompletedQuest(IQuestTemplate quest){
        Quester.LOGGER.info("Setting quests 'complete':");
        if(completedQuests.size() == 0){
            incompletedQuests.remove(quest);
            completedQuests.add(quest);
        }else {
            for (IQuestTemplate q : completedQuests) {
                if (quest.getName().equalsIgnoreCase(q.getName())) {
                    throw new IllegalArgumentException("I don't know how you managed to do this but uhh... you are setting a quest complete that is already completed???");
                } else {
                    Quester.LOGGER.info("\t" + quest.getName());
                    incompletedQuests.remove(quest);
                    completedQuests.add(quest);
                }
            }
        }
    }

    static <T extends IQuestPageTemplate<T>> void setQuestPageTemplate(IQuestPageTemplate<T> template, String name){
        pages.put(name, template);
    }

    public static Set<IQuestTemplate> getCompletedQuests(){
        return completedQuests;
    }
}
