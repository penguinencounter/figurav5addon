package dev.penguinencounter.figurav5addon;

import net.minecraft.nbt.CompoundTag;

import java.util.List;

//dummy class containing the return object of the parser
public record ModelParseResult(CompoundTag textures, List<CompoundTag> animationList, CompoundTag modelNbt) {
}
