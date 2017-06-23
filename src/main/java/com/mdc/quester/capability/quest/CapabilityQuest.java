package com.mdc.quester.capability.quest;

import com.mdc.quester.Quester;
import com.mdc.quester.capability.CapabilityProvider;
import com.mdc.quester.handler.NetworkHandler;
import com.mdc.quester.quests.QuestHelper;
import com.mdc.quester.templates.IQuestTemplate;
import com.mdc.quester.messages.MessageCapability;
import com.mdc.quester.player.QuesterCapability;
import com.mdc.quester.registry.QuestData;
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

public class CapabilityQuest implements ICapQuests{
    private static final String KEY_QUEST = "quest";
    private static final String KEY_QUESTS = "quests";
    private static final String KEY_QUEST_COMPLETE = "completed_quests";
    private static final String KEY_QUEST_INCOMPLETE = "incomplete_quests";
    private static final String KEY_QUESTS_PROGRESS = "progress";
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
        NBTTagCompound nbt = new NBTTagCompound();
        NBTTagList list = new NBTTagList();
        for(IQuestTemplate temp : this.getCompletedQuests()){
            NBTTagCompound comp_compl = new NBTTagCompound();
            NBTTagCompound progress = temp.serializeQuest(this.player);
            comp_compl.setTag(KEY_QUESTS_PROGRESS, progress);
            list.appendTag(comp_compl);
        }
        for(IQuestTemplate temp : this.getIncompletedQuests()){
            NBTTagCompound comp_compl = new NBTTagCompound();
            NBTTagCompound progress = temp.serializeQuest(this.player);
            comp_compl.setTag(KEY_QUESTS_PROGRESS, progress);
            list.appendTag(comp_compl);
        }
        NBTTagCompound compound = new NBTTagCompound();
        compound.setTag(KEY_QUEST, list);
        nbt.setTag(KEY_QUESTS, compound);
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        if(nbt.hasKey(KEY_QUESTS)){
            NBTTagList taglist = nbt.getTagList(KEY_QUEST, Constants.NBT.TAG_COMPOUND);
            for(IQuestTemplate temp : this.getCompletedQuests()){
                for(int i = 0; i < taglist.tagCount(); i++) {
                    if(taglist.getCompoundTagAt(i).hasKey(KEY_QUESTS_PROGRESS)) {
                        temp.deserializeQuest(taglist.getCompoundTagAt(i));
                    }else{
                        this.setCompletedQuests(taglist.getStringTagAt(i));
                    }
                }
            }
            for(IQuestTemplate temp : this.getIncompletedQuests()){
                for(int i = 0; i < taglist.tagCount(); i++) {
                    if(taglist.getCompoundTagAt(i).hasKey(KEY_QUESTS_PROGRESS)) {
                        temp.deserializeQuest(taglist.getCompoundTagAt(i));
                    }else{
                        this.setIncompletedQuests(taglist.getStringTagAt(i));
                    }
                }
            }
        }
    }
}
