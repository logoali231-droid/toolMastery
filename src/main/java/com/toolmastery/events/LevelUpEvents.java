package com.toolmastery.events;

import com.toolmastery.data.ToolExperience;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.init.SoundEvents;           // <-- adicione
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class LevelUpEvents {

    public static void trigger(EntityPlayer player, ItemStack stack, int newLevel) {
        if (player == null || stack == null || stack.isEmpty()) return;
        World world = player.world;
        if (world.isRemote) return;

        // Partículas de encantamento
        for (int i = 0; i < 25; i++) {
            double ox = (world.rand.nextDouble() - 0.5) * 0.4;
            double oy = (world.rand.nextDouble() - 0.5) * 0.4;
            double oz = (world.rand.nextDouble() - 0.5) * 0.4;
            world.spawnParticle(EnumParticleTypes.ENCHANTMENT_TABLE,
                    player.posX + ox, player.posY + 1.0, player.posZ + oz, 0, 0, 0);
        }

        // Som de level up do jogador
        SoundEvent sound = SoundEvents.ENTITY_PLAYER_LEVELUP;
        world.playSound(null, player.posX, player.posY, player.posZ, sound, SoundCategory.PLAYERS, 1.0f, 1.0f);

        // Mensagem no chat
        String nome = stack.getDisplayName();
        String msg = "§6[ToolMastery] §fSua " + nome + " §aalcancou o nível §e" + newLevel + "§a!";
        player.sendMessage(new TextComponentString(msg));

        if (newLevel >= ToolExperience.MAX_LEVEL) {
            player.sendMessage(new TextComponentString("§6[ToolMastery] §eITEM MESTRE! §fEsta ferramenta atingiu o domínio máximo."));
        }
    }
}