package com.mdc.quester.utils;

import com.mdc.quester.Quester;
import com.mdc.quester.client.QuestCompletedRenderer;
import com.mdc.quester.handler.NetworkHandler;
import com.mdc.quester.messages.MessageGui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class GUIUtils {

    @Deprecated
    public static void openGuiOnQuestCompleted(EntityPlayerMP player, int id, boolean completed, String name){
        if(completed && !(name.equals(null))){
            sendChange(player);
            Quester.LOGGER.info("Opening gui: " + id);
        }
    }

    public static void showQuestOverlay(EntityPlayer player, String name){
        new QuestCompletedRenderer(player, name);
    }

    private static void sendChange(EntityPlayerMP player){
        NetworkHandler.INSTANCE.sendToServer(new MessageGui(player.getUniqueID()));
    }
}
