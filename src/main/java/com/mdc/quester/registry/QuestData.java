package com.mdc.quester.registry;

import com.mdc.quester.Quester;
import com.mdc.quester.templates.IQuestPageTemplate;
import com.mdc.quester.templates.IQuestTemplate;
import net.minecraftforge.fml.common.FMLLog;

import java.util.*;

public class QuestData {
    public static Set<IQuestTemplate> quests = new HashSet<>();
    public static Map<String, IQuestPageTemplate> pages  = new HashMap<>();
    public static Set<IQuestTemplate> incompletedQuests = new HashSet<>();
    public static Set<IQuestTemplate> completedQuests = new HashSet<>();

    public static IQuestTemplate<?> getQuestTemplate(){
        for(IQuestTemplate quest : quests){
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
        for(IQuestTemplate quest : quests){
            if(quest.getName().equals(name)){
                return quest;
            }
        }
        return null;
    }

    static <T extends IQuestTemplate<T>> void setQuestTemplate(IQuestTemplate<T> template){
        Quester.LOGGER.info("Registering quest: ");
        if(quests.size() == 0){
            quests.add(template);
            Quester.LOGGER.info("\t"+template.getName());
        }else{
            Iterable<IQuestTemplate> iterable = quests;
            Iterator iterator = iterable.iterator();
            if(iterator.hasNext()){
                if(iterable.iterator().next().getName().equals(template.getName())){
                    try {
                        throw new Exception("Cannot set quest complete as it is already completed: " + template.getName());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }else{
                    Quester.LOGGER.info("\t"+template.getName());
                    quests.add(template);
                }
            }
        }
    }

    public static void setIncompletedQuest(IQuestTemplate quest){
        Quester.LOGGER.info("Setting quests 'incomplete':");
        if(incompletedQuests.size() == 0){
            completedQuests.remove(quest);
            incompletedQuests.add(quest);
            Quester.LOGGER.info("\t"+quest.getName());
        }else{
            Iterable<IQuestTemplate> incompletedIterable = incompletedQuests;
            Iterator iterator = incompletedIterable.iterator();
            while (iterator.hasNext()) {
                incompletedQuests.add(incompletedIterable.iterator().next());
                completedQuests.add(incompletedIterable.iterator().next());
                if(!iterator.hasNext()){
                    if(incompletedIterable.iterator().next().getName().equals(quest.getName())){
                        try {
                            throw new Exception("Cannot set quest complete as it is already completed: " + quest.getName());
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }else {
                        incompletedQuests.add(quest);
                        completedQuests.remove(quest);
                    }
                }
            }
        }
    }

    public static void setCompletedQuest(IQuestTemplate quest){
        Quester.LOGGER.info("Setting quests 'complete':");
        if(completedQuests.size() == 0){
            incompletedQuests.remove(quest);
            completedQuests.add(quest);
        }else {
            Iterable<IQuestTemplate> completedIterable = completedQuests;
            Iterable<IQuestTemplate> incompletedIterable = incompletedQuests;
            Iterator iterator = completedIterable.iterator();
            while(iterator.hasNext()){
                completedQuests.add(completedIterable.iterator().next());
                incompletedQuests.add(incompletedIterable.iterator().next());
                if(!iterator.hasNext()){
                    if(completedIterable.iterator().next().getName().equals(quest.getName())){
                        try {
                            throw new Exception("Cannot set quest complete as it is already completed: " + quest.getName());
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                    }else {
                        completedQuests.add(quest);
                        incompletedQuests.remove(quest);
                    }
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
