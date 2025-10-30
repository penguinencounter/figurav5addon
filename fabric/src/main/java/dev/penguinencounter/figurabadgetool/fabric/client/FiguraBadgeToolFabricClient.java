package dev.penguinencounter.figurabadgetool.fabric.client;

import dev.penguinencounter.figurabadgetool.FiguraBadgeTool;
import net.fabricmc.api.ClientModInitializer;

public final class FiguraBadgeToolFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FiguraBadgeTool.init();
    }
}
