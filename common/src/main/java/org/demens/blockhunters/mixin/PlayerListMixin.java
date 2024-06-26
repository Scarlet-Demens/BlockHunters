package org.demens.blockhunters.mixin;

import net.minecraft.network.Connection;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.demens.blockhunters.BlockHunters;
import org.demens.blockhunters.extension.BlockDisguise;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerList.class)
public class PlayerListMixin {
    @Inject(method = "placeNewPlayer", at = @At(value = "TAIL"))
    private void onPlayerJoin(Connection netManager, ServerPlayer player, CallbackInfo ci) {
        BlockHunters.LOGGER.info("Server join");
        if (((BlockDisguise) player).blockHunters$hasDisguise()) {
            ((BlockDisguise) player).blockHunters$refreshDisguise();
        }
    }
}
