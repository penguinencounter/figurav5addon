package dev.penguinencounter.figurav5addon.duck;

import dev.penguinencounter.figurav5addon.BlockbenchParser2;
import org.figuramc.figura.parsers.AvatarMetadataParser;

public class locals {
    public static final ThreadLocal<BlockbenchParser2> activeParser = ThreadLocal.withInitial(() -> null);
    public static final ThreadLocal<AvatarMetadataParser.Metadata> earlyMetadata = ThreadLocal.withInitial(() -> null);
}
