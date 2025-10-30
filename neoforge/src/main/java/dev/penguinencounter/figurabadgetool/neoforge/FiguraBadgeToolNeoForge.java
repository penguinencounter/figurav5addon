package dev.penguinencounter.figurabadgetool.neoforge;

import dev.penguinencounter.figurabadgetool.FiguraBadgeTool;
import net.neoforged.fml.common.Mod;

@Mod(FiguraBadgeTool.MOD_ID)
public final class FiguraBadgeToolNeoForge {
    public FiguraBadgeToolNeoForge() {
        // Run our common setup.
        FiguraBadgeTool.init();
    }
}
