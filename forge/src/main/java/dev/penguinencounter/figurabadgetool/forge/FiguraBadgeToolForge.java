package dev.penguinencounter.figurabadgetool.neoforge;

import dev.penguinencounter.figurabadgetool.FiguraBadgeTool;
import net.minecraftforge.fml.common.Mod;

@Mod(FiguraBadgeTool.MOD_ID)
public final class FiguraBadgeToolForge {
    public FiguraBadgeToolForge() {
        // Run our common setup.
        FiguraBadgeTool.init();
    }
}
