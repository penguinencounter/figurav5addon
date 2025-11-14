package dev.penguinencounter.figurav5addon.duck;

import org.figuramc.figura.avatar.local.LocalAvatarLoader;

import java.lang.reflect.Field;

public class PrivateAccess {
    public static final Object LoadState$METADATA;
    public static final Field LocalAvatarLoader$loadState$f;

    static {
        try {
            Class<?> LoadState$c = Class.forName("org.figuramc.figura.avatar.local.LocalAvatarLoader$LoadState");
            Field LoadState$METADATA$f = LoadState$c.getDeclaredField("METADATA");
            LoadState$METADATA$f.setAccessible(true);
            LoadState$METADATA = LoadState$METADATA$f.get(null);

            LocalAvatarLoader$loadState$f = LocalAvatarLoader.class.getDeclaredField("loadState");
            LocalAvatarLoader$loadState$f.setAccessible(true);
        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException("Failed to access Figura private types! This is a bug in figurav5addon (or your Figura version is incompatible), please report it!", e);
        }
    }
}
