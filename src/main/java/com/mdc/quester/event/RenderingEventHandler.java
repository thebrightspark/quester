package com.mdc.quester.event;

import com.mdc.quester.client.QuestCompletedRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class RenderingEventHandler {
    public static boolean canRender = false;
    public static String nameToRender = null;
    public static ItemStack displayIcon = ItemStack.EMPTY;

    @SubscribeEvent
    public static void renderOverlay(RenderGameOverlayEvent.Post event){
        Minecraft mc = Minecraft.getMinecraft();
        if(event.getType() == RenderGameOverlayEvent.ElementType.TEXT){
            if(mc.world.getTotalWorldTime() < mc.world.getTotalWorldTime() + 10) {
                QuestCompletedRenderer renderer = new QuestCompletedRenderer(mc, nameToRender, displayIcon).setLocation((mc.displayWidth / 2) - 160, (mc.displayHeight / 4) - 32).setSize(160, 32);
                renderer.initGui();
            }else{
                for(int i = 0; i < 160; i++){
                    QuestCompletedRenderer renderer = new QuestCompletedRenderer(mc, nameToRender, displayIcon).setLocation((mc.displayWidth / 2) - i, (mc.displayHeight / 4) - 32).setSize(160, 32);
                    renderer.initGui();
                }
            }
        }

    }
}
