package com.mdc.quester.quests;

import com.mdc.quester.interfaces.IQuestTemplate;
import com.mdc.quester.registry.QuestData;
import com.mdc.quester.registry.QuestRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CompleteQuest {
    public static boolean complete(EntityPlayer player, World world, BlockPos pos){
        for(IQuestTemplate quest : QuestRegistry.quests){
            return quest.triggered(player, world, pos);
        }
        return false;
    }
}
