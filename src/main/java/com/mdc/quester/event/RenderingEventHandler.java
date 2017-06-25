package com.mdc.quester.event;

import com.mdc.quester.client.QuestCompletedRenderer;
import com.mdc.quester.utils.GUIUtils;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class RenderingEventHandler {
    public static boolean canRender = false;
    public static String nameToRender = null;

    @SubscribeEvent
    public static void renderOverlay(RenderGameOverlayEvent.Post event){
        if(event.getType() != RenderGameOverlayEvent.ElementType.EXPERIENCE){
            return;
        }else{
            GUIUtils.showQuestOverlay(Minecraft.getMinecraft().player, "test");
        }

    }
}
