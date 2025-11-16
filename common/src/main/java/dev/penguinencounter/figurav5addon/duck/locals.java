package dev.penguinencounter.figurav5addon.duck;

import dev.penguinencounter.figurav5addon.BlockbenchParser2;
import org.figuramc.figura.parsers.AvatarMetadataParser;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

public class locals {
    public static final ThreadLocal<BlockbenchParser2> activeParser = ThreadLocal.withInitial(() -> null);
    public static final ThreadLocal<AvatarMetadataParser.Metadata> earlyMetadata = ThreadLocal.withInitial(() -> null);
    public static final ThreadLocal<Byte> formatVersionArg = ThreadLocal.withInitial(() -> null);
}
