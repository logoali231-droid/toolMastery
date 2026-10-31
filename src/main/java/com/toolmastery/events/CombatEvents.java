package com.toolmastery.events;


import com.toolmastery.data.ToolExperience;
import net.minecraft.entity.player.EntityPlayer; // <-- adicione
import net.minecraft.item.ItemStack; // <-- adicione
import net.minecraftforge.event.entity.living.LivingAttackEvent; // <-- adicione
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent; // <-- adicione package

public class CombatEvents {

    private static final int XP_PER_HIT = 1;

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
        ToolExperience.ensureCategory(stack, cat);

        int oldLevel = ToolExperience.getLevel(stack);
        int newLevel = ToolExperience.addXP(stack, XP_PER_HIT, cat);

        if (newLevel > oldLevel) {
            LevelUpEvents.trigger(player, stack, newLevel);
        }
    }
}