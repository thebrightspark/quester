package com.mdc.quester.interfaces;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created by Alex on 6/11/2017.
 */
public interface IQuestTemplate<T> {
    /**
     * Implement this so that the registry can identify what is being registered
     * @return the type that is implementing this interface
     */
    default IQuestTemplate<T> getT(IQuestTemplate<T> t){
        return t;
    }

    /**
     * When you register a quest and set a name for the quest, this method can be used to separate different names.
     * @return the name of the quest
     */
    String getName();

    /**
     * This method is used to get the page that the quest is registered to.
     * @return This will return the page that is implementing {@link IQuestPageTemplate}
     */
    IQuestPageTemplate<?> getPage();

    /**
     *
     * @return what triggers this quest
     */
    boolean triggered(EntityPlayer player, World world, BlockPos pos);
}
