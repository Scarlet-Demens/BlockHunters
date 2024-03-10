package org.demens.blockhunters.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.shapes.Shapes;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.ArrayList;
import java.util.Arrays;

@Mixin(ClipContext.Block.class)
public class ClipContextBlockMixin {
    @Mutable
    @Shadow @Final private static ClipContext.Block[] $VALUES;

    @SuppressWarnings("unused")
    @Unique
    private static final ClipContext.Block VISUAL_NO_DISGUISE = addBlock("VISUAL_NO_DISGUISE", (blockState, blockGetter, blockPos, collisionContext) -> {
        Player player = Minecraft.getInstance().player;
        if (player != null && player.blockHunters$hasDisguise() && player.blockHunters$getDisguiseBlockPos().equals(blockPos)) {
            return Shapes.empty();
        }
        return blockState.getVisualShape(blockGetter, blockPos, collisionContext);
    });

    @Invoker("<init>")
    public static ClipContext.Block invokeInit(String internalName, int internalId, ClipContext.ShapeGetter shapeGetter) {
        throw new AssertionError();
    }

    @Unique
    private static ClipContext.Block addBlock(String internalName, ClipContext.ShapeGetter shapeGetter) {
        ArrayList<ClipContext.Block> blocks = new ArrayList<>(Arrays.asList($VALUES));
        ClipContext.Block block = invokeInit(internalName, blocks.get(blocks.size() - 1).ordinal() + 1, shapeGetter);
        blocks.add(block);
        $VALUES = blocks.toArray(new ClipContext.Block[0]);
        return block;
    }
}
