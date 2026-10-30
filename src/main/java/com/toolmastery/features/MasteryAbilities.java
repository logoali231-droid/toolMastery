package com.toolmastery.features;

import com.toolmastery.data.ToolExperience;
import com.toolmastery.progression.MasteryLevels;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;

import java.util.Random;

public class MasteryAbilities {
    private static final Random RAND = new Random();

    /**
     * Chance de não gastar durabilidade ao quebrar bloco.
     */
    public static boolean shouldSaveDurability(ItemStack stack) {
        if (stack == null || stack.isEmpty()) return false;
        ToolExperience.Category cat = ToolExperience.getCategory(stack);
        if (cat == null) return false;
        int level = ToolExperience.getLevel(stack);
        float chance = MasteryLevels.getDurabilitySaveChance(level);
        return RAND.nextFloat() < chance;
    }

    /**
     * Chance de drop extra ao minerar minério (nível 10+).
     */
    public static boolean shouldDropExtra(ItemStack stack) {
        if (stack == null || stack.isEmpty()) return false;
        if (ToolExperience.getCategory(stack) != ToolExperience.Category.MINING) return false;
        int level = ToolExperience.getLevel(stack);
        float chance = MasteryLevels.getExtraDropChance(level);
        return RAND.nextFloat() < chance;
    }

    /**
     * Bônus de dano para espadas.
     */
    public static float getCombatDamageBonus(ItemStack stack) {
        if (stack == null || stack.isEmpty()) return 0.0f;
        if (ToolExperience.getCategory(stack) != ToolExperience.Category.COMBAT) return 0.0f;
        return MasteryLevels.getCombatDamageBonus(ToolExperience.getLevel(stack));
    }

    /**
     * Aplica efeito de fraqueza ao alvo se a espada for nível 10+ (chance 10%).
     */
    public static void maybeApplyWeakness(EntityPlayer attacker, EntityLivingBase target, ItemStack stack) {
        if (target == null || attacker == null) return;
        if (ToolExperience.getCategory(stack) != ToolExperience.Category.COMBAT) return;
        if (ToolExperience.getLevel(stack) < 10) return;
        if (RAND.nextFloat() < 0.10f) {
            target.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 100, 0));
        }
    }
}