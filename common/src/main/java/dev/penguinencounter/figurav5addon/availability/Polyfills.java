package dev.penguinencounter.figurav5addon.availability;

import net.minecraft.nbt.CompoundTag;

import java.util.Optional;

@SuppressWarnings({"unchecked", "ConstantValue", "OptionalGetWithoutIsPresent"})
public class Polyfills {
    public static boolean CompoundTag_getBoolean(CompoundTag $this, String key) {
        Object result = $this.getBoolean(key);
        // on older versions, this is just a boolean
        if (result instanceof Boolean) return (Boolean) result;
        if (result instanceof Optional<?>) return ((Optional<Boolean>) result).get();
        throw new IllegalStateException(String.format(
                "Unexpected return type for getBoolean. This is a bug in figurav5addon; please report it and include your game log!\n" +
                        "result type is %s, $this is %s, key is '%s'",
                result.getClass().getName(),
                $this.getClass().getName(),
                key
        ));
    }
    public static byte CompoundTag_getByte(CompoundTag $this, String key) {
        Object result = $this.getByte(key);
        // on older versions, this is just a boolean
        if (result instanceof Byte) return (Byte) result;
        if (result instanceof Optional<?>) return ((Optional<Byte>) result).get();
        throw new IllegalStateException(String.format(
                "Unexpected return type for getByte. This is a bug in figurav5addon; please report it and include your game log!\n" +
                        "result type is %s, $this is %s, key is '%s'",
                result.getClass().getName(),
                $this.getClass().getName(),
                key
        ));
    }
}
