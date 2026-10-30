package com.toolmastery.config;

import net.minecraftforge.common.config.Configuration;
import java.io.File;

public class MasteryConfig {

    public static int BASE_XP = 1000;
    public static double XP_MULTIPLIER = 1.1;
    public static float BONUS_PER_LEVEL = 0.05f;

    public static void init(File configFile) {

        Configuration config = new Configuration(configFile);
        config.load();

        BASE_XP = config.get(
                "progression",
                "baseXP",
                1000,
                "XP necessário para alcançar o nível 2",
                100,
                Integer.MAX_VALUE
        ).getInt(1000);

        XP_MULTIPLIER = config.get(
                "progression",
                "xpMultiplier",
                1.1,
                "Multiplicador de XP a cada nível",
                1.0,
                10.0
        ).getDouble(1.1);

        BONUS_PER_LEVEL = (float) config.get(
                "balance",
                "bonusPerLevel",
                0.05,
                "Bônus de velocidade de mineração por nível",
                0.0,
                1.0
        ).getDouble(0.05);

        config.save();
    }
}