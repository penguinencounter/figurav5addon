package dev.penguinencounter.figurav5addon.mixin;

import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalByteRef;
import dev.penguinencounter.figurav5addon.BlockbenchCommonTypes;
import dev.penguinencounter.figurav5addon.duck.locals;
import net.minecraft.nbt.CompoundTag;
import org.figuramc.figura.avatar.Avatar;
import org.figuramc.figura.model.FiguraModelPart;
import org.figuramc.figura.model.FiguraModelPartReader;
import org.figuramc.figura.model.rendering.texture.FiguraTextureSet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(FiguraModelPartReader.class)
public class FiguraModelPartReaderMixin {
    @Inject(
            method = "read",
            at = @At("HEAD")
    )
    private static void figurav5$getFormatVersion(Avatar owner,
                                      CompoundTag partCompound,
                                      List<FiguraTextureSet> textureSets,
                                      boolean smoothNormals,
                                      CallbackInfoReturnable<FiguraModelPart> cir,
                                      @Share("formatVersion") LocalByteRef formatVersion) {
        Byte inheritedFormatVersion = locals.formatVersionArg.get();
        formatVersion.set(
                partCompound.contains("_v") ? partCompound.getByte("_v") :
                        inheritedFormatVersion == null ? BlockbenchCommonTypes.FORMAT_V4 : inheritedFormatVersion
        );
    }

    @Inject(
            method = "read",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/figuramc/figura/model/FiguraModelPartReader;read(Lorg/figuramc/figura/avatar/Avatar;Lnet/minecraft/nbt/CompoundTag;Ljava/util/List;Z)Lorg/figuramc/figura/model/FiguraModelPart;"
            )
    )
    private static void figurav5$passFormatVersion(Avatar owner,
                                                   CompoundTag partCompound,
                                                   List<FiguraTextureSet> textureSets,
                                                   boolean smoothNormals,
                                                   CallbackInfoReturnable<FiguraModelPart> cir,
                                                   @Share("formatVersion") LocalByteRef formatVersion) {
        locals.formatVersionArg.set(formatVersion.get());
    }

    @Inject(
            method = "read",
            at = @At("RETURN")
    )
    private static void figurav5$cleanup(Avatar owner,
                                         CompoundTag partCompound,
                                         List<FiguraTextureSet> textureSets,
                                         boolean smoothNormals,
                                         CallbackInfoReturnable<FiguraModelPart> cir) {
        locals.formatVersionArg.remove();
    }
}
