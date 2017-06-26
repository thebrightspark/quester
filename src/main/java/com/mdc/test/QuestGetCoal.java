package com.mdc.test;

import com.mdc.quester.templates.IQuestTemplate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

public class QuestGetCoal implements IQuestTemplate<QuestGetCoal>{
    private static boolean isTriggered = false;

    @Override
    public String getName() {
        return "Get Coal";
    }

    @Override
    public NBTTagCompound serializeQuest(EntityPlayer player) {
        NBTTagCompound nbt = player.getEntityData();
        nbt.setString("progress", String.valueOf(isTriggered));
        return nbt;
    }

    @Override
    public void deserializeQuest(NBTTagCompound comp) {
        if(comp.hasKey("progress")){
            isTriggered = Boolean.getBoolean(comp.getString("progress"));
        }
    }

    @Override
    public ItemStack getDisplayIcon() {
        return new ItemStack(Items.COAL);
    }

    @Override
    public void resetQuest() {
        isTriggered = false;
    }

    public static void setTriggered(boolean triggered){
        isTriggered = triggered;
    }

    @Override
    public boolean triggered(EntityPlayer player, World world, BlockPos pos) {
        return isTriggered;
    }
}
