package com.toolmastery.features;

import com.toolmastery.data.ToolExperience;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ToolRepair {
    /**
     * Custo: 5 XP acumulados da ferramenta por 1 ponto de durabilidade restaurada.
     */
    public static final int XP_PER_DURABILITY = 5;

    public static boolean repair(ItemStack stack, EntityPlayer player, int durabilityToRestore) {
        if (stack == null || stack.isEmpty() || player == null) return false;
        if (!stack.isItemDamaged()) return false;

        int damage = stack.getItemDamage();
        int toRestore = Math.min(durabilityToRestore, damage);
        if (toRestore <= 0) return false;

        int xpCost = toRestore * XP_PER_DURABILITY;
        if (!ToolExperience.consumeXP(stack, xpCost)) return false;

        stack.setItemDamage(damage - toRestore);
        return true;
    }
}