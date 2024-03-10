package org.demens.blockhunters.mixin;

import net.minecraft.client.Camera;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Camera.class)
public class CameraMixin {
    @Shadow private Entity entity;

    @Redirect(method = "getMaxZoom", at = @At(value = "NEW", target = "(Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/level/ClipContext$Block;Lnet/minecraft/world/level/ClipContext$Fluid;Lnet/minecraft/world/entity/Entity;)Lnet/minecraft/world/level/ClipContext;"))
    private ClipContext disguiseIgnoreCamCollision(Vec3 vec3, Vec3 vec32, ClipContext.Block block, ClipContext.Fluid fluid, Entity entity) {
        if (entity instanceof Player player && player.blockHunters$hasDisguise()) {
            return new ClipContext(vec3, vec32, ClipContext.Block.valueOf("VISUAL_NO_DISGUISE"), fluid, entity);
        }
        return new ClipContext(vec3, vec32, block, fluid, entity);
    }
}
