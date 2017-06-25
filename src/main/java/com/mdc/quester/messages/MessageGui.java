package com.mdc.quester.messages;

import com.mdc.quester.capability.quest.ICapQuests;
import com.mdc.quester.player.QuesterCapability;
import com.mdc.quester.quests.QuestHelper;
import com.mdc.quester.templates.IQuestTemplate;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.UUID;

public class MessageGui implements IMessage{
    private int guiID;
    private UUID playerUuid;

    public MessageGui(){}

    public MessageGui(UUID playerUuid){
        this.playerUuid = playerUuid;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        guiID = buf.readInt();
        long mostSig = buf.readLong();
        long leastSig = buf.readLong();
        playerUuid = new UUID(mostSig, leastSig);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(guiID);
        buf.writeLong(playerUuid.getMostSignificantBits());
        buf.writeLong(playerUuid.getLeastSignificantBits());
    }

    public static class Handler implements IMessageHandler<MessageGui, IMessage> {

        @Override
        public IMessage onMessage(MessageGui message, MessageContext ctx) {
            IThreadListener mainThread = (WorldServer) ctx.getServerHandler().playerEntity.world;
            mainThread.addScheduledTask(() -> {
                WorldServer world = (WorldServer) ctx.getServerHandler().playerEntity.world;
                EntityPlayerMP player = (EntityPlayerMP)world.getPlayerEntityByUUID(message.playerUuid);
                ICapQuests icap = QuestHelper.INSTANCE.getQuestCapability(player);
                if(icap.getIncompletedQuests().size() == 0) return;
                for(IQuestTemplate temp : icap.getIncompletedQuests()){
                    MinecraftServer server = world.getMinecraftServer();
                    if(server != null){
                        ITextComponent msg1;
                        if(player != null){
                            msg1 = new TextComponentTranslation("message.quests.incomplete", player.getDisplayNameString()).appendText(" [");
                        }else{
                            msg1 = new TextComponentTranslation(" [");
                        }

                        /*TextComponentTranslation questName = new TextComponentTranslation(temp.getName());
                        questName.getStyle().setColor(TextFormatting.RED);
                        questName.getStyle().setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                                new TextComponentTranslation("message.quest.description")
                                        .appendText(":\n")
                                        .appendSibling(new TextComponentTranslation(temp.getName())
                                        .appendText("\n"))));*/
                        server.getPlayerList().sendMessage(msg1.appendText(temp.getName()).appendText(" ]"));
                    }
                }
            });

            return null;
        }
    }
}
