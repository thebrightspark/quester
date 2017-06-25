package com.mdc.quester.handler;

import com.mdc.quester.Quester;
import com.mdc.quester.client.QuestCompletedRenderer;
import com.mdc.quester.client.QuestPageRenderer;
import com.mdc.quester.consts.EnumGui;
import com.mdc.quester.container.ContainerQuestPage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class GuiHandler implements IGuiHandler {
    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if(ID >= 0 && ID < EnumGui.values().length){
            switch(EnumGui.values()[ID]){
                case QUEST_PAGE:
                    return new ContainerQuestPage(player, world);
            }
        }
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if(ID >= 0 && ID < EnumGui.values().length){
            switch(EnumGui.values()[ID]){
                case QUEST_DISPLAY:
                    return new QuestCompletedRenderer((EntityPlayerMP)player);
                case QUEST_PAGE:
                    return new QuestPageRenderer();
                case QUEST_COMPLETED:
                    Quester.LOGGER.info("Rendering Completed Quest Renderer!");
                    return new QuestCompletedRenderer((EntityPlayerMP)player);
            }
        }
        return null;
    }
}
