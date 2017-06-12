package com.mdc.quester.messages;

import com.mdc.quester.Quester;
import com.mdc.quester.capability.ICapability;
import com.mdc.quester.player.QuesterCapability;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageCapability implements IMessage {
    private int capID;
    private NBTTagCompound capNBT;

    public MessageCapability(){}

    public MessageCapability(Capability<? extends ICapability> capability, NBTTagCompound capNBT){
        capID = QuesterCapability.getCapabilityID(capability);
        if(capID == -1) Quester.LOGGER.error("Couldn't find an ID for the capability. Nothing will get updated on the client!");
        this.capNBT = capNBT;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        capID = buf.readInt();
        capNBT = ByteBufUtils.readTag(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(capID);
        ByteBufUtils.writeTag(buf, capNBT);
    }

    public static class Handler implements IMessageHandler<MessageCapability, IMessage> {

        @Override
        public IMessage onMessage(MessageCapability message, MessageContext ctx) {
            IThreadListener mainThread = Minecraft.getMinecraft();
            mainThread.addScheduledTask(() ->{
                Minecraft mc = Minecraft.getMinecraft();
                EntityPlayerSP player = mc.player;
                Capability<? extends ICapability> cap = QuesterCapability.getCapabilityFromID(message.capID);
                ICapability icap = player.getCapability(cap, null);
                if(icap != null) icap.deserializeNBT(message.capNBT);
            });

            return null;
        }
    }
}
