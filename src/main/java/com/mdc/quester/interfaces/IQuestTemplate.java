package com.mdc.quester.interfaces;

/**
 * Created by Alex on 6/11/2017.
 */
public interface IQuestTemplate<T> {
    /**
     * Implement this so that the registry can identify what is being registered
     * @return the type that is implementing this interface
     */
    default IQuestTemplate<T> getT(IQuestTemplate<T> t){
        return t;
    }

    /**
     * When you register a quest and set an id for the quest, this method can be used to separate different id's.
     * I might make it so that you can register multiple quests under the same quest id (metaquests)
     * @return the id of the quest
     */
    int getId();

    /**
     * This method is used to get the page that the quest is registered to.
     * @return This will return the page that is implementing {@link IQuestPageTemplate}
     */
    IQuestPageTemplate<?> getPage();
}
