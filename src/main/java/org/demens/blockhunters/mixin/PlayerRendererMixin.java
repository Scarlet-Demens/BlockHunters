package org.demens.blockhunters.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerRenderer.class)
public class PlayerRendererMixin extends LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    @Unique
    private BlockRenderDispatcher dispatcher;

    public PlayerRendererMixin(EntityRendererProvider.Context context, PlayerModel<AbstractClientPlayer> entityModel, float f) {
        super(context, entityModel, f);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(EntityRendererProvider.Context context, boolean bl, CallbackInfo ci) {
        dispatcher = context.getBlockRenderDispatcher();
    }

    @SuppressWarnings({"rawtypes"})
    @Redirect(method = "render(Lnet/minecraft/client/player/AbstractClientPlayer;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/LivingEntityRenderer;render(Lnet/minecraft/world/entity/LivingEntity;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V"))
    private void redirectRender(LivingEntityRenderer instance, LivingEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        AbstractClientPlayer playerEntity = (AbstractClientPlayer) entity;
        if (playerEntity.blockHunters$hasDisguise() && !playerEntity.isDeadOrDying()) {
            this.shadowStrength = 0f;
            renderBlockDisguise(playerEntity, entity, poseStack, buffer);
        } else {
            this.shadowStrength = 1f;
            super.render(playerEntity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        }
    }

    @Unique
    private void renderBlockDisguise(AbstractClientPlayer playerEntity, LivingEntity entity, PoseStack poseStack, MultiBufferSource buffer) {
        BlockState blockState = playerEntity.blockHunters$getDisguiseBlockState();
        if (blockState == null || !playerEntity.blockHunters$getDisguiseVisible() || blockState.getRenderShape() != RenderShape.MODEL)
            return;

        Level level = entity.getLevel();
        if (blockState == level.getBlockState(entity.blockPosition()) || blockState.getRenderShape() == RenderShape.INVISIBLE)
            return;

        poseStack.pushPose();
        BlockPos blockPos = new BlockPos(entity.getX(), entity.getBoundingBox().maxY, entity.getZ());
        poseStack.translate(-0.5, 0.0, -0.5);
        dispatcher.getModelRenderer().tesselateBlock(level, this.dispatcher.getBlockModel(blockState), blockState, blockPos, poseStack, buffer.getBuffer(ItemBlockRenderTypes.getMovingBlockRenderType(blockState)), false, RandomSource.create(), blockState.getSeed(BlockPos.ZERO), OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
    }

    @Shadow
    public ResourceLocation getTextureLocation(AbstractClientPlayer entity) {
        return null;
    }
}
