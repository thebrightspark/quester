package com.mdc.quester.utils;

import com.mdc.quester.Quester;
import com.mdc.quester.consts.EnumGui;
import com.mdc.quester.handler.NetworkHandler;
import com.mdc.quester.messages.MessageGui;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GUIUtils {

    public static boolean openGuiOnQuestCompleted(EntityPlayerMP player, int id, boolean completed, String name){
        if(completed && !(name.equals(null))){
            BlockPos pos = player.getPosition();
            player.openGui(Quester.INSTANCE, id, player.world, pos.getX(), pos.getY(), pos.getZ());
            NetworkHandler.INSTANCE.sendToServer(new MessageGui(player.getUniqueID()));
            Quester.LOGGER.info("Opening gui: " + id);
            return true;
        }
        return false;
    }
}
