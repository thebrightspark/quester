package com.mdc.quester.client;

import com.mdc.quester.Quester;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public class QuestRenderer extends GuiScreen {
    protected static ResourceLocation guiImage;
    protected int xSize = 176;
    protected int ySize = 168;
    protected int guiLeft, guiTop;

    public QuestRenderer(){
        guiImage = new ResourceLocation("textures/gui/advancements/backgrounds/adventure.png");
    }

    @Override
    public void initGui() {
        guiLeft = (width - xSize) / 2;
        guiTop = (height - ySize) / 2;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        mc.getTextureManager().bindTexture(guiImage);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        drawExtraBg();

        super.drawScreen(mouseX, mouseY, partialTicks);

        drawText();

        List<String> tooltip = new ArrayList<>();
        drawTooltips(tooltip, mouseX, mouseY);
        if(!tooltip.isEmpty()){
            drawHoveringText(tooltip, mouseX, mouseY);
        }
    }

    protected void drawExtraBg(){}

    protected void drawText(){}

    protected void drawTooltips(List<String> tooltip, int mouseX, int mouseY){}

    protected int wrapText(String text, int x, int y, int width, int color, boolean drawShadow){
        String[] textArray = text.split(" ");
        String line = null;
        int lineNum = 0;
        int lineHeight = fontRendererObj.FONT_HEIGHT;
        for(String s : textArray){
            if(s.equals("\n")){
                drawString(line == null ? "" : line, x, y + (lineNum++ * lineHeight), color, drawShadow);
                line = null;
                continue;
            }
            if(line == null){
                line = s;
            }else{
                if(fontRendererObj.getStringWidth(line + s) <= width){
                    line += " " + s;
                }else{
                    drawString(fontRendererObj, line, x, y + (lineNum++ * lineHeight), color);
                    line = s;
                }
            }
        }
        if(line != null){
            drawString(fontRendererObj, line, x, y + (lineNum++ * lineHeight), color);
        }
        return lineNum + 1;
    }

    public void drawString(String text, int x, int y, int color, boolean drawShadow){
        fontRendererObj.drawString(text, x, y, color, drawShadow);
    }

    public void drawString(String text, int x, int y, int color){
        drawString(text, x, y, color, false);
    }

    public void drawStringWithShadow(String text, int x, int y, int color){
        drawString(text, x, y, color, true);
    }

    public void drawCenteredString(String text, int x, int y, int color, boolean drawShadow){
        drawString(text, (x - fontRendererObj.getStringWidth(text) / 2), y, color, drawShadow);
    }

    public void drawCenteredString(String text, int x, int y, int color){
        drawCenteredString(text, x, y, color, false);
    }

    public void drawCenteredStringWithShadow(String text, int x, int y, int color){
        drawCenteredString(text, x, y, color, true);
    }
}
