package com.mdc.test;

import com.mdc.quester.templates.IQuestTemplate;
import com.mdc.quester.utils.NBTUtils;
import com.mdc.test.handler.EventHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

import static com.mdc.quester.utils.NBTUtils.*;

import static com.mdc.quester.utils.NBTUtils.KEY_QUEST_PROGRESS;

public class QuestGetWood implements IQuestTemplate<QuestGetWood> {
    private static boolean isTriggered = false;

    @Override
    public String getName() {
        return "Get Wood";
    }

    @Override
    public NBTTagCompound serializeQuest(EntityPlayer player) {
        NBTTagCompound tag = player.getEntityData();
        if(!tag.hasKey("progress")){
            tag.setString("progress", String.valueOf(isTriggered));
        }
        return tag;
    }

    @Override
    public void deserializeQuest(NBTTagCompound comp) {
        if(comp.hasKey(KEY_QUEST_PROGRESS)){
            isTriggered = Boolean.getBoolean(KEY_QUEST_PROGRESS);
        }
    }

    @Override
    public void resetQuest() {
        isTriggered = false;
    }

    @Override
    public ItemStack getDisplayIcon() {
        return new ItemStack(Item.getItemFromBlock(Blocks.LOG));
    }

    @Override
    public boolean triggered(EntityPlayer player, World world, BlockPos pos) {
        if(player.inventory.hasItemStack(new ItemStack(Item.getItemFromBlock(Blocks.LOG)))){
            isTriggered = true;
            return true;
        }
        return false;
    }
}
