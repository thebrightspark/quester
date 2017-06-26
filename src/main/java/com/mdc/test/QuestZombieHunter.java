package com.mdc.test;

import com.mdc.quester.templates.IQuestTemplate;
import com.mdc.test.handler.EventHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

import static com.mdc.quester.utils.NBTUtils.*;

public class QuestZombieHunter implements IQuestTemplate<QuestZombieHunter> {
    private static boolean isTriggered = false;

    @Override
    public String getName() {
        return "Zombie Hunter";
    }

    @Override
    public NBTTagCompound serializeQuest(EntityPlayer player) {
        NBTTagCompound nbt = player.getEntityData();

        nbt.setString(KEY_QUEST_PROGRESS, String.valueOf(isTriggered));

        return nbt;
    }

    @Override
    public void deserializeQuest(NBTTagCompound comp) {
        if(comp.hasKey(KEY_QUEST_PROGRESS)){
            isTriggered = Boolean.valueOf(comp.getString(KEY_QUEST_PROGRESS));
        }
    }

    @Override
    public void resetQuest() {
        EventHandler.zombiesKilled = 0;
        isTriggered = false;
    }

    @Override
    public ItemStack getDisplayIcon() {
        return new ItemStack(Items.DIAMOND_SWORD);
    }

    public static void setTriggered(boolean triggered) {
        isTriggered = triggered;
    }

    @Override
    public boolean triggered(EntityPlayer player, World world, BlockPos pos) {
        return isTriggered;
    }
}
