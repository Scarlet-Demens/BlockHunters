package org.demens.blockhunters.extension;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.level.block.state.BlockState;

public interface BlockDisguise {
    default void blockHunters$setDisguise(BlockState state) {}

    default void blockHunters$setDisguiseVisible(boolean active) {}

    default void blockHunters$setStillnessCounter(int value) {}

    default void blockHunters$removeDisguise() {}

    default boolean blockHunters$hasDisguise() {
        return false;
    }

    default void blockHunters$refreshDisguise() {}

    default BlockState blockHunters$getDisguiseBlockState() {
        return null;
    }

    default boolean blockHunters$getDisguiseVisible() {
        return false;
    }

    default BlockPos blockHunters$getDisguiseBlockPos() {
        return null;
    }

    default EntityDataAccessor<Boolean> blockHunters$getDataHasDisguise() {
        return null;
    }
}
