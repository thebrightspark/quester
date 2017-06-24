package com.mdc.quester.capability.quest;

import com.mdc.quester.Quester;
import com.mdc.quester.capability.CapabilityProvider;
import com.mdc.quester.handler.NetworkHandler;
import com.mdc.quester.quests.QuestHelper;
import com.mdc.quester.templates.IQuestTemplate;
import com.mdc.quester.messages.MessageCapability;
import com.mdc.quester.player.QuesterCapability;
import com.mdc.quester.registry.QuestData;
import com.mdc.quester.utils.NBTUtils;
import io.netty.util.internal.ConcurrentSet;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.Constants;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static com.mdc.quester.utils.NBTUtils.*;

public class CapabilityQuest implements ICapQuests{

    private EntityPlayerMP player;
    private Set<IQuestTemplate> questsCompleted = new ConcurrentSet<>();
    private Set<IQuestTemplate> questsIncompleted = new ConcurrentSet<>();

    @Override
    public Set<IQuestTemplate> getCompletedQuests() {
        return questsCompleted;
    }

    public Set<IQuestTemplate> getIncompletedQuests(){
        return questsIncompleted;
    }

    public void setCompletedQuests(String name){
        this.questsCompleted.clear();
        IQuestTemplate quest = QuestData.INSTANCE.getQuestByName(name);
        this.questsCompleted.add(quest);
    }

    public void setIncompletedQuests(String name){
        this.questsIncompleted.clear();
        IQuestTemplate quest = QuestData.INSTANCE.getQuestByName(name);
        this.questsIncompleted.add(quest);
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
    public boolean addIncompletedQuest(IQuestTemplate quest, EntityPlayerMP player) {
        if(questsIncompleted.size() == 0){
            questsCompleted.remove(quest);
            questsIncompleted.add(quest);
            dataChanged(player);
            return true;
        }else{
            Iterator<IQuestTemplate> iterator = questsIncompleted.iterator();
            while(iterator.hasNext()) {
                IQuestTemplate temp = iterator.next();
                if (temp.getName().equals(quest.getName())) {
                    return false;
                }
            }
            questsCompleted.remove(quest);
            questsIncompleted.add(quest);
            dataChanged(player);
            return true;
        }

    }

    @Override
    public boolean addCompletedQuest(IQuestTemplate quest, EntityPlayerMP player){
        if(questsCompleted.size() == 0){
            questsIncompleted.remove(quest);
            questsCompleted.add(quest);
            dataChanged(player);
            return true;
        }else {
            Iterator<IQuestTemplate> iterator = questsCompleted.iterator();
            while(iterator.hasNext()) {
                IQuestTemplate temp = iterator.next();
                if (temp.getName().equals(quest.getName()) && !hasCompletedQuest(quest)) {
                    return false;
                }
            }
            questsIncompleted.remove(quest);
            questsCompleted.add(quest);
            dataChanged(player);
            return true;
        }
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
        NBTTagCompound ret = new NBTTagCompound();

        NBTTagList completed = new NBTTagList();
        for(IQuestTemplate temp : this.getCompletedQuests()){
            NBTTagCompound quest = new NBTTagCompound();
            quest.setString(KEY_QUEST_NAME, temp.getName());
            quest.setTag(KEY_QUEST_DATA, temp.serializeQuest(this.player));
            completed.appendTag(quest);
        }
        ret.setTag(NBTUtils.KEY_QUEST_COMPLETE, completed);

        NBTTagList incompleted = new NBTTagList();
        for(IQuestTemplate temp : this.getIncompletedQuests()){
            NBTTagCompound quest = new NBTTagCompound();
            quest.setString(KEY_QUEST_NAME, temp.getName());
            quest.setTag(KEY_QUEST_DATA, temp.serializeQuest(this.player));
            incompleted.appendTag(quest);
        }
        ret.setTag(KEY_QUEST_INCOMPLETE, incompleted);

        return ret;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        //TODO: Deserialize NBT
        if(nbt.hasKey(KEY_QUEST_COMPLETE)){
            NBTTagList list = nbt.getTagList(KEY_QUEST_COMPLETE, Constants.NBT.TAG_COMPOUND);
            for(int i = 0; i < list.tagCount(); i++){
                if(list.getStringTagAt(i).equals(KEY_QUEST_NAME)) {
                    IQuestTemplate temp = QuestData.INSTANCE.getQuestByName(list.getStringTagAt(i));
                    this.questsCompleted.add(temp);
                }else if(list.getStringTagAt(i).equals(KEY_QUEST_DATA)) {
                    IQuestTemplate temp = QuestData.INSTANCE.getQuestByName(list.getStringTagAt(i));
                    temp.deserializeQuest(nbt);
                }
            }
        }
        if(nbt.hasKey(KEY_QUEST_INCOMPLETE)){
            NBTTagList list = nbt.getTagList(KEY_QUEST_INCOMPLETE, Constants.NBT.TAG_COMPOUND);
            for(int i = 0; i < list.tagCount(); i++){
                if(list.getCompoundTagAt(i).hasKey(KEY_QUEST_NAME) && list.getCompoundTagAt(i).hasKey(KEY_QUEST_DATA)) {
                    IQuestTemplate temp = QuestData.INSTANCE.getQuestByName(list.getCompoundTagAt(i).getString(KEY_QUEST_NAME));
                    temp.deserializeQuest(nbt.getCompoundTag(KEY_QUEST_DATA));
                    this.questsCompleted.add(temp);
                }
            }
        }
    }
}
