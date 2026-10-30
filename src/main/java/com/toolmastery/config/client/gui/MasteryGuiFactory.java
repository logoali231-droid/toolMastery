package com.toolmastery.config.client.gui;

import com.toolmastery.ToolMastery;
import com.toolmastery.config.MasteryConfig;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;
import net.minecraftforge.common.config.ConfigElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class MasteryGuiFactory implements IModGuiFactory {

    @Override
    public void initialize(Minecraft minecraftInstance) {
    }

    @Override
    public boolean hasConfigGui() {
        return true;
    }

    @Override
    public GuiScreen createConfigGui(GuiScreen parentScreen) {

        List<IConfigElement> elements = new ArrayList<>();

        elements.add(new ConfigElement(
                MasteryConfig.getConfig().getCategory("progression")
        ));

        elements.add(new ConfigElement(
                MasteryConfig.getConfig().getCategory("balance")
        ));

        return new GuiConfig(
                parentScreen,
                elements,
                ToolMastery.MODID,
                false,
                false,
                "ToolMastery Configuration"
        );
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return Collections.emptySet();
    }
}