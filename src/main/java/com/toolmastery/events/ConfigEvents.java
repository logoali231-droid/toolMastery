
package com.toolmastery.events;

import com.toolmastery.ToolMastery;
import com.toolmastery.config.MasteryConfig;
import com.toolmastery.data.ToolExperience;

import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = ToolMastery.MODID)
public class ConfigEvents {

    @SubscribeEvent
    public static void onConfigChanged(
            ConfigChangedEvent.OnConfigChangedEvent event) {

        if (event.getModID().equals(ToolMastery.MODID)) {
            MasteryConfig.sync();
        }
    }
        // ... métodos existentes: getBaseXP, getXPForLevel, getLevelForXP,
    //     getMiningSpeedBonus, getCombatDamageBonus ...

    /**
     * Chance (0.0–1.0) de a ferramenta não gastar durabilidade ao usar.
     * 0 até o nível 2; +5% por nível a partir do 3; cap em 50%.
     */
    public static float getDurabilitySaveChance(int level) {
        if (level < 3) return 0.0f;
        float chance = 0.05f * (level - 2);
        return Math.min(chance, 0.5f);
    }

    /**
     * Chance (0.0–1.0) de dropar um item extra ao minerar.
     * 0 até o nível 5; +5% por nível a partir do 6; cap em 50%.
     */
    public static float getExtraDropChance(int level) {
        if (level < 6) return 0.0f;
        float chance = 0.05f * (level - 5);
        return Math.min(chance, 0.5f);
    }
}

    

