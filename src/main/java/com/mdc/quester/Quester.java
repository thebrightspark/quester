package com.mdc.quester;

import com.mdc.quester.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Quester.MODID, name="quester", version = Quester.VERSION, acceptedMinecraftVersions = "{1.11.2, 1.12}")
public class Quester
{
    public static final String MODID = "quester";
    public static final String VERSION = "1.0";
    public static final String CLIENT_PROXY = "com.mdc.quester.proxy.ClientProxy";
    public static final String COMMON_PROXY = "com.mdc.quester.proxy.CommonProxy";

    @SidedProxy(clientSide=CLIENT_PROXY, serverSide=COMMON_PROXY)
    public static CommonProxy proxy;

    public static final Logger LOGGER = LogManager.getLogger(MODID.toUpperCase());

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event){
        proxy.preInit();
    }

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event){
        proxy.init();
    }

    @Mod.EventHandler
    public static void serverStarting(FMLServerStartingEvent event){
        proxy.serverStarting(event);
    }
}
