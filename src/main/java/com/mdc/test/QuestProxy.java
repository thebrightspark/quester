package com.mdc.test;

import com.mdc.quester.registry.QuestRegistry;

public class QuestProxy {
    public static void init(){
        QuestRegistry.registerQuest(new QuestGetWood());
        QuestRegistry.registerQuest(new QuestGetPlanks());
        QuestRegistry.registerQuest(new QuestResources());
    }
}
