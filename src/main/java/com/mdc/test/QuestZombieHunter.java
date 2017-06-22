package com.mdc.test;

import com.mdc.quester.templates.IQuestTemplate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class QuestZombieHunter implements IQuestTemplate<QuestZombieHunter> {
    private static boolean isTriggered = false;

    @Override
    public String getName() {
        return "Zombie Hunter";
    }

    @Override
    public NBTTagCompound serializeQuest(EntityPlayer player) {
        NBTTagCompound nbt = player.getEntityData();

        nbt.setBoolean("progress", isTriggered);

        return nbt;
    }

    @Override
    public void deserializeQuest(NBTTagCompound comp) {
        if(comp.hasKey("progress")){
            isTriggered = comp.getBoolean("progress");
        }
    }

    public static void setTriggered(boolean triggered) {
        isTriggered = triggered;
    }

    @Override
    public boolean triggered(EntityPlayer player, World world, BlockPos pos) {
        return isTriggered;
    }
}
