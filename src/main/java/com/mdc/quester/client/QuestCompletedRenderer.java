package com.mdc.quester.client;

import com.mdc.quester.capability.quest.ICapQuests;
import com.mdc.quester.player.QuesterCapability;
import com.mdc.quester.quests.QuestHelper;
import com.mdc.quester.registry.QuestData;
import com.mdc.quester.templates.IQuestTemplate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class QuestCompletedRenderer extends QuestRenderer{
    private static final String LANG = "gui.quest.";
    private IQuestTemplate quest;

    public QuestCompletedRenderer(EntityPlayerMP player){
        super();
        updateQuest(player);
        xSize = 192;
        ySize = 191;
    }

    public void updateQuest(EntityPlayerMP player){
        EntityPlayerMP p = player;
        ICapQuests icap = QuestHelper.INSTANCE.getQuestCapability(p);
        quest = icap.getLastCompletedQuest();
    }

    /*private boolean isItemSubmitQuest(){
        for(IQuestTemplate temp : QuestData.INSTANCE.completedQuests) {
            return quest != null && quest.getName().equals(temp.getName());
        }
        return false;
    }*/

    @Override
    public void initGui() {
        super.initGui();
    }

    @Override
    protected void drawExtraBg() {
        if(quest == null) return;
        int iconY = (guiTop - 18);
        int iconX = (guiLeft - 18);
        drawTexturedModalRect(guiLeft + 18, guiTop + 18, iconX, iconY, 32, 32);
    }

    @Override
    protected void drawText() {
        if(quest == null) return;
        wrapText(quest.getName(), 52 + guiLeft, 19 + guiTop, 85, 0, false);
        wrapText("Completed!", 17 + guiLeft, 54 + guiTop, 160, 0, false);
    }
}
