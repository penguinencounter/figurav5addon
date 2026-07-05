package dev.penguinencounter.figurav5addon.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import dev.penguinencounter.figurav5addon.BlockbenchCommonTypes;
import dev.penguinencounter.figurav5addon.duck.FiguraModelPartOverlay;
import org.figuramc.figura.model.FiguraModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FiguraModelPart.class)
public abstract class FiguraModelPartMixin implements FiguraModelPartOverlay {
    @Unique
    public byte figurav5$formatVersion = BlockbenchCommonTypes.FORMATLESS;

    @Override
    public byte figurav5$getFormatVersion() {
        return figurav5$formatVersion;
    }

    @Override
    public void figurav5$setFormatVersion(byte value) {
        figurav5$formatVersion = value;
    }

    @Inject(
            method = "copy",
            at = @At(value = "RETURN", ordinal = 1),
            remap = false
    )
    private void figurav5$copyInherit(Object obj,
                                      Boolean deepP,
                                      CallbackInfoReturnable<FiguraModelPart> cir,
                                      @Local(name = "result") FiguraModelPart result) {
        ((FiguraModelPartOverlay) result).figurav5$setFormatVersion(figurav5$getFormatVersion());
    }

    @Inject(
            method = "deepCopy",
            at = @At(value = "RETURN"),
            remap = false
    )
    private void figurav5$copyInherit2(String name,
                                       CallbackInfoReturnable<FiguraModelPart> cir,
                                       @Local(name = "result") FiguraModelPart result) {
        ((FiguraModelPartOverlay) result).figurav5$setFormatVersion(figurav5$getFormatVersion());
    }
}
