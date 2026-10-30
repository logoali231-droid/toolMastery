
package com.toolmastery.events;

import com.toolmastery.data.ToolExperience;
import com.toolmastery.progression.MasteryLevels;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockOre;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MiningEvents {

    @SubscribeEvent
    public void onBlockBreak(BreakEvent event) {

        // XP deve ser processado somente no servidor.
        if (event.getWorld().isRemote) return;

        EntityPlayer player = event.getPlayer();

        if (player == null || player.capabilities.isCreativeMode) {
            return;
        }

        ItemStack stack = player.getHeldItemMainhand();

        if (stack.isEmpty()) return;

        IBlockState state = event.getState();
        Block block = state.getBlock();

        ToolExperience.Category blockCat = categorizeBlock(block);

        if (blockCat == null) return;

        ToolExperience.Category toolCat =
                ToolExperience.getItemCategory(stack);

        if (toolCat == null || toolCat != blockCat) return;

        // Define a categoria da ferramenta na primeira utilização.
        ToolExperience.ensureCategory(stack, toolCat);

        int xp = 1;

        if (blockCat == ToolExperience.Category.MINING
                && block instanceof BlockOre) {
            xp = 3;
        }

        ToolExperience.addXP(stack, xp, blockCat);
    }

    @SubscribeEvent
    public void onBreakSpeed(BreakSpeed event) {

        EntityPlayer player = event.getEntityPlayer();

        if (player == null) return;

        ItemStack stack = player.getHeldItemMainhand();

        if (stack.isEmpty()) return;

        int level = ToolExperience.getLevel(stack);

        if (level > 1) {

            float bonus =
                    MasteryLevels.getMiningSpeedBonus(level);

            event.setNewSpeed(
                    event.getNewSpeed() * (1.0f + bonus)
            );
        }
    }

    private ToolExperience.Category categorizeBlock(Block block) {

        // Troncos.
        if (block instanceof BlockLog) {
            return ToolExperience.Category.CUTTING;
        }

        // Plantações e vegetação.
        if (block instanceof BlockCrops || block instanceof BlockBush) {
            return ToolExperience.Category.HARVESTING;
        }

        // Minérios vanilla.
        if (block instanceof BlockOre) {
            return ToolExperience.Category.MINING;
        }

        // Pedra, terra, areia e outros blocos.
        return ToolExperience.Category.MINING;
    }
}
