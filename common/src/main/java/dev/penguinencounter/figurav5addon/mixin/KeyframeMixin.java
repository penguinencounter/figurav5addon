package dev.penguinencounter.figurav5addon.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mojang.datafixers.util.Pair;
import dev.penguinencounter.figurav5addon.duck.FiguraModelPartOverlay;
import dev.penguinencounter.figurav5addon.duck.locals;
import org.figuramc.figura.animation.Animation;
import org.figuramc.figura.animation.Interpolation;
import org.figuramc.figura.animation.Keyframe;
import org.figuramc.figura.animation.TransformType;
import org.figuramc.figura.avatar.Avatar;
import org.figuramc.figura.math.vector.FiguraVec3;
import org.figuramc.figura.model.FiguraModelPart;
import org.jetbrains.annotations.Nullable;
import org.luaj.vm2.LuaError;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static dev.penguinencounter.figurav5addon.BlockbenchCommonTypes.FORMAT_V5;
import static dev.penguinencounter.figurav5addon.KeyframeHelpers.trimErrorMessage;

@Mixin(value = Keyframe.class, remap = false)
public abstract class KeyframeMixin {
    @Unique
    @Nullable
    private FiguraModelPart figurav5$part;
    @Unique
    @Nullable
    private TransformType figurav5$channel;

    @Shadow
    @Final
    private Animation animation;

    @Shadow
    @Final
    private float time;

    @Shadow
    @Final
    private String chunkName;

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Ljava/lang/Object;<init>()V", shift = At.Shift.AFTER))
    private void figurav5$additionalCtor(Avatar owner,
                                         Animation animation,
                                         float time,
                                         Interpolation interpolation,
                                         Pair<FiguraVec3, String[]> a,
                                         Pair<FiguraVec3, String[]> b,
                                         FiguraVec3 bezierLeft,
                                         FiguraVec3 bezierRight,
                                         FiguraVec3 bezierLeftTime,
                                         FiguraVec3 bezierRightTime,
                                         CallbackInfo ci) {
        figurav5$part = locals.Keyframe$part.get();
        figurav5$channel = locals.Keyframe$channel.get();

        locals.Keyframe$part.remove();
        locals.Keyframe$channel.remove();
    }

    // make the chunk name not useless
    @Definition(id = "getName", method = "Lorg/figuramc/figura/animation/Animation;getName()Ljava/lang/String;")
    @Definition(id = "time", local = @Local(type = float.class, argsOnly = true))
    @Definition(id = "animation", local = @Local(type = Animation.class, argsOnly = true))
    @Expression("animation.getName() + ' keyframe (' + time + 's)'")
    @ModifyExpressionValue(
            method = "<init>",
            at = @At("MIXINEXTRAS:EXPRESSION")
    )
    private String figurav5$betterChunkName(String original) {
        if (figurav5$part == null || figurav5$channel == null) return original;
        // note: scripts rely on the animation name followed by "keyframe" being at the start
        return animation.getName() +
                " keyframe (part '" +
                figurav5$part.name +
                "', time " + time + "s, " +
                figurav5$channel.name() + " ?" +
                ")";
    }

    @Inject(
            method = "parseStringData",
            at = @At(
                    value = "FIELD",
                    target = "Lorg/figuramc/figura/avatar/Avatar;luaRuntime:Lorg/figuramc/figura/lua/FiguraLuaRuntime;",
                    ordinal = 0
            )
    )
    private void figurav5$enhanceErrorMessage(String data,
                                              float delta,
                                              CallbackInfoReturnable<Float> cir,
                                              @Local(name = "ignored2") Exception exprExc,
                                              @Local(name = "e") LocalRef<Exception> stmtExc) {
        Exception stmtExcUnder = stmtExc.get();
        if (!(exprExc instanceof LuaError)) return;
        if (!(stmtExcUnder instanceof LuaError)) return;

        String trailers = "";
        if (figurav5$part != null && ((FiguraModelPartOverlay) figurav5$part).figurav5$getFormatVersion() >= FORMAT_V5) {
            // try to tell the user about the issue
            //noinspection TextBlockMigration
            trailers = "\n\n§6If you opened a 4.12 model file in 5.0, your keyframes might be corrupted.§r\n" +
                    "§6You'll have to manually fix them; note that the X and Y values on rotation,§r\n" +
                    "§6 as well as the X value on position, need to be negated.§r";
        }
        //noinspection TextBlockMigration
        stmtExc.set(new LuaError(String.format(
                "Syntax error in keyframe [%s]:\n\n" +
                        "Not a valid expression, because:\n%s\n" +
                        "Not a valid block, because:\n%s\n\n" +
                        "script:\n" +
                        "%s" +
                        "%s",
                chunkName,
                trimErrorMessage(exprExc.getMessage()),
                trimErrorMessage(stmtExcUnder.getMessage()),
                data,
                trailers
        )));
    }
}
