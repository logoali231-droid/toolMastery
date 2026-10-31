package com.toolmastery.events;

import com.toolmastery.abilities.MasteryAbilities;
import com.toolmastery.capability.ToolExperience;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CombatBonusEvents {

    @SubscribeEvent
    public void onLivingHurt(LivingHurtEvent event) {
        if (!(event.getSource().getTrueSource() instanceof EntityPlayer)) return;
        EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();

        ItemStack stack = player.getHeldItemMainhand();
        if (stack.isEmpty()) return;
        if (ToolExperience.getItemCategory(stack) != ToolExperience.Category.COMBAT) return;

        float bonus = MasteryAbilities.getCombatDamageBonus(stack);
        if (bonus > 0.0f) {
            event.setAmount(event.getAmount() + bonus);
        }
    }

    @SubscribeEvent
    public void onLivingAttack(LivingAttackEvent event) {
        if (!(event.getSource().getTrueSource() instanceof EntityPlayer)) return;
        if (!(event.getEntityLiving() instanceof EntityLivingBase)) return;

        EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
        ItemStack stack = player.getHeldItemMainhand();
        if (stack.isEmpty()) return;
        if (ToolExperience.getItemCategory(stack) != ToolExperience.Category.COMBAT) return;

        MasteryAbilities.maybeApplyWeakness(player, (EntityLivingBase) event.getEntityLiving(), stack);
    }
}