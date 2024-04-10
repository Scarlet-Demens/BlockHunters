package org.demens.blockhunters.mixin;

import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.demens.blockhunters.extension.BlockDisguise;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerGamePacketListenerImplMixin {
    @Shadow public ServerPlayer player;

    @Inject(method = "handleMovePlayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/level/ServerPlayer;absMoveTo(DDDFF)V", ordinal = 1, shift = At.Shift.BEFORE))
    private void resetStillnessCounter(ServerboundMovePlayerPacket packet, CallbackInfo ci) {
        double posDeltaLen = player.position().subtract(player.xo, player.yo, player.zo).lengthSqr();
        if (((BlockDisguise) player).blockHunters$hasDisguise() && posDeltaLen != 0) {
            ((BlockDisguise) player).blockHunters$setStillnessCounter(0);
        }
    }
}
