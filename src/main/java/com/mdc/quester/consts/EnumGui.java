package com.mdc.quester.consts;

import net.minecraft.util.IStringSerializable;

/**
 * Created by Alex on 6/23/2017.
 */
public enum EnumGui implements IStringSerializable{
    QUEST_DISPLAY("quest_display"),
    QUEST_COMPLETED("quest_completed"),
    QUEST_PAGE("quest_page");

    public String name;

    EnumGui(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
