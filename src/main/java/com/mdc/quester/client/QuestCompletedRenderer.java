package com.mdc.quester.client;

import com.mdc.quester.capability.quest.ICapQuests;
import com.mdc.quester.player.QuesterCapability;
import com.mdc.quester.quests.QuestHelper;
import com.mdc.quester.registry.QuestData;
import com.mdc.quester.templates.IQuestTemplate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class QuestCompletedRenderer extends QuestRenderer{
    private static final String LANG = "gui.quest.";
    private String name;
    private ItemStack displayIcon;

    public QuestCompletedRenderer(Minecraft mc) {
        this(mc, null, ItemStack.EMPTY);
    }

    public QuestCompletedRenderer(Minecraft mc, String name, ItemStack displayIcon){
        super();
        xSize = 192;
        ySize = 191;
        this.name = name;
        this.mc = mc;
        this.fontRendererObj = mc.fontRendererObj;
        this.displayIcon = displayIcon;
    }

    @Override
    public void initGui() {
        guiImage = new ResourceLocation("textures/gui/toasts.png");
        mc.getTextureManager().bindTexture(guiImage);
        drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 95, this.xSize, this.ySize);
        this.mc.getRenderItem().renderItemIntoGUI(displayIcon, this.guiLeft + 10, this.guiTop + 10);
        drawText();
    }

    public QuestCompletedRenderer setLocation(int x, int y){
        this.guiLeft = x;
        this.guiTop = y;
        return this;
    }

    public QuestCompletedRenderer setSize(int x, int y){
        this.xSize = x;
        this.ySize = y;
        return this;
    }

    @Override
    protected void drawExtraBg() {
        int iconY = (guiTop - 18);
        int iconX = (guiLeft - 18);
        drawTexturedModalRect(guiLeft + 18, guiTop + 18, iconX, iconY, 32, 32);
    }

    @Override
    public void drawText() {
        if(this.name == null) return;
        this.drawCenteredString(name + " " + TextFormatting.GREEN + "completed!", guiLeft + fontRendererObj.getStringWidth(name) + (xSize/2) - 20, guiTop + (ySize/3) + 3, 0xaaaaaa);
    }
}
