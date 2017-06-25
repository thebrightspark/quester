package com.mdc.quester.container;

import com.mdc.quester.templates.IQuestTemplate;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import java.util.Set;

public class ContainerQuestPage extends QuesterContainer{
    public ContainerQuestPage(EntityPlayer player, World world) {
        super(player, world);
    }

    @Override
    protected void init() {
        xStart = 48;
        yStart = 122;
    }

    @Override
    protected void addQuestsFromQuestSet(Set<IQuestTemplate> quests) {
        super.addQuestsFromQuestSet(quests);
    }
}
