package com.mdc.quester.examples;

import com.mdc.quester.interfaces.IQuestPageTemplate;

public class QuestModPage implements IQuestPageTemplate<QuestModPage> {

    @Override
    public int getPageNumber() {
        return 1;
    }
}
