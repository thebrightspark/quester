package com.mdc.quester.templates;

import com.mdc.quester.Quester;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import com.mdc.quester.utils.NBTUtils;

/**
 * When creating a new quest, implement this into a new class and place the class name into the type parameter.<br>
 * Example:<br>
 * <pre>public class QuestSmeltIron implements IQuestTemplate<QuestSmeltIron>{</pre>
 * @author Alex Couch
 */
public interface IQuestTemplate<T> {
    /**
     * This is an internal use only. You do not need to override this.<br>
     * This is to get the type that is implementing this template.<br>
     * @return the type that is implementing this interface
     * @author Alex Couch
     * @since 0.1.0
     */
    default IQuestTemplate<T> getT(IQuestTemplate<T> t){
        return t;
    }

    /**
     * When you register a quest and set a name for the quest, this method can be used to separate different names.
     * It is currently not recommended to make names in the minecraft unlocalized format like:<br>
     * <b>quest_name</b><br>
     * It is more like this:<br>
     * <b>Quest Name</b><br>
     * @return the name of the quest
     * @since 0.1.0
     * @author Alex Couch
     */
    String getName();

    /**
     * This method is used to get the page that the quest is registered to.<br>
     * This is not fully implemented yet, so don't worry about this!
     * @return This will return the page that is implementing {@link IQuestPageTemplate}
     * @author Alex Couch
     * @since 0.1.0
     */
    default IQuestPageTemplate<?> getPage(){
        return null;
    }

    /**
     * This method must always be implemented at all times in order to make any quest work correctly.<br>
     * You can use the given parameters to make your trigger more complex.<br>
     * Here is an example:<br>
     * <pre>
     *  public boolean triggered(EntityPlayer player, World world, BlockPos pos){
     *      return player.inventory.hasItemStack(new ItemStack(Item.getItemFromBlock(Blocks.LOG)));
     *  }
     * </pre>
     * @return what triggers this quest
     * @author Alex Couch
     * @since 0.1.0
     */
    boolean triggered(EntityPlayer player, World world, BlockPos pos);

    /**
     * Use this to serialize the progress of the quest so that if a player is halfway done with a quest and they leave the world,<br>
     * then it doesn't reset the quest to null progress due to the fact that the quest was never triggered. You can use<br>
     * {@link NBTUtils} to see a list of <code>NBT KEYS</code> that <code>Quester</code> is using to serialize all quests.
     * @param player the player instance on the client side
     * @return an nbt tag compound that contains the progress of the quest
     * @since 0.7.0
     * @author Alex Couch
     */
    NBTTagCompound serializeQuest(EntityPlayer player);

    /**
     * Use this to deserialize the progress of the quest so that when the player logs back into a world, the quest gets set
     * to it's appropriate progress value. Please refer to {@link NBTUtils} for a list of <code>NBT KEYS</code> that Quester
     * is using to deserialize the quests.
     * @param comp the nbt tag compound that is being deserialized
     * @since 0.7.0
     * @author Alex Couch
     */
    void deserializeQuest(NBTTagCompound comp);

    /**
     * Use this for debugging so that when you use the command <code>/quester reset</code>, the quest gets reset appropriately.
     * @since 0.8.0
     * @author Alex Couch
     */
    void resetQuest();
}
