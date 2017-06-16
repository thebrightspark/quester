package com.mdc.quester.templates;

public interface IQuestPageTemplate<T> {
    default T getT(T t){
        return t;
    }

    int getPageNumber();
}
