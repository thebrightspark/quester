package com.mdc.quester.registry;

import com.mdc.quester.Quester;
import com.mdc.quester.templates.IQuestPageTemplate;
import com.mdc.quester.templates.IQuestTemplate;
import io.netty.util.internal.ConcurrentSet;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

import java.util.*;

public class QuestData {
    public Map<IQuestTemplate, ItemStack> quests = new HashMap<>();
    public Map<String, IQuestPageTemplate> pages  = new HashMap<>();
    public Set<IQuestTemplate> incompletedQuests = new ConcurrentSet<>();
    public Set<IQuestTemplate> completedQuests = new ConcurrentSet<>();

    public static QuestData INSTANCE = new QuestData();

    public IQuestTemplate getQuestByName(String name){
        for(IQuestTemplate quest : INSTANCE.quests.keySet()){
            if(quest.getName().equals(name)){
                return quest;
            }
        }
        return null;
    }

    <T extends IQuestTemplate<T>> void setQuestTemplate(IQuestTemplate<T> template, ItemStack displayIcon){
        Quester.LOGGER.info("Registering quest: ");
        if(INSTANCE.quests.size() == 0){
            INSTANCE.quests.put(template, displayIcon);
            Quester.LOGGER.info("\t"+template.getName());
        }else{
            Collection<IQuestTemplate> templist = INSTANCE.quests.keySet();
            Iterator<IQuestTemplate> iterator = templist.iterator();
            while(iterator.hasNext()){
                IQuestTemplate temp = iterator.next();
                if(temp.getName().equals(template.getName())){
                    try {
                        throw new Exception("Cannot set quest complete as it is already completed: " + template.getName());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
            Quester.LOGGER.info("\t"+template.getName());
            INSTANCE.quests.put(template, displayIcon);
        }
    }

    public void setIncompletedQuest(IQuestTemplate quest){
        Quester.LOGGER.info("Setting quests 'incomplete':");
        if(INSTANCE.incompletedQuests.size() == 0){
            INSTANCE.completedQuests.remove(quest);
            INSTANCE.incompletedQuests.add(quest);
            Quester.LOGGER.info("\t"+quest.getName());
        }else{
            Iterator<IQuestTemplate> it = incompletedQuests.iterator();
            while(it.hasNext()){
                IQuestTemplate temp = it.next();
                if(temp.getName().equals(quest.getName())){
                    try {
                        throw new Exception("Cannot set quest incomplete as it is already incompleted: " + quest.getName());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
            INSTANCE.completedQuests.remove(quest);
            INSTANCE.incompletedQuests.add(quest);
        }
    }

    public void setCompletedQuest(IQuestTemplate quest){
        Quester.LOGGER.info("Setting quests 'complete':");
        if(INSTANCE.completedQuests.size() == 0){
            INSTANCE.incompletedQuests.remove(quest);
            INSTANCE.completedQuests.add(quest);
            Quester.LOGGER.info(quest.getName());
        }else {
            Iterator<IQuestTemplate> it = INSTANCE.completedQuests.iterator();
            while(it.hasNext()) {
                IQuestTemplate temp = it.next();
                if (temp.getName().equals(quest.getName())) {
                    try {
                        throw new Exception("Cannot set quest complete as it is already completed: " + quest.getName());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
            INSTANCE.completedQuests.add(quest);
            INSTANCE.incompletedQuests.remove(quest);
            Quester.LOGGER.info(quest.getName());
        }
    }

    /*public ItemStack getDisplayIconFor(IQuestTemplate temp){
        for(IQuestTemplate t : INSTANCE.quests.keySet()){
            if(t.getName().equals(temp.getName())){
                return quests.get(t);
            }
        }
        return null;
    }*/

    <T extends IQuestPageTemplate<T>> void setQuestPageTemplate(IQuestPageTemplate<T> template, String name){
        INSTANCE.pages.put(name, template);
    }
}
