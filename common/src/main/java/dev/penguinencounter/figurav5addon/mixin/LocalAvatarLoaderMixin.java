package dev.penguinencounter.figurav5addon.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.penguinencounter.figurav5addon.BlockbenchParser2;
import dev.penguinencounter.figurav5addon.FiguraV5Addon;
import dev.penguinencounter.figurav5addon.ModelParseResult;
import dev.penguinencounter.figurav5addon.V5AddonState;
import org.figuramc.figura.avatar.UserData;
import org.figuramc.figura.avatar.local.LocalAvatarLoader;
import org.figuramc.figura.parsers.BlockbenchModelParser;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Group;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.nio.file.Path;

@Mixin(value = LocalAvatarLoader.class)
public abstract class LocalAvatarLoaderMixin {
    // 1.20
    @Inject(
            method = "lambda$loadAvatar$2",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/figuramc/figura/avatar/local/LocalAvatarLoader;loadModels(Ljava/nio/file/Path;Ljava/nio/file/Path;Lorg/figuramc/figura/parsers/BlockbenchModelParser;Lnet/minecraft/nbt/CompoundTag;Lnet/minecraft/nbt/ListTag;Ljava/lang/String;)Lnet/minecraft/nbt/CompoundTag;"
            )
    )
    @Group(name = "createParser", min = 1)
    private static void figurav5$createParser1(Path finalPath, UserData target, CallbackInfo ci) {
        V5AddonState.activeParser.set(new BlockbenchParser2());
    }

    @WrapOperation(
            method = "loadModels",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/figuramc/figura/parsers/BlockbenchModelParser;parseModel(Ljava/nio/file/Path;Ljava/nio/file/Path;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lorg/figuramc/figura/parsers/BlockbenchModelParser$ModelData;"
            )
    )
    private static BlockbenchModelParser.ModelData figurav5$replaceParser(
            BlockbenchModelParser instance,
            Path avatarFolder,
            Path sourceFile,
            String json,
            String modelName,
            String folders,
            Operation<BlockbenchModelParser.ModelData> original
    ) throws Exception {
        ModelParseResult result = V5AddonState.activeParser.get()
                .parseModel(avatarFolder, sourceFile, json, modelName, folders);
        return FiguraV5Addon.adapt(result);
    }

    // 1.20
    @Inject(
            method = "lambda$loadAvatar$2",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/figuramc/figura/avatar/local/LocalAvatarLoader;loadModels(Ljava/nio/file/Path;Ljava/nio/file/Path;Lorg/figuramc/figura/parsers/BlockbenchModelParser;Lnet/minecraft/nbt/CompoundTag;Lnet/minecraft/nbt/ListTag;Ljava/lang/String;)Lnet/minecraft/nbt/CompoundTag;",
                    shift = At.Shift.AFTER
            )
    )
    @Group(name = "discardParser", min = 1)
    private static void figurav5$discardParser1(Path finalPath, UserData target, CallbackInfo ci) {
        V5AddonState.activeParser.remove();
    }
}
