package com.toolmastery.progression;

import com.toolmastery.config.MasteryConfig;
import com.toolmastery.data.ToolExperience;

import net.minecraft.item.ItemStack;

public class MasteryLevels {

    public static final int MAX_LEVEL = 10;

    /**
     * XP base para ir do nível 1 para o nível 2.
     * = 60% da durabilidade máxima da ferramenta.
     */
    public static int getBaseXP(ItemStack stack) {
        if (stack == null || stack.isEmpty())
            return MasteryConfig.BASE_XP;
        int max = stack.getMaxDamage();
        if (max <= 0)
            return MasteryConfig.BASE_XP;
        float pct = MasteryConfig.DURABILITY_PERCENT / 100f;
        return Math.max(10, Math.round(max * pct));
    }

    public static float getDurabilitySaveChance(int level) {
        int start = MasteryConfig.DURABILITY_SAVE_START_LEVEL;
        if (level < start)
            return 0.0f;
        float perLevel = MasteryConfig.DURABILITY_SAVE_PER_LEVEL_PERCENT / 100f;
        float max = MasteryConfig.DURABILITY_SAVE_MAX_PERCENT / 100f;
        float chance = perLevel * (level - start + 1);
        return Math.min(chance, max);
    }

    /**
     * XP cumulativo necessário para ALCANÇAR o nível informado.
     * getXPForLevel(1, stack) == 0
     * getXPForLevel(2, stack) == getBaseXP(stack)
     */
    public static int getXPForLevel(int level, ItemStack stack) {
        if (level <= 1)
            return 0;
        if (level > MAX_LEVEL)
            level = MAX_LEVEL;
        int base = getBaseXP(stack);
        double total = 0;
        double perLevel = base;
        for (int i = 1; i < level; i++) {
            total += perLevel;
            perLevel *= MasteryConfig.XP_MULTIPLIER;
        }
        return (int) Math.round(total);
    }

    /**
     * Nível correspondente a uma quantidade de XP cumulativo.
     */
    public static int getLevelForXP(int xp, ItemStack stack) {
        if (xp <= 0)
            return 1;
        int base = getBaseXP(stack);
        int level = 1;
        double cumulative = 0;
        double perLevel = base;
        while (level < MAX_LEVEL) {
            if (xp < cumulative + perLevel)
                break;
            cumulative += perLevel;
            perLevel *= MasteryConfig.XP_MULTIPLIER;
            level++;
        }
        return level;
    }

    /**
     * Bônus de velocidade de mineração por nível.
     */
    public static float getMiningSpeedBonus(int level) {
        return (level - 1) * MasteryConfig.BONUS_PER_LEVEL;
    }

    /**
     * Bônus de dano de combate por nível.
     */
    public static float getCombatDamageBonus(int level) {
        return 0.5f * (level - 1);
    }

    public static float getExtraDropChance(int level) {
        int start = MasteryConfig.EXTRA_DROP_START_LEVEL;
        if (level < start)
            return 0.0f;
        float perLevel = MasteryConfig.EXTRA_DROP_PER_LEVEL_PERCENT / 100f;
        float max = MasteryConfig.EXTRA_DROP_MAX_PERCENT / 100f;
        float chance = perLevel * (level - start + 1);
        return Math.min(chance, max);
    }

        /**
     * Descrição textual da habilidade ativa neste nível, para o tooltip.
     * Retorna "" se o nível não desbloqueia nada.
     */
    public static String getAbilityDescription(ToolExperience.Category cat, int level) {
        if (cat == null) return "";
        if (level < 3) return "";

        switch (cat) {
            case MINING:
                if (level >= 10) return "\u00A7bMineração Aprimorada X";
                if (level >= 6)  return "\u00A7bDrop Extra";
                if (level >= 3)  return "\u00A7bDurabilidade Economizada";
                break;

            case COMBAT:
                if (level >= 10) return "\u00A7cMestre do Combate";
                if (level >= 6)  return "\u00A7cFraqueza";
                if (level >= 3)  return "\u00A7cDano Extra";
                break;

            default:
                return "";
        }
        return "";
    }

    
}