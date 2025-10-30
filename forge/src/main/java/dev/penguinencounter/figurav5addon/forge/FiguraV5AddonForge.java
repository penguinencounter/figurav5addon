package dev.penguinencounter.figurav5addon.forge;

import dev.penguinencounter.figurav5addon.FiguraV5Addon;
import net.minecraftforge.fml.common.Mod;

@Mod(FiguraV5Addon.MOD_ID)
public final class FiguraV5AddonForge {
    public FiguraV5AddonForge() {
        // Run our common setup.
        FiguraV5Addon.init();
    }
}
