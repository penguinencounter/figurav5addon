package dev.penguinencounter.figurav5addon;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.figuramc.figura.animation.Keyframe;
import org.figuramc.figura.avatar.local.LocalAvatarLoader;
import org.figuramc.figura.model.FiguraModelPart;
import org.figuramc.figura.model.FiguraModelPartReader;
import org.figuramc.figura.parsers.AvatarMetadataParser;
import org.figuramc.figura.parsers.BlockbenchModelParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Environment(EnvType.CLIENT)
public final class FiguraV5Addon {
    public static final String MOD_ID = "figurav5addon";
    public static final Logger LOGGER = LoggerFactory.getLogger(FiguraV5Addon.class);

    public static void init() {
        LOGGER.info("FiguraV5Addon is here");

        bumpClasses();
    }

    public static BlockbenchModelParser.ModelData adapt(ModelParseResult result) {
        return new BlockbenchModelParser.ModelData(result.textures(), result.animationList(), result.modelNbt());
    }

    @SuppressWarnings("unused")
    private static void bumpClasses() {
        // make mixin wake up and do the work ON THREAD
        Class<Keyframe> keyframeClass = Keyframe.class;
        Class<FiguraModelPartReader> figuraModelPartReaderClass = FiguraModelPartReader.class;
        Class<LocalAvatarLoader> localAvatarLoaderClass = LocalAvatarLoader.class;
        Class<FiguraModelPart> figuraModelPartClass = FiguraModelPart.class;
        Class<AvatarMetadataParser.Metadata> metadataClass = AvatarMetadataParser.Metadata.class;
    }
}
