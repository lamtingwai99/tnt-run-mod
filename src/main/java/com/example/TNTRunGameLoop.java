package com.example;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.TntEntity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TNTRunGameLoop {
    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            if (!TNTRunCommand.isGameActive) return;

            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                if (player.isSpectator()) continue;

                World world = player.getWorld();
                BlockPos posBelow = player.getBlockPos().down();
                BlockState stateBelow = world.getBlockState(posBelow);

                if (!stateBelow.isAir() && isReplaceable(stateBelow)) {
                    world.setBlockState(posBelow, Blocks.AIR.getDefaultState());

                    TntEntity tnt = EntityType.TNT.create(world);
                    if (tnt != null) {
                        tnt.refreshPositionAndAngles(posBelow.getX() + 0.5, posBelow.getY(), posBelow.getZ() + 0.5, 0.0F, 0.0F);
                        tnt.setFuse(40);
                        world.spawnEntity(tnt);
                    }
                }
            }
        });
    }

    private static boolean isReplaceable(BlockState state) {
        if (state.isOf(Blocks.OBSIDIAN) || state.isOf(Blocks.CRYING_OBSIDIAN) || state.isOf(Blocks.BEDROCK)) {
            return false;
        }

        if (state.isIn(BlockTags.COAL_ORES) || state.isIn(BlockTags.IRON_ORES) ||
            state.isIn(BlockTags.GOLD_ORES) || state.isIn(BlockTags.DIAMOND_ORES) ||
            state.isIn(BlockTags.REDSTONE_ORES) || state.isIn(BlockTags.LAPIS_ORES) ||
            state.isIn(BlockTags.COPPER_ORES) || state.isIn(BlockTags.EMERALD_ORES) ||
            state.isOf(Blocks.DIAMOND_BLOCK) || state.isOf(Blocks.GOLD_BLOCK) ||
            state.isOf(Blocks.IRON_BLOCK) || state.isOf(Blocks.COAL_BLOCK) ||
            state.isOf(Blocks.EMERALD_BLOCK) || state.isOf(Blocks.REDSTONE_BLOCK) ||
            state.isOf(Blocks.COPPER_BLOCK) || state.isOf(Blocks.NETHERITE_BLOCK)) {
            return false;
        }

        return true;
    }
}
