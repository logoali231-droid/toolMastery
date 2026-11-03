package com.toolmastery.events;

import com.toolmastery.data.ToolExperience;
import com.toolmastery.progression.MasteryLevels;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ItemEvents {

    @SubscribeEvent
    public void onTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (stack.isEmpty()) return;

        ToolExperience.Category cat = ToolExperience.getItemCategory(stack);
        if (cat == null) return;

        int level = ToolExperience.getLevel(stack);
        if (level <= 0) return;

        event.getToolTip().add("");
        event.getToolTip().add("§6§lMAESTRIA DA FERRAMENTA");
        event.getToolTip().add("§7Categoria: §e" + cat.name().toLowerCase());
        event.getToolTip().add("§7Nível: §f" + level + (level >= ToolExperience.MAX_LEVEL ? " §6[MAESTRE]" : ""));
        event.getToolTip().add(ToolExperience.getProgressBar(stack));

        float bonus = MasteryLevels.getMiningSpeedBonus(level);
        if (cat == ToolExperience.Category.MINING && bonus > 0) {
            event.getToolTip().add("§aVelocidade de mineração: §f+" + (int) (bonus * 100) + "%");
        }

        String desc = MasteryLevels.getAbilityDescription(cat, level);
        if (desc != null && !desc.isEmpty()) {
            event.getToolTip().add("§bHabilidade: §f" + desc);
        }

        event.getToolTip().add("");
    }

    
}