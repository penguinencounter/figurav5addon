package dev.penguinencounter.figurav5addon.mixin;

import dev.penguinencounter.figurav5addon.PermissivePath;
import org.figuramc.figura.utils.PathUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.nio.file.Path;

@Mixin(value = PathUtils.class, remap = false)
public abstract class PathUsageFixes {
    @Redirect(
            method = "getPath(Ljava/lang/String;)Ljava/nio/file/Path;",
            at = @At(value = "INVOKE", target = "Ljava/nio/file/Path;of(Ljava/lang/String;[Ljava/lang/String;)Ljava/nio/file/Path;")
    )
    private static Path replace1(String first, String[] more) {
        return PermissivePath.fakeFS.getPath(first, more);
    }

    @Redirect(
            method = "getWorkingDirectory",
            at = @At(value = "INVOKE", target = "Ljava/nio/file/Path;of(Ljava/lang/String;[Ljava/lang/String;)Ljava/nio/file/Path;")
    )
    private static Path replace2(String first, String[] more) {
        return PermissivePath.fakeFS.getPath(first, more);
    }
}
