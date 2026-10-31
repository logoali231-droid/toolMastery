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
        if (!(event.getSource().getTrueSource() instanceof EntityPlayer))
            return;
        EntityPlayer player = (EntityPlayer) event.getSource().getTrueSource();
        if (player.isCreative())
            return;

        ItemStack stack = player.getHeldItemMainhand();
        if (stack.isEmpty())
            return;

        ToolExperience.Category cat = ToolExperience.getItemCategory(stack);
        if (cat != ToolExperience.Category.COMBAT)
            return;

        int oldLevel = ToolExperience.getLevel(stack);
        int newLevel = ToolExperience.addXP(stack, XP_PER_HIT, cat);

        if (newLevel > oldLevel) {
            LevelUpEvents.trigger(player, stack, newLevel);
        }
    }
}