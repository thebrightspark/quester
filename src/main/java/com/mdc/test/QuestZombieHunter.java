package com.mdc.test;

import com.mdc.quester.templates.IQuestTemplate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class QuestZombieHunter implements IQuestTemplate<QuestZombieHunter> {
    private boolean isTriggered = false;

    @Override
    public String getName() {
        return "Zombie Hunter";
    }

    @Override
    public void setTriggered(boolean triggered) {
        this.isTriggered = triggered;
    }

    @Override
    public boolean triggered(EntityPlayer player, World world, BlockPos pos) {
        return isTriggered;
    }
}
