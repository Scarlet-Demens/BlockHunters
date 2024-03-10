package org.demens.blockhunters.mixin;

import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Inject(method = "onSyncedDataUpdated", at = @At("TAIL"))
    private void updateDisguisePose(EntityDataAccessor<?> key, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity)(Object)this;
        if (entity instanceof Player player && key.equals(player.blockHunters$getDataHasDisguise())) {
            player.refreshDimensions();
        }
    }
}
