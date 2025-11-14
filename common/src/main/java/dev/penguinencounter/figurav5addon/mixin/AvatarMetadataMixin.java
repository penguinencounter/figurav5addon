package dev.penguinencounter.figurav5addon.mixin;

import dev.penguinencounter.figurav5addon.BlockbenchParser2;
import dev.penguinencounter.figurav5addon.duck.AvatarMetadataOverlay;
import org.figuramc.figura.parsers.AvatarMetadataParser;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(AvatarMetadataParser.Metadata.class)
public class AvatarMetadataMixin implements AvatarMetadataOverlay {
    @SuppressWarnings("AddedMixinMembersNamePattern")
    @Unique
    public BlockbenchParser2.LoadOptions loadOptions;

    @Unique
    @Override
    public BlockbenchParser2.LoadOptions figurav5$getLoadOptions() {
        return loadOptions;
    }
}
