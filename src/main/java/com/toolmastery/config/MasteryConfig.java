package com.toolmastery.config;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class MasteryConfig {
    public static int BASE_XP = 1000;
    public static double XP_MULTIPLIER = 1.1;
    public static float BONUS_PER_LEVEL = 0.05f; // 5% por nível

    public static void init(File configFile) {
        Configuration config = new Configuration(configFile);
        config.load();

        BASE_XP = config.getInt("baseXP", "progression", 1000, 100, Integer.MAX_VALUE,
                "XP necessário para alcançar o nível 2");
        XP_MULTIPLIER = config.getDouble("xpMultiplier", "progression", 1.1, 1.0, 10.0,
                "Multiplicador de XP a cada nível");
        BONUS_PER_LEVEL = (float) config.getDouble("bonusPerLevel", "balance", 0.05, 0.0, 1.0,
                "Bônus de velocidade de mineração por nível");

        config.save();
    }
}