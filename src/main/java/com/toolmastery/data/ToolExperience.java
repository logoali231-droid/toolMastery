package com.toolmastery.data;

import net.minecraft.item.ItemSword;
import net.minecraft.item.Item; // <-- adicione esta linha
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.item.ItemStack;

import java.util.Set;

import com.toolmastery.progression.MasteryLevels;

public class ToolExperience {

    public static final String NBT_LEVEL = "TM_Level";
    public static final String NBT_XP = "TM_XP";
    public static final String NBT_CATEGORY = "TM_Category";
    public static final int MAX_LEVEL = MasteryLevels.MAX_LEVEL;

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
        if (name.isEmpty())
            return null;
        return Category.valueOf(name);
    }

    public static void ensureCategory(ItemStack stack, Category category) {
        if (getCategory(stack) == null) {
            getTag(stack).setString(NBT_CATEGORY, category.name());
        }
    }

    public static void setLevel(ItemStack stack, int level) {
        getTag(stack).setInteger(NBT_LEVEL, level);
    }

    public static void setXP(ItemStack stack, int xp) {
        getTag(stack).setInteger(NBT_XP, xp);
    }

    public static Category getItemCategory(ItemStack stack) {
        if (stack.isEmpty())
            return null;
        Item item = stack.getItem();
        Set<String> classes = item.getToolClasses(stack);
        if (classes.contains("pickaxe"))
            return Category.MINING;
        if (classes.contains("axe"))
            return Category.MINING;
        if (classes.contains("shovel"))
            return Category.MINING;
        if (classes.contains("shears"))
            return Category.MINING;
        if (classes.contains("hoe"))
            return Category.MINING;
        if (classes.contains("sword"))
            return Category.COMBAT;
        return null;
    }

    /**
     * Adiciona XP e retorna o novo nível.
     * Se o nível subir, retorna o novo nível (> nível anterior).
     */
public static int addXP(ItemStack stack, int amount, Category category) {
    NBTTagCompound tag = getTag(stack);
    int xp = tag.getInteger(NBT_XP);      // use NBT_XP
    int level = tag.getInteger(NBT_LEVEL); // use NBT_LEVEL
    if (level >= MasteryLevels.MAX_LEVEL) return level;

    xp += amount;
    int newLevel = MasteryLevels.getLevelForXP(xp);
    tag.setInteger(NBT_XP, xp);            // use NBT_XP
    tag.setInteger(NBT_LEVEL, newLevel);   // use NBT_LEVEL
    return newLevel;
}
    public static String getProgressBar(ItemStack stack) {
        int level = getLevel(stack);
        int xp = getXP(stack);
        int xpForCurrent = MasteryLevels.getXPForLevel(level);
        int xpForNext = MasteryLevels.getXPForLevel(level + 1);
        int range = xpForNext - xpForCurrent;
        int current = xp - xpForCurrent;

        int bars = 10;
        int filled = range > 0 ? (int) Math.round((double) current / range * bars) : bars;

        StringBuilder sb = new StringBuilder("§7[");
        for (int i = 0; i < bars; i++) {
            sb.append(i < filled ? "§a|" : "§7|");
        }
        sb.append("§7] §f").append(current).append("§7/§e").append(range);
        return sb.toString();
    }

    /**
     * Tenta consumir XP da ferramenta. Retorna true se conseguiu.
     */
    public static boolean consumeXP(ItemStack stack, int amount) {
        if (stack.isEmpty())
            return false;
        NBTTagCompound tag = getTag(stack);
        int xp = tag.getInteger("xp");
        if (xp < amount)
            return false;

        int newXP = xp - amount;
        tag.setInteger("xp", newXP);
        tag.setInteger("level", MasteryLevels.getLevelForXP(newXP));
        return true;
    }
}