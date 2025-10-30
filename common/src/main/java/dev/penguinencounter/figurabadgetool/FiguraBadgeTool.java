package dev.penguinencounter.figurabadgetool;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Environment(EnvType.CLIENT)
public final class FiguraBadgeTool {
    public static final String MOD_ID = "figurabadgetool";
    public static final Logger LOGGER = LoggerFactory.getLogger(FiguraBadgeTool.class);

    public static void init() {
        LOGGER.info("FiguraBadgeTool - common :)");
    }
}
