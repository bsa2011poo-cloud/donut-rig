package com.ollie.rig;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import com.mojang.blaze3d.platform.InputConstants;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public final class DonutRigClient implements ClientModInitializer {
    public static boolean rigEnabled = false;
    public static String selectedGame = "BLACKJACK";
    public static String selectedPaperRole = "HOST";
    public static final List<ItemStack> localPreview = new ArrayList<>();
    public static BlockPos blackjackDispenser;
    public static BlockPos blackjackLever;
    public static BlockPos hostDispenser;
    public static BlockPos viewerDispenser;
    public static BlockPos paperLever;

    private static KeyMapping toggleKey;
    private static KeyMapping menuKey;
    private static KeyMapping captureKey;

    @Override
    public void onInitializeClient() {
        toggleKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.donutrig.toggle", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_R,
                "category.donutrig"));
        menuKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.donutrig.menu", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_O,
                "category.donutrig"));
        captureKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.donutrig.capture", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_P,
                "category.donutrig"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleKey.consumeClick()) rigEnabled = !rigEnabled;
            while (menuKey.consumeClick()) {
                if (client.player != null) client.setScreen(new RigScreen());
            }
            while (captureKey.consumeClick()) captureLookedAtDispenser(client);
        });
    }

    private static void captureLookedAtDispenser(Minecraft client) {
        if (client.level == null || client.hitResult == null || client.hitResult.getType() != HitResult.Type.BLOCK) return;
        BlockPos pos = ((BlockHitResult) client.hitResult).getBlockPos();
        BlockEntity entity = client.level.getBlockEntity(pos);
        if (!(entity instanceof DispenserBlockEntity dispenser)) return;
        localPreview.clear();
        Container container = dispenser;
        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);
            if (!stack.isEmpty()) localPreview.add(stack.copy());
        }
        switch (selectedGame) {
            case "BLACKJACK" -> blackjackDispenser = pos.immutable();
            case "PAPER" -> {
                if ("HOST".equals(selectedPaperRole)) hostDispenser = pos.immutable();
                else viewerDispenser = pos.immutable();
            }
        }
    }
}
