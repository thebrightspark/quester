package com.mdc.quester.client;

public class QuestPageRenderer extends QuestRenderer{
    @Override
    public void initGui() {
        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString("Quests", this.width/2, 15, 0xffffff, true);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void drawBackground(int tint) {
        super.drawBackground(tint);
    }
}
