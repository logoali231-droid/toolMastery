package com.toolmastery.progression;

import com.toolmastery.config.MasteryConfig;
import com.toolmastery.data.ToolExperience;

public class MasteryLevels {
    public static final int MAX_LEVEL = 20;

    /**
     * Fórmula de progressão:
     * $$XP(n) = BASE\_XP \times XP\_MULTIPLIER^{(n-1)}$$
     */
    public static int getXPForLevel(int level) {
        if (level <= 1) return 0;
        return (int) (MasteryConfig.BASE_XP * Math.pow(MasteryConfig.XP_MULTIPLIER, level - 1));
    }

    public static int getLevelForXP(int xp) {
        int level = 1;
        while (level < MAX_LEVEL && getXPForLevel(level + 1) <= xp) {
            level++;
        }
        return level;
    }

    public static float getMiningSpeedBonus(int level) {
        if (level <= 1) return 0.0f;
        return (level - 1) * MasteryConfig.BONUS_PER_LEVEL;
    }

    public static float getCombatDamageBonus(int level) {
        if (level <= 1) return 0.0f;
        return (level - 1) * 0.5f; // +0.5 de dano por nível acima de 1
    }

    /**
     * Chance de 0% a 38% (nível 20) de não gastar durabilidade.
     */
    public static float getDurabilitySaveChance(int level) {
        if (level <= 1) return 0.0f;
        return Math.min((level - 1) * 0.02f, 0.38f);
    }

    /**
     * Chance de drop extra de minério, liberada apenas no nível 10+.
     */
    public static float getExtraDropChance(int level) {
        return level >= 10 ? 0.05f : 0.0f;
    }

    public static String getAbilityName(ToolExperience.Category cat, int level) {
        switch (cat) {
            case MINING:
                if (level >= 15) return "Extração Instintiva";
                if (level >= 10) return "Veia Rica";
                if (level >= 5) return "Toque Leve";
                break;
            case CUTTING:
                if (level >= 10) return "Lenhador Incansável";
                if (level >= 5) return "Corte Preciso";
                break;
            case HARVESTING:
                if (level >= 10) return "Colheita Abundante";
                if (level >= 5) return "Mãos Cuidadosas";
                break;
            case COMBAT:
                if (level >= 15) return "Golpe Pesado";
                if (level >= 10) return "Ataque Fraquejante";
                if (level >= 5) return "Lâmina Afiada";
                break;
        }
        return null;
    }

    public static String getAbilityDescription(ToolExperience.Category cat, int level) {
        String name = getAbilityName(cat, level);
        if (name == null) return null;

        switch (cat) {
            case MINING:
                if (level >= 15) return "§7Chance de não gastar durabilidade e drop extra.";
                if (level >= 10) return "§75% de chance de drop extra em minérios.";
                if (level >= 5) return "§7Até §f38%§7 de chance de não gastar durabilidade.";
                break;
            case CUTTING:
                if (level >= 10) return "§7Reduz desgaste ao cortar troncos.";
                if (level >= 5) return "§7Chance de não gastar durabilidade.";
                break;
            case HARVESTING:
                if (level >= 10) return "§7Chance de drop extra de sementes/plantas.";
                if (level >= 5) return "§7Chance de não gastar durabilidade.";
                break;
            case COMBAT:
                if (level >= 15) return "§7+" + (int)(getCombatDamageBonus(level)) + " de dano e chance de fraqueza.";
                if (level >= 10) return "§7+" + (int)(getCombatDamageBonus(level)) + " de dano.";
                if (level >= 5) return "§7+" + (int)(getCombatDamageBonus(level)) + " de dano.";
                break;
        }
        return name;
    }
}