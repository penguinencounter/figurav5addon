package dev.penguinencounter.figurav5addon.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.penguinencounter.figurav5addon.BlockbenchParser2;
import dev.penguinencounter.figurav5addon.FiguraV5Addon;
import dev.penguinencounter.figurav5addon.ModelParseResult;
import dev.penguinencounter.figurav5addon.duck.AvatarMetadataOverlay;
import dev.penguinencounter.figurav5addon.duck.PrivateAccess;
import dev.penguinencounter.figurav5addon.duck.locals;
import org.figuramc.figura.avatar.UserData;
import org.figuramc.figura.avatar.local.LocalAvatarLoader;
import org.figuramc.figura.parsers.AvatarMetadataParser;
import org.figuramc.figura.parsers.BlockbenchModelParser;
import org.figuramc.figura.utils.IOUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Mixin(value = LocalAvatarLoader.class)
public abstract class LocalAvatarLoaderMixin {
    @Inject(
            method = "lambda$loadAvatar$2",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/figuramc/figura/avatar/local/LocalAvatarLoader;loadModels(Ljava/nio/file/Path;Ljava/nio/file/Path;Lorg/figuramc/figura/parsers/BlockbenchModelParser;Lnet/minecraft/nbt/CompoundTag;Lnet/minecraft/nbt/ListTag;Ljava/lang/String;)Lnet/minecraft/nbt/CompoundTag;"
            )
    )
    private static void figurav5$createParser(Path finalPath, UserData target, CallbackInfo ci) {
        locals.activeParser.set(new BlockbenchParser2());
    }

    @Inject(
            method = "lambda$loadAvatar$2",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/figuramc/figura/avatar/local/LocalAvatarLoader;loadSounds(Ljava/nio/file/Path;Lnet/minecraft/nbt/CompoundTag;)V",
                    shift = At.Shift.AFTER
            )
    )
    private static void figurav5$preloadMetadata(Path finalPath, UserData target, CallbackInfo ci) throws IOException {
        try {
            PrivateAccess.LocalAvatarLoader$loadState$f.set(null, PrivateAccess.LoadState$METADATA);
        } catch (IllegalAccessException e) {
            FiguraV5Addon.LOGGER.error("Failed to update load state", e);
        }
        // note: avatar.jsonc is not out yet
        // ...but we support it anyway in case this is being misapplied I suppose
        // the other validator still exists
        Path avatarJson = finalPath.resolve("avatar.json");
        Path avatarJsonc = finalPath.resolve("avatar.jsonc");
        final String _meta;
        if (Files.exists(avatarJsonc)) {
            _meta = IOUtils.readFile(avatarJsonc);
        } else {
            _meta = IOUtils.readFile(avatarJson);
        }
        locals.earlyMetadata.set(AvatarMetadataParser.read(_meta));
    }

    @WrapOperation(
            method = "loadModels",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/figuramc/figura/parsers/BlockbenchModelParser;parseModel(Ljava/nio/file/Path;Ljava/nio/file/Path;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lorg/figuramc/figura/parsers/BlockbenchModelParser$ModelData;"
            ),
            remap = false
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
        ModelParseResult result = locals.activeParser.get()
                .parseModel(
                        avatarFolder,
                        sourceFile,
                        json,
                        modelName,
                        folders,
                        ((AvatarMetadataOverlay) locals.earlyMetadata.get()).figurav5$getLoadOptions()
                );
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
    private static void figurav5$clean(Path finalPath, UserData target, CallbackInfo ci) {
        locals.earlyMetadata.remove();
        locals.activeParser.remove();
    }
}
