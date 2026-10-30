package com.toolmastery.data;

import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import com.toolmastery.progression.MasteryLevels;
import java.util.Set;

public class ToolExperience {

    public static final String NBT_LEVEL = "TM_Level";
    public static final String NBT_XP = "TM_XP";
    public static final String NBT_CATEGORY = "TM_Category";

    public enum Category {
        MINING, CUTTING, HARVESTING, COMBAT
    }

    private static NBTTagCompound getTag(ItemStack stack) {
        NBTTagCompound tag = stack.getTagCompound();
        if (tag == null) {
            tag = new NBTTagCompound();
            stack.setTagCompound(tag);
        }
        return tag;
    }

    public static int getLevel(ItemStack stack) {
        return getTag(stack).getInteger(NBT_LEVEL);
    }

    public static int getXP(ItemStack stack) {
        return getTag(stack).getInteger(NBT_XP);
    }

    public static Category getCategory(ItemStack stack) {
        String name = getTag(stack).getString(NBT_CATEGORY);
        if (name.isEmpty()) return null;
        return Category.valueOf(name);
    }

    public static void ensureCategory(ItemStack stack, Category category) {
        if (getCategory(stack) == null) {
            getTag(stack).setString(NBT_CATEGORY, category.name());
        }
    }

    /**
     * Infere a categoria com base no tipo da ferramenta.
     * - Picareta → MINING
     * - Machado → CUTTING
     * - Tesoura/Enxada → HARVESTING
     * - Espada → COMBAT
     */
    public static Category getItemCategory(ItemStack stack) {
        Set<String> classes = stack.getItem().getToolClasses(stack);
        if (classes.contains("pickaxe")) return Category.MINING;
        if (classes.contains("axe")) return Category.CUTTING;
        if (classes.contains("shears") || classes.contains("hoe")) return Category.HARVESTING;
        if (stack.getItem() instanceof ItemSword) return Category.COMBAT;
        return null;
    }

    public static void addXP(ItemStack stack, int amount, Category category) {
        if (stack == null || stack.isEmpty() || amount <= 0 || category == null) return;

        NBTTagCompound tag = getTag(stack);

        String saved = tag.getString(NBT_CATEGORY);
        if (saved.isEmpty()) {
            tag.setString(NBT_CATEGORY, category.name());
        } else if (!saved.equals(category.name())) {
            return; // categoria travada e diferente da ação atual
        }

        int xp = tag.getInteger(NBT_XP) + amount;
        int oldLevel = tag.getInteger(NBT_LEVEL);
        int newLevel = MasteryLevels.getLevelForXP(xp);

        tag.setInteger(NBT_XP, xp);
        if (newLevel > oldLevel) {
            tag.setInteger(NBT_LEVEL, newLevel);
            // Aqui você poderia enviar uma mensagem de "Level up!" ao jogador
        }
    }
}