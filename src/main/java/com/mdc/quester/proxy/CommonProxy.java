package com.mdc.quester.proxy;

import com.mdc.quester.command.CommandQuesterBase;
import com.mdc.quester.examples.QuestGetStone;
import com.mdc.quester.examples.QuestGetWood;
import com.mdc.quester.examples.QuestModPage;
import com.mdc.quester.handler.NetworkHandler;
import com.mdc.quester.player.QuesterCapability;
import com.mdc.quester.registry.QuestRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy {
    public void preInit(){
        QuesterCapability.init();
        NetworkHandler.init();
    }

    public void init(){
        QuestRegistry.registerQuest(new QuestGetWood());
        QuestRegistry.registerQuest(new QuestGetStone());
        QuestRegistry.registerQuestPage(new QuestModPage(), "modPage");
    }

    public void postInit(){}

    public void serverStarting(FMLServerStartingEvent event){
        event.registerServerCommand(new CommandQuesterBase());
    }
}
