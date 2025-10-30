package dev.penguinencounter.figurabadgetool.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.penguinencounter.figurabadgetool.FiguraBadgeCommand;
import org.figuramc.figura.commands.FiguraCommands;
import org.figuramc.figura.utils.FiguraClientCommandSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = FiguraCommands.class, remap = false)
public abstract class FiguraCommandsMixin {
    @Inject(method = "getCommandRoot", at = @At("RETURN"))
    private static void figurabadgetool$addBadgeCommand(CallbackInfoReturnable<LiteralArgumentBuilder<FiguraClientCommandSource>> cir,
                                                        @Local LiteralArgumentBuilder<FiguraClientCommandSource> root) {
        root.then(FiguraBadgeCommand.command());
    }
}
