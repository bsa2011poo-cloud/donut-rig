package com.ollie.rig;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public final class RigScreen extends Screen {
    public RigScreen() { super(Component.literal("Donut SMP Rig")); }

    @Override
    protected void init() {
        int cx = this.width / 2;
        int y = this.height / 2 - 45;
        addRenderableWidget(Button.builder(Component.literal("Rig: " + (DonutRigClient.rigEnabled ? "ON" : "OFF")), b -> {
            DonutRigClient.rigEnabled = !DonutRigClient.rigEnabled;
            b.setMessage(Component.literal("Rig: " + (DonutRigClient.rigEnabled ? "ON" : "OFF")));
        }).bounds(cx - 100, y, 200, 20).build());
        addRenderableWidget(Button.builder(Component.literal("Game: " + DonutRigClient.selectedGame), b -> {
            DonutRigClient.selectedGame = DonutRigClient.selectedGame.equals("BLACKJACK") ? "PAPER" : "BLACKJACK";
            b.setMessage(Component.literal("Game: " + DonutRigClient.selectedGame));
        }).bounds(cx - 100, y + 25, 200, 20).build());
        addRenderableWidget(Button.builder(Component.literal("Paper target: " + DonutRigClient.selectedPaperRole), b -> {
            DonutRigClient.selectedPaperRole = DonutRigClient.selectedPaperRole.equals("HOST") ? "VIEWER" : "HOST";
            b.setMessage(Component.literal("Paper target: " + DonutRigClient.selectedPaperRole));
        }).bounds(cx - 100, y + 50, 200, 20).build());
        addRenderableWidget(Button.builder(Component.literal("Done"), b -> onClose())
                .bounds(cx - 100, y + 80, 200, 20).build());
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        renderBackground(graphics, mouseX, mouseY, delta);
        graphics.drawCenteredString(font, title, width / 2, height / 2 - 75, 0xFFFFFF);
        graphics.drawCenteredString(font, "P: copy looked-at dispenser contents locally", width / 2, height / 2 + 65, 0xAAAAAA);
        graphics.drawCenteredString(font, "R: toggle Rig | O: open this menu", width / 2, height / 2 + 78, 0xAAAAAA);
        super.render(graphics, mouseX, mouseY, delta);
    }
}
