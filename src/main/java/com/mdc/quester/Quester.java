package com.mdc.quester;

import com.mdc.quester.quests.CompleteQuest;
import com.mdc.quester.registry.QuestRegistry;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Quester.MODID, name="quester", version = Quester.VERSION, acceptedMinecraftVersions = "{1.11.2, 1.12}")
public class Quester
{
    public static final String MODID = "quester";
    public static final String VERSION = "1.0";

    public static final Logger LOGGER = LogManager.getLogger(MODID.toUpperCase());

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event){
        QuestRegistry.registerQuest(new QuestGetWood(), "get_wood");
        QuestRegistry.registerQuestPage(new QuestModPage(), "modPage");
        LOGGER.log(Level.DEBUG, CompleteQuest.complete(Minecraft.getMinecraft().player, Minecraft.getMinecraft().world, Minecraft.getMinecraft().player.getPosition()));
    }
}
