package com.mdc.quester.capability.quest;

import com.mdc.quester.Quester;
import com.mdc.quester.capability.CapabilityProvider;
import com.mdc.quester.handler.NetworkHandler;
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
        NBTTagCompound n = new NBTTagCompound();
        //Adding incomplete quests to nbt
        Iterator<IQuestTemplate> it = QuestData.INSTANCE.incompletedQuests.iterator();
        while(it.hasNext()) {
            IQuestTemplate temp = it.next();
            NBTTagList taglist = new NBTTagList();
            for (int i = 0; i < QuestData.INSTANCE.incompletedQuests.size(); i++) {
                NBTTagList list_incomp = new NBTTagList();
                NBTTagCompound nbt_incomp = new NBTTagCompound();
                nbt_incomp.setString(KEY_QUEST, temp.getName());
                NBTTagCompound comp = temp.serializeQuest(this.player);
                nbt_incomp.setTag(KEY_QUESTS_PROGRESS, comp);
                list_incomp.appendTag(nbt_incomp);
                n.setTag(KEY_QUEST_INCOMPLETE, list_incomp);
                taglist.appendTag(n);
            }
        }
        //Adding completed quests to nbt
        Iterator<IQuestTemplate> it2 = QuestData.INSTANCE.completedQuests.iterator();
        while(it2.hasNext()) {
            NBTTagList list = new NBTTagList();
            IQuestTemplate quest = it2.next();
            for (int i = 0; i < QuestData.INSTANCE.completedQuests.size(); i++) {
                NBTTagList tag2 = new NBTTagList();
                NBTTagCompound comp_compl = new NBTTagCompound();
                comp_compl.setString(KEY_QUEST, quest.getName());
                NBTTagCompound comp = quest.serializeQuest(this.player);
                comp_compl.setTag(KEY_QUESTS_PROGRESS, comp);
                tag2.appendTag(tag2);
                n.setTag(KEY_QUEST_COMPLETE, tag2);
                list.appendTag(n);
            }
        }
        nbt.setTag(KEY_QUESTS, n);
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        if(nbt.hasKey(KEY_QUESTS)){
            NBTTagCompound n = nbt.getCompoundTag(KEY_QUESTS);
            NBTTagList completedlist = n.getTagList(KEY_QUEST_COMPLETE, Constants.NBT.TAG_COMPOUND);
            for(int i = 0; i < completedlist.tagCount();i++) {
                NBTTagCompound tag = completedlist.getCompoundTagAt(i);
                String name = tag.getString(KEY_QUEST);
                IQuestTemplate quest = QuestData.INSTANCE.getQuestByName(name);
                questsCompleted.add(quest);
            }
            NBTTagList incompletelist = n.getTagList(KEY_QUEST_INCOMPLETE, Constants.NBT.TAG_COMPOUND);
            for(int i = 0; i < incompletelist.tagCount();i++) {
                NBTTagCompound tag = incompletelist.getCompoundTagAt(i);
                String name = tag.getString(KEY_QUEST);
                IQuestTemplate quest = QuestData.INSTANCE.getQuestByName(name);
                questsIncompleted.add(quest);
                if(nbt.hasKey(KEY_QUESTS_PROGRESS)){
                    quest.deserializeQuest(nbt);
                }
            }
        }
    }
}
