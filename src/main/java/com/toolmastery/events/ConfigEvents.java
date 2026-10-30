
package com.toolmastery.events;

import com.toolmastery.ToolMastery;
import com.toolmastery.config.MasteryConfig;

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
}
