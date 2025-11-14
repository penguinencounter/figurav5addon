package dev.penguinencounter.figurav5addon.duck;

import dev.penguinencounter.figurav5addon.BlockbenchParser2;
import org.spongepowered.asm.mixin.Unique;

public interface AvatarMetadataOverlay {
    @Unique
    BlockbenchParser2.LoadOptions figurav5$getLoadOptions();
}
