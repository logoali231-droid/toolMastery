
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
import com.toolmastery.events.LevelUpEvents;   // same package — may already be implicit
import net.minecraft.block.state.IBlockState;

public class MiningEvents {

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        EntityPlayer player = event.getPlayer();
        if (player == null || player.isCreative())
            return;

        ItemStack stack = player.getHeldItemMainhand();
        if (stack.isEmpty())
            return;

        ToolExperience.Category cat = ToolExperience.getItemCategory(stack);
        if (cat != ToolExperience.Category.MINING)
            return;

        int oldLevel = ToolExperience.getLevel(stack);
        int newLevel = ToolExperience.addXP(stack, XP_PER_BLOCK, cat);

        if (newLevel > oldLevel) {
            LevelUpEvents.trigger(player, stack, newLevel);
        }

        if (MasteryAbilities.shouldDropExtra(stack)) {
            IBlockState state = event.getWorld().getBlockState(event.getPos());
            state.getBlock().dropBlockAsItem(event.getWorld(), event.getPos(), state, 1);
        }
    }

    @SubscribeEvent
    public void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        ItemStack stack = event.getEntityPlayer().getHeldItemMainhand();
        if (stack.isEmpty())
            return;
        if (ToolExperience.getItemCategory(stack) != ToolExperience.Category.MINING)
            return;
        int level = ToolExperience.getLevel(stack);
        if (level <= 1)
            return;
        float bonus = MasteryAbilities.getMiningSpeedBonus(stack);
        event.setNewSpeed(event.getOriginalSpeed() * (1.0f + bonus));
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
