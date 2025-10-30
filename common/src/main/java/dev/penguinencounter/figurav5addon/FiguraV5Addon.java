package dev.penguinencounter.figurav5addon;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.figuramc.figura.parsers.BlockbenchModelParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Environment(EnvType.CLIENT)
public final class FiguraV5Addon {
    public static final String MOD_ID = "figurav5addon";
    public static final Logger LOGGER = LoggerFactory.getLogger(FiguraV5Addon.class);

    public static void init() {
        LOGGER.info("FiguraV5Addon is here");
    }

    public static BlockbenchModelParser.ModelData adapt(ModelParseResult result) {
        return new BlockbenchModelParser.ModelData(result.textures(), result.animationList(), result.modelNbt());
    }
}
