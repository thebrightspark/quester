package com.mdc.quester.interfaces;

public interface IQuestPageTemplate<T> {
    default T getT(T t){
        return t;
    }

    int getPageNumber();
}
