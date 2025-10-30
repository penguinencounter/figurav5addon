package dev.penguinencounter.figurav5addon;

public class V5AddonState {
    public static final ThreadLocal<BlockbenchParser2> activeParser = ThreadLocal.withInitial(() -> null);
}
