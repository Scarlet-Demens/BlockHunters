package org.demens.blockhunters.item;

import dev.architectury.registry.CreativeTabRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.demens.blockhunters.extension.BlockDisguise;
import org.demens.blockhunters.registry.CreativeModeTabs;

public class DisguiseWandItem extends Item {
    public DisguiseWandItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (level.isClientSide())
            return InteractionResult.FAIL;

        BlockPos clickedPos = context.getClickedPos();
        BlockState state = level.getBlockState(clickedPos);
        Player player = context.getPlayer();
        if (player != null && !state.isAir()) {
            ((BlockDisguise) player).blockHunters$setDisguise(state);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }
}
