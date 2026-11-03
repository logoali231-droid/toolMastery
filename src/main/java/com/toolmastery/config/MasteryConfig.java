package com.toolmastery.config;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class MasteryConfig {

    private static Configuration config;

    public static int BASE_XP = 1000;
    public static double XP_MULTIPLIER = 1.1;
    public static float BONUS_PER_LEVEL = 0.05f;

    /** 0-100: % da durabilidade máxima usada como XP para o nível 2. */
    public static int DURABILITY_PERCENT = 60;
    // --- Abilities: Durability Save ---
    public static int DURABILITY_SAVE_START_LEVEL = 3;
    public static int DURABILITY_SAVE_PER_LEVEL_PERCENT = 5;
    public static int DURABILITY_SAVE_MAX_PERCENT = 50;

    // --- Abilities: Extra Drop ---
    public static int EXTRA_DROP_START_LEVEL = 6;
    public static int EXTRA_DROP_PER_LEVEL_PERCENT = 5;
    public static int EXTRA_DROP_MAX_PERCENT = 50;

    public static void init(File configFile) {
        if (config == null) {
            config = new Configuration(configFile);
            config.load();
        }
        sync();
    }

    public static void sync() {
        if (config == null)
            return;

        BASE_XP = config.get(
                "progression",
                "basexp",
                1000,
                "XP de fallback para ferramentas sem durabilidade",
                100,
                Integer.MAX_VALUE).getInt(1000);

        XP_MULTIPLIER = config.get(
                "progression",
                "xpMultiplier",
                1.1,
                "Multiplicador de XP por nível",
                1.0,
                10.0).getDouble(1.1);

        DURABILITY_PERCENT = config.get(
                "progression",
                "durabilityPercent",
                60,
                "Porcentagem da durabilidade máxima usada como XP base para o nível 2 (0-100). Ex: 60 = 60%",
                0,
                100).getInt(60);

        BONUS_PER_LEVEL = (float) config.get(
                "balance",
                "bonusPerLevel",
                0.05,
                "Bônus de velocidade por nível",
                0.0,
                1.0).getDouble(0.05);

        DURABILITY_SAVE_START_LEVEL = config.get(
                "abilities", "durabilitySaveStartLevel", 3,
                "Nível mínimo para 'Durability Save' ativar (1-10)",
                1, 10).getInt(3);

        DURABILITY_SAVE_PER_LEVEL_PERCENT = config.get(
                "abilities", "durabilitySavePerLevelPercent", 5,
                "% de chance adicional por nível acima do 'startLevel' (0-50)",
                0, 50).getInt(5);

        DURABILITY_SAVE_MAX_PERCENT = config.get(
                "abilities", "durabilitySaveMaxPercent", 50,
                "% máxima de chance de salvar durabilidade (0-100)",
                0, 100).getInt(50);

        EXTRA_DROP_START_LEVEL = config.get(
                "abilities", "extraDropStartLevel", 6,
                "Nível mínimo para 'Extra Drop' ativar (1-10)",
                1, 10).getInt(6);

        EXTRA_DROP_PER_LEVEL_PERCENT = config.get(
                "abilities", "extraDropPerLevelPercent", 5,
                "% de chance adicional por nível acima do 'startLevel' (0-50)",
                0, 50).getInt(5);

        EXTRA_DROP_MAX_PERCENT = config.get(
                "abilities", "extraDropMaxPercent", 50,
                "% máxima de chance de drop extra (0-100)",
                0, 100).getInt(50);

        if (config.hasChanged()) {
            config.save();
        }
    }

    public static Configuration getConfig() {
        return config;
    }
}