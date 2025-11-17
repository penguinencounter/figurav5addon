package dev.penguinencounter.figurav5addon.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalByteRef;
import dev.penguinencounter.figurav5addon.BlockbenchCommonTypes;
import dev.penguinencounter.figurav5addon.duck.FiguraModelPartOverlay;
import dev.penguinencounter.figurav5addon.duck.locals;
import net.minecraft.nbt.CompoundTag;
import org.figuramc.figura.animation.TransformType;
import org.figuramc.figura.avatar.Avatar;
import org.figuramc.figura.model.FiguraModelPart;
import org.figuramc.figura.model.FiguraModelPartReader;
import org.figuramc.figura.model.rendering.texture.FiguraTextureSet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
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
        Byte inheritedFormatVersion = locals.ModelPart$formatVersion.get();
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
        locals.ModelPart$formatVersion.set(formatVersion.get());
    }

    @Definition(id = "result", local = @Local(type = FiguraModelPart.class, ordinal = 1))
    @Expression("result = @(?)")
    @ModifyExpressionValue(
            method = "read",
            at = @At(
                    value = "MIXINEXTRAS:EXPRESSION"
            )
    )
    private static FiguraModelPart figurav5$setVersion(FiguraModelPart original, @Share("formatVersion") LocalByteRef formatVersion) {
        ((FiguraModelPartOverlay) original).figurav5$setFormatVersion(formatVersion.get());
        return original;
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
        locals.ModelPart$formatVersion.remove();
    }

    // also, keyframe helpers
    @Inject(
            method = "read",
            at = @At(
                    value = "INVOKE",
                    target = "Lorg/figuramc/figura/animation/Keyframe;<init>(Lorg/figuramc/figura/avatar/Avatar;Lorg/figuramc/figura/animation/Animation;FLorg/figuramc/figura/animation/Interpolation;Lcom/mojang/datafixers/util/Pair;Lcom/mojang/datafixers/util/Pair;Lorg/figuramc/figura/math/vector/FiguraVec3;Lorg/figuramc/figura/math/vector/FiguraVec3;Lorg/figuramc/figura/math/vector/FiguraVec3;Lorg/figuramc/figura/math/vector/FiguraVec3;)V"
            )
    )
    private static void figurav5$kfMetadata(Avatar owner,
                                            CompoundTag partCompound,
                                            List<FiguraTextureSet> textureSets,
                                            boolean smoothNormals,
                                            CallbackInfoReturnable<FiguraModelPart> cir,
                                            @Local FiguraModelPart result,
                                            @Local TransformType type) {
        locals.Keyframe$part.set(result);
        locals.Keyframe$channel.set(type);
    }
}
