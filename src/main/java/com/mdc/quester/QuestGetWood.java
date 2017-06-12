package com.mdc.quester;

import com.mdc.quester.interfaces.IQuestPageTemplate;
import com.mdc.quester.interfaces.IQuestTemplate;

public class QuestGetWood implements IQuestTemplate<QuestGetWood> {

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public IQuestPageTemplate<QuestModPage> getPage() {
        return new QuestModPage();
    }
}
