package com.toolmastery.events;

import com.toolmastery.data.ToolExperience;
import com.toolmastery.progression.MasteryLevels;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrop;
import net.minecraft.block.BlockLogs;
import net.minecraft.block.BlockOre;
import net.minecraft.block.BlockPlant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MiningEvents {

    @SubscribeEvent
    public void onBlockBreak(BreakEvent event) {
        EntityPlayer player = event.getPlayer();
        if (player == null || player.capabilities.isCreativeMode) return;

        ItemStack stack = player.getHeldItemMainhand();
        if (stack.isEmpty()) return;

        Block block = event.getState().getBlock();
        ToolExperience.Category blockCat = categorizeBlock(block);
        if (blockCat == null) return;

        ToolExperience.Category toolCat = ToolExperience.getItemCategory(stack);
        if (toolCat == null) return;

        // Só ganha XP se a ferramenta for adequada à ação
        if (toolCat != blockCat) return;

        // Trava a categoria da ferramenta na primeira utilização
        ToolExperience.ensureCategory(stack, toolCat);

        // Valores ilustrativos da sua especificação
        int xp = (blockCat == ToolExperience.Category.MINING && block instanceof BlockOre) ? 3 : 1;
        ToolExperience.addXP(stack, xp, blockCat);
    }

    @SubscribeEvent
    public void onBreakSpeed(BreakSpeed event) {
        EntityPlayer player = event.getEntity();
        ItemStack stack = player.getHeldItemMainhand();
        if (stack.isEmpty()) return;

        int level = ToolExperience.getLevel(stack);
        if (level > 1) {
            float bonus = MasteryLevels.getMiningSpeedBonus(level);
            // Aplica o bônus multiplicando a velocidade base
            event.NewSpeed *= (1.0f + bonus);
        }
    }

    private ToolExperience.Category categorizeBlock(Block block) {
        if (block instanceof BlockLogs) return ToolExperience.Category.CUTTING;
        if (block instanceof BlockCrop || block instanceof BlockPlant) return ToolExperience.Category.HARVESTING;
        if (block instanceof BlockOre) return ToolExperience.Category.MINING;
        // Pedra, terra, areia etc. contam como mineração genérica
        return ToolExperience.Category.MINING;
    }
}