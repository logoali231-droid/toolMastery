package com.toolmastery.progression;

import com.toolmastery.config.MasteryConfig;

public class MasteryLevels {

    /**
     * Fórmula de progressão (exemplo configurável):
     * $$XP(n) = BASE\_XP \times XP\_MULTIPLIER^{(n-1)}$$
     */
    public static int getXPForLevel(int level) {
        if (level <= 1) return 0;
        return (int) (MasteryConfig.BASE_XP * Math.pow(MasteryConfig.XP_MULTIPLIER, level - 1));
    }

    public static int getLevelForXP(int xp) {
        int level = 1;
        while (getXPForLevel(level + 1) <= xp) {
            level++;
        }
        return level;
    }

    /**
     * Exemplo: nível 3 concede +10% se BONUS_PER_LEVEL = 0.05.
     */
    public static float getMiningSpeedBonus(int level) {
        if (level <= 1) return 0.0f;
        return (level - 1) * MasteryConfig.BONUS_PER_LEVEL;
    }
}