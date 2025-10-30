package dev.penguinencounter.figurav5addon.fabric.client;

import dev.penguinencounter.figurav5addon.FiguraV5Addon;
import net.fabricmc.api.ClientModInitializer;

public final class FiguraV5AddonFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FiguraV5Addon.init();
    }
}
