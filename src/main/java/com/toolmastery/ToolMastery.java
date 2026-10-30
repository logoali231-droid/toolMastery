package com.toolmastery;

import com.toolmastery.config.MasteryConfig;
import com.toolmastery.events.CombatEvents;
import com.toolmastery.events.MiningEvents;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ToolMastery.MODID, name = ToolMastery.NAME, version = ToolMastery.VERSION, guiFactory = "com.toolmastery.config.client.gui.MasteryGuiFactory")
public class ToolMastery {
    public static final String MODID = "toolmastery";
    public static final String NAME = "ToolMastery";
    public static final String VERSION = "1.0.0";

    @Mod.Instance
    public static ToolMastery instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MasteryConfig.init(event.getSuggestedConfigurationFile());

        // Registra os listeners de evento
        MinecraftForge.EVENT_BUS.register(new MiningEvents());
        MinecraftForge.EVENT_BUS.register(new CombatEvents());
    }
}