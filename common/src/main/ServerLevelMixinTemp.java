package org.demens.blockhunters.mixin;

import @vector_package@;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.@color_package@;
import net.minecraft.world.phys.Vec3;
import org.demens.blockhunters.extension.BlockDisguise;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin {
    @Shadow public abstract ServerLevel getLevel();

    @Inject(method = "destroyBlockProgress", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerGamePacketListenerImpl;send(Lnet/minecraft/network/protocol/Packet;)V"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void removeDisguiseAfterStartDestroy(int breakerId, BlockPos pos, int progress, CallbackInfo ci, Iterator var4, ServerPlayer serverPlayer) {
        if (((BlockDisguise) serverPlayer).blockHunters$hasDisguise() && ((BlockDisguise) serverPlayer).blockHunters$getDisguiseBlockPos().equals(pos)) {
            ((BlockDisguise) serverPlayer).blockHunters$setDisguiseVisible(true);
            getLevel().playSound(null, pos, SoundEvents.WITHER_BREAK_BLOCK, SoundSource.MASTER, 1, 2);
            BlockState state = getLevel().getBlockState(pos);
            int color = state.@get_color@.calculateRGBColor(@color_package@.Brightness.NORMAL);
            DustParticleOptions dustParticle = new DustParticleOptions(new Vector3f(Vec3.fromRGB24(color)@convert_vec3@), 2f);
            getLevel().sendParticles(
                    dustParticle,
                    pos.getX(),
                    pos.getY(),
                    pos.getZ(),
                    300,
                    0.5,
                    0.5,
                    0.5,
                    0.1
            );
        }
    }
}
