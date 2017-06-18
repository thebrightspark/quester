package com.mdc.quester.capability.quest;

import com.mdc.quester.Quester;
import com.mdc.quester.capability.CapabilityProvider;
import com.mdc.quester.handler.NetworkHandler;
import com.mdc.quester.templates.IQuestTemplate;
import com.mdc.quester.messages.MessageCapability;
import com.mdc.quester.player.QuesterCapability;
import com.mdc.quester.registry.QuestData;
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
    private static final String KEY_QUEST_COMPLETE = "completed_quests";
    private static final String KEY_QUEST_UNCOMPLETE = "uncomplete_quests";
    private EntityPlayerMP player;
    private static Set<IQuestTemplate> questsCompleted = new HashSet<>();
    private static Set<IQuestTemplate> questsIncompleted = new HashSet<>();

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
    public void clearSet(Set<IQuestTemplate> list) {
        list.clear();
    }

    @Override
    public boolean addIncompletedQuest(IQuestTemplate quest, EntityPlayerMP player) {
        if(questsIncompleted.size() == 0){
            questsIncompleted.add(quest);
            QuestData.setIncompletedQuest(quest);
            dataChanged(player);
            return true;
        }else{
            for(IQuestTemplate q : questsIncompleted){
                if(!q.getName().equals(quest.getName())){
                    questsIncompleted.add(quest);
                    QuestData.setIncompletedQuest(quest);
                    dataChanged(player);
                    return true;
                }
            }
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
                if (!q.getName().equals(quest.getName()) && !hasCompletedQuest(quest)) {
                    questsCompleted.add(quest);
                    dataChanged(player);
                    return true;
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
        for(IQuestTemplate quest : QuestData.completedQuests){
            NBTTagList taglist = nbt.getTagList(KEY_QUEST_COMPLETE, Constants.NBT.TAG_COMPOUND);
            NBTTagCompound tag = new NBTTagCompound();
            tag.setString(KEY_QUEST_COMPLETE, quest.getName());
            taglist.appendTag(tag);
        }
        for(IQuestTemplate quest : QuestData.incompletedQuests){
            NBTTagList taglist = nbt.getTagList(KEY_QUEST_UNCOMPLETE, Constants.NBT.TAG_COMPOUND);
            NBTTagCompound tag = new NBTTagCompound();
            tag.setString(KEY_QUEST_UNCOMPLETE, quest.getName());
            taglist.appendTag(tag);
        }
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        if(nbt.hasKey(KEY_QUESTS)){
            NBTTagList taglist = nbt.getTagList(KEY_QUEST_COMPLETE, Constants.NBT.TAG_COMPOUND);
            for(int i = 0; i < taglist.tagCount();i++) {
                IQuestTemplate quest = QuestData.getQuestByName(taglist.getStringTagAt(i));
                questsCompleted.add(quest);
            }
        }
    }
}
