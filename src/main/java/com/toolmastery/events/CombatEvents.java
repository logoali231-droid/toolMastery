package com.toolmastery.events;

import com.toolmastery.data.ToolExperience;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CombatEvents {

    @SubscribeEvent
    public void onLivingAttack(LivingAttackEvent event) {
        // Verifica quem está atacando
        if (!(event.getSource().getTrueSource() instanceof EntityPlayer)) return;

        EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
        ItemStack stack = player.getHeldItemMainhand();
        if (stack.isEmpty()) return;

        ToolExperience.Category toolCat = ToolExperience.getItemCategory(stack);
        if (toolCat != ToolExperience.Category.COMBAT) return;

        ToolExperience.ensureCategory(stack, ToolExperience.Category.COMBAT);
        ToolExperience.addXP(stack, 2, ToolExperience.Category.COMBAT);
    }
}