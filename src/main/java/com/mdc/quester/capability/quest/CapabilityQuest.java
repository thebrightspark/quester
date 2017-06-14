package com.mdc.quester.capability.quest;

import com.mdc.quester.Quester;
import com.mdc.quester.capability.CapabilityProvider;
import com.mdc.quester.handler.NetworkHandler;
import com.mdc.quester.interfaces.IQuestTemplate;
import com.mdc.quester.messages.MessageCapability;
import com.mdc.quester.player.QuesterCapability;
import com.mdc.quester.quests.QuestHelper;
import com.mdc.quester.registry.QuestData;
import com.mdc.quester.registry.QuestRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.Constants;

import java.util.HashSet;
import java.util.Set;

public class CapabilityQuest implements ICapQuests{
    private static final String KEY_QUESTS = "quests";
    private EntityPlayerMP player;
    private static Set<IQuestTemplate> questsCompleted = new HashSet<>();

    @Override
    public Set<IQuestTemplate> getCompletedQuests() {
        return questsCompleted;
    }

    @Override
    public void setPlayer(EntityPlayerMP player) {
        this.player = player;
    }

    @Override
    public boolean hasCompletedQuest(IQuestTemplate quest) {
        for(IQuestTemplate q : questsCompleted) {
            return quest.getName().equalsIgnoreCase(q.getName());
        }
        return false;
    }

    @Override
    public boolean addCompletedQuest(IQuestTemplate quest, EntityPlayerMP player){
        if(questsCompleted.size() == 0){
            questsCompleted.add(quest);
            dataChanged(player);
            return true;
        }else {
            for (IQuestTemplate q : questsCompleted) {
                if (q.getName().equalsIgnoreCase(quest.getName())) {
                    dataChanged(player);
                    return !hasCompletedQuest(quest) && questsCompleted.add(quest);
                }
            }
        }
        return false;
    }

    @Override
    public ResourceLocation getKey() {
        return new ResourceLocation(Quester.MODID, "quest");
    }

    @Override
    public ICapabilityProvider getProvider() {
        return new CapabilityProvider<>(QuesterCapability.QUESTS);
    }

    @Override
    public void dataChanged(EntityPlayerMP player) {
        NetworkHandler.INSTANCE.sendTo(new MessageCapability(QuesterCapability.QUESTS, serializeNBT()), player);
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        for(IQuestTemplate quest : questsCompleted){
            if(nbt.hasKey(KEY_QUESTS)){
                NBTTagList taglist = nbt.getTagList(KEY_QUESTS, Constants.NBT.TAG_COMPOUND);
                NBTTagCompound tag = new NBTTagCompound();
                if(!tag.hasKey(KEY_QUESTS)){
                    tag.setString(KEY_QUESTS, quest.getName());
                }
                taglist.appendTag(tag);
                nbt.setTag(KEY_QUESTS, taglist);
            }else{
                nbt.setString(KEY_QUESTS, quest.getName());

            }
        }
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        if(nbt.hasKey(KEY_QUESTS)){
            NBTTagList taglist = nbt.getTagList(KEY_QUESTS, Constants.NBT.TAG_COMPOUND);
            for(int i = 0; i < taglist.tagCount(); i++) {
                String name = taglist.getStringTagAt(i);
                for(IQuestTemplate quest : QuestData.quests.values()){
                    if(!quest.getName().equalsIgnoreCase(name)){
                        IQuestTemplate questToAdd = QuestData.getQuestByName(name);
                        addCompletedQuest(questToAdd, this.player);
                    }
                }
            }
        }
    }
}
