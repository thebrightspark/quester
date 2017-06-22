package com.mdc.quester.proxy;

import com.mdc.quester.command.CommandQuesterBase;
import com.mdc.quester.handler.NetworkHandler;
import com.mdc.quester.player.QuesterCapability;
import com.mdc.quester.registry.QuestRegistry;
import com.mdc.test.QuestProxy;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

public class CommonProxy {
    public void preInit(){
        QuesterCapability.init();
        NetworkHandler.init();
    }

    public void init(){
        //This is how the test quests are being initialized. Pay no heed to this.
        QuestProxy.init();
    }

    public void postInit(){}

    public void serverStarting(FMLServerStartingEvent event){
        event.registerServerCommand(new CommandQuesterBase());
    }
}
