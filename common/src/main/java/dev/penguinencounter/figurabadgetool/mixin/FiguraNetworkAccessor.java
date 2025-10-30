package dev.penguinencounter.figurabadgetool.mixin;

import org.figuramc.figura.backend2.HttpAPI;
import org.figuramc.figura.backend2.NetworkStuff;
import org.jetbrains.annotations.Contract;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.net.http.HttpRequest;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;

@Mixin(value = NetworkStuff.class, remap = false)
public interface FiguraNetworkAccessor {
    @Invoker
    @Contract // No contract.
    static void invokeQueueString(UUID owner, Function<HttpAPI, HttpRequest> request, BiConsumer<Integer, String> consumer) {
        throw new AssertionError();
    }
}
