package com.mdc.quester.command;

import com.mdc.quester.capability.quest.ICapQuests;
import com.mdc.quester.interfaces.IQuestTemplate;
import com.mdc.quester.player.QuesterCapability;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import java.util.Set;

public class CommandQuesterBase extends CommandBase{
    @Override
    public String getName() {
        return "quester";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/quester <reset:show>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if(sender.getEntityWorld().isRemote) return;
        if(args.length == 0){
            sender.sendMessage(new TextComponentString(TextFormatting.RED + "Wrong use of command: /quester <reset:show>"));
        }else if(args[0].equalsIgnoreCase("reset")){
            resetQuests(sender);
        }else if(args[0].equalsIgnoreCase("show")){
            showQuests(sender);
        }
    }

    private void resetQuests(ICommandSender sender){
        if(sender.getCommandSenderEntity() instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer)sender.getCommandSenderEntity();
            if(player.hasCapability(QuesterCapability.QUESTS, null)){
                ICapQuests icap = player.getCapability(QuesterCapability.QUESTS, null);
                if(icap == null) return;
                icap.completedQuest().clear();
            }
        }
    }

    private void showQuests(ICommandSender sender){
        if(sender.getCommandSenderEntity() instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer)sender.getCommandSenderEntity();
            if(player.hasCapability(QuesterCapability.QUESTS, null)){
                ICapQuests icap = player.getCapability(QuesterCapability.QUESTS, null);
                if(icap == null) return;
                player.sendStatusMessage(new TextComponentString("Completed Quests: "), false);
                for(IQuestTemplate quest : icap.completedQuest()){
                    player.sendStatusMessage(new TextComponentString(quest.getName()), false);
                }
            }
        }
    }
}