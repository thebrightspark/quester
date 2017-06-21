package com.mdc.quester.templates;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

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
     * <p>This method can be overrided to set the quest to be <code>triggered</code> from an external class, such as an
     * event handler. You need to call this by instantiating a new instance of the quest and set it through that.
     * You will need to set a private boolean field in your quest class to be called by the <code>triggered</code> method.</p><br>
     * An example of this is shown here:
     * <pre>
     * private static boolean isTriggered = false;
     *
     * public void setTriggered(boolean triggered){
     * isTriggered = triggered;
     * }
     * </pre>
     * This is how it should be done normally, only when needed.
     *
     * @param triggered whether the quest is triggered from an external class (like an event handler)
     * @author Alex Couch
     * @see IQuestTemplate#triggered(EntityPlayer, World, BlockPos)
     * @since 0.6.7
     * @deprecated currently deprecated. Create your own method and call it through a static reference
     */
    //@Deprecated
    //default void setTriggered(boolean triggered){}
}
