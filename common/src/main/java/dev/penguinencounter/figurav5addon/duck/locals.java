package dev.penguinencounter.figurav5addon.duck;

import dev.penguinencounter.figurav5addon.BlockbenchParser2;
import org.figuramc.figura.animation.TransformType;
import org.figuramc.figura.model.FiguraModelPart;
import org.figuramc.figura.parsers.AvatarMetadataParser;

public class locals {
    public static final ThreadLocal<BlockbenchParser2> LocalAvatarLoader$activeParser = ThreadLocal.withInitial(() -> null);
    public static final ThreadLocal<AvatarMetadataParser.Metadata> LocalAvatarLoader$earlyMetadata = ThreadLocal.withInitial(() -> null);
    public static final ThreadLocal<Byte> ModelPart$formatVersion = ThreadLocal.withInitial(() -> null);
    public static final ThreadLocal<FiguraModelPart> Keyframe$part = ThreadLocal.withInitial(() -> null);
    public static final ThreadLocal<TransformType> Keyframe$channel = ThreadLocal.withInitial(() -> null);
}
