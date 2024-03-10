package org.demens.blockhunters.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.demens.blockhunters.BlockHunters;
import org.demens.blockhunters.extension.BlockDisguise;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements BlockDisguise {
    @Unique private int stillnessCounter;
    @Unique private boolean disguiseVisible;
    @Unique private BlockState disguiseBlockState;
    @Unique private BlockPos spawnedBlockPos = BlockPos.ZERO;
    @Unique private static final EntityDataAccessor<Optional<BlockState>> DATA_PLAYER_DISGUISE_BS = SynchedEntityData.defineId(Player.class, EntityDataSerializers.BLOCK_STATE);
    @Unique private static final EntityDataAccessor<BlockPos> DATA_PLAYER_DISGUISE_BP = SynchedEntityData.defineId(Player.class, EntityDataSerializers.BLOCK_POS);
    @Unique private static final EntityDataAccessor<Boolean> DATA_PLAYER_HAS_DISGUISE = SynchedEntityData.defineId(Player.class, EntityDataSerializers.BOOLEAN);
    @Unique private static final EntityDataAccessor<Boolean> DATA_PLAYER_DISGUISE_VISIBLE = SynchedEntityData.defineId(Player.class, EntityDataSerializers.BOOLEAN);

    protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public void blockHunters$setDisguise(BlockState disguise) {
        if (level.isClientSide())
            return;

        BlockHunters.LOGGER.info("Setting disguise");
        entityData.set(DATA_PLAYER_HAS_DISGUISE, true);
        blockHunters$setDisguiseVisible(true);
        setDisguiseBlockState(disguise);
    }

    @Override
    public void blockHunters$removeDisguise() {
        if (level.isClientSide())
            return;

        if (spawnedBlockPos != BlockPos.ZERO)
            level.setBlockAndUpdate(spawnedBlockPos, Blocks.AIR.defaultBlockState());
        entityData.set(DATA_PLAYER_HAS_DISGUISE, false);
        setInvisible(false);
        setDisguiseBlockState(null);
    }

    @Override
    public void blockHunters$refreshDisguise() {
        entityData.get(DATA_PLAYER_DISGUISE_BS).ifPresent(this::blockHunters$setDisguise);
    }

    @Override
    public void blockHunters$setDisguiseVisible(boolean active) {
        entityData.set(DATA_PLAYER_DISGUISE_VISIBLE, active);
        disguiseVisible = active;
        setInvisible(!active);
    }

    @Override
    public boolean blockHunters$getDisguiseVisible() {
        return disguiseVisible;
    }

    @Override
    public void blockHunters$setStillnessCounter(int value) {
        stillnessCounter = value;
    }

    @Override
    public boolean blockHunters$hasDisguise() {
        return entityData.get(DATA_PLAYER_HAS_DISGUISE);
    }

    @Unique
    private void setDisguiseBlockState(BlockState state) {
        if (state == null) {
            entityData.set(DATA_PLAYER_DISGUISE_BS, Optional.empty());
        } else {
            entityData.set(DATA_PLAYER_DISGUISE_BS, Optional.of(state));
        }
        disguiseBlockState = state;
    }

    @Unique
    private void setDisguiseBlockPos(BlockPos pos) {
        entityData.set(DATA_PLAYER_DISGUISE_BP, pos);
        spawnedBlockPos = pos;
    }

    @Override
    public BlockState blockHunters$getDisguiseBlockState() {
        Optional<BlockState> bsOptional = entityData.get(DATA_PLAYER_DISGUISE_BS);
        return bsOptional.orElse(null);
    }

    @Override
    public BlockPos blockHunters$getDisguiseBlockPos() {
        return spawnedBlockPos;
    }

    @Override
    public EntityDataAccessor<Boolean> blockHunters$getDataHasDisguise() {
        return DATA_PLAYER_HAS_DISGUISE;
    }

    @Override
    public boolean isInWall() {
        if (blockHunters$hasDisguise())
            return false;
        return super.isInWall();
    }

    @Override
    public boolean isPickable() {
        if (blockHunters$hasDisguise() && !disguiseVisible)
            return false;
        return super.isPickable();
    }

    @Override
    public boolean isPushable() {
        if (blockHunters$hasDisguise() && !disguiseVisible)
            return false;
        return super.isPushable();
    }

    @Override
    public void push(Entity entity) {
        if (blockHunters$hasDisguise() && !disguiseVisible)
            return;
        super.push(entity);
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (!level.isClientSide())
            return;

        if (key.equals(DATA_PLAYER_DISGUISE_BS)) {
            disguiseBlockState = blockHunters$getDisguiseBlockState();
        } else if (key.equals(DATA_PLAYER_DISGUISE_BP)) {
            spawnedBlockPos = entityData.get(DATA_PLAYER_DISGUISE_BP);
        } else if (key.equals(DATA_PLAYER_DISGUISE_VISIBLE)) {
            disguiseVisible = entityData.get(DATA_PLAYER_DISGUISE_VISIBLE);
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void customTickEnd(CallbackInfo ci) {
        if (level.isClientSide()) {
            if (spawnedBlockPos != BlockPos.ZERO) {
                BlockState state = level.getBlockState(spawnedBlockPos);
                state.isViewBlocking = Blocks::never;
                state.isSuffocating = Blocks::never;
                level.setBlock(spawnedBlockPos, state, 0);
            }
        } else if (blockHunters$hasDisguise()) {
            if (spawnedBlockPos != BlockPos.ZERO) {
                Vec3 currentPos = position();
                BlockState state = level.getBlockState(spawnedBlockPos);
                double dist = spawnedBlockPos.distToCenterSqr(currentPos);
                if (dist > 1 || state.isAir() || disguiseVisible) {
                    if (!state.isAir() && !disguiseVisible)
                        setDisguiseBlockState(state);
                    level.setBlockAndUpdate(spawnedBlockPos, Blocks.AIR.defaultBlockState());
                    blockHunters$setDisguiseVisible(true);
                    setDisguiseBlockPos(BlockPos.ZERO);
                    stillnessCounter = 0;
                }
            } else {
                if (stillnessCounter >= 60) {
                    blockHunters$setDisguiseVisible(false);
                    BlockPos blockPos = blockPosition();
                    level.setBlockAndUpdate(blockPos, disguiseBlockState);
                    setDisguiseBlockPos(blockPos);
                }
                stillnessCounter++;
            }
        }
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void addDisguiseData(CompoundTag compound, CallbackInfo ci) {
        if (disguiseBlockState != null) {
            compound.put("BlockDisguise", NbtUtils.writeBlockState(disguiseBlockState));
        }
        if (spawnedBlockPos != BlockPos.ZERO) {
            compound.put("BlockPos", NbtUtils.writeBlockPos(spawnedBlockPos));
        }
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void readDisguiseData(CompoundTag compound, CallbackInfo ci) {
        if (compound.contains("BlockDisguise")) {
            setDisguiseBlockState(NbtUtils.readBlockState(compound.getCompound("BlockDisguise")));
            entityData.set(DATA_PLAYER_HAS_DISGUISE, true);
        }
        if (compound.contains("BlockPos")) {
            BlockPos blockPos = NbtUtils.readBlockPos(compound.getCompound("BlockPos"));
            if (!level.getBlockState(blockPos).isAir()) {
                blockHunters$setDisguiseVisible(false);
                setDisguiseBlockPos(blockPos);
            }
        }
    }

    @Inject(method = "defineSynchedData", at = @At("TAIL"))
    private void defineDisguiseData(CallbackInfo ci) {
        entityData.define(DATA_PLAYER_HAS_DISGUISE, false);
        entityData.define(DATA_PLAYER_DISGUISE_VISIBLE, false);
        entityData.define(DATA_PLAYER_DISGUISE_BS, Optional.empty());
        entityData.define(DATA_PLAYER_DISGUISE_BP, BlockPos.ZERO);
    }

    @Inject(method = "getDimensions", at = @At("HEAD"), cancellable = true)
    private void modifyDimensions(Pose pose, CallbackInfoReturnable<EntityDimensions> cir) {
        if (blockHunters$hasDisguise())
            cir.setReturnValue(EntityDimensions.fixed(1, 1));
    }

    @Inject(method = "getStandingEyeHeight", at = @At("HEAD"), cancellable = true)
    private void disguiseEyeHeight(Pose pose, EntityDimensions dimensions, CallbackInfoReturnable<Float> cir) {
        if (blockHunters$hasDisguise()) {
            cir.setReturnValue(0.5f);
        }
    }

    @Inject(method = "getDestroySpeed", at = @At("HEAD"), cancellable = true)
    private void disguiseNoMining(BlockState state, CallbackInfoReturnable<Float> cir) {
        if (blockHunters$hasDisguise() && !disguiseVisible)
            cir.setReturnValue(0.0f);
    }
}
