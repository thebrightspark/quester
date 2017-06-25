package com.mdc.quester.container;

import com.mdc.quester.client.QuestPageRenderer;
import com.mdc.quester.templates.IQuestTemplate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.world.World;

import java.util.Set;

public class QuesterContainer extends Container{

    protected World world;
    protected int xStart, yStart;

    public QuesterContainer(EntityPlayer player, World world){
        this.world = world;
        init();
    }

    protected void init(){}

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

    protected void addQuestsFromQuestSet(Set<IQuestTemplate> quests){
        for(IQuestTemplate quest : quests){
            for(int i = 0; i < quests.size(); i++) {
                addQuest(quest, 50 + (i * 10), 50);
            }
        }
    }

    private void addQuest(IQuestTemplate quest, int x, int y){
        QuestPageRenderer.drawRect(x, y, x+25, y+25, 0xff5500);

    }
}
