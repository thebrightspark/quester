package com.mdc.quester.handler;

import com.mdc.quester.Quester;
import com.mdc.quester.messages.MessageCapability;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandler {
    public static SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Quester.MODID.toLowerCase());

    private static int packetID = 0;

    public static void init(){
        regPacket(MessageCapability.Handler.class, MessageCapability.class, Side.CLIENT);
    }

    private static <REQ extends IMessage, REPLY extends IMessage> void regPacket(Class<? extends IMessageHandler<REQ, REPLY>> handler, Class<REQ> message, Side side){
        INSTANCE.registerMessage(handler, message, packetID++, side);
    }
}
