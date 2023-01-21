package it.naick.permanenteffects.tasks;

import it.naick.permanenteffects.PermanentEffects;
import it.naick.permanenteffects.objects.EffectPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class EffectsTask extends BukkitRunnable {

    @Override
    public void run() {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            EffectPlayer effectPlayer = new EffectPlayer(onlinePlayer.getName());
            if (effectPlayer.getEffectList().isEmpty()) continue;
            for (PotionEffectType potionEffectType : effectPlayer.getEffectList().keySet()) {
                PotionEffect potionEffect = new PotionEffect(potionEffectType, Integer.MAX_VALUE, effectPlayer.getEffectList().get(potionEffectType) - 1, false, false);
                if (onlinePlayer.hasPotionEffect(potionEffectType)
                        && getPotionEffect(onlinePlayer, potionEffectType).getAmplifier() < potionEffect.getAmplifier())
                    onlinePlayer.removePotionEffect(potionEffectType);

                Bukkit.getScheduler().runTaskLater(PermanentEffects.getInstance(), () -> {
                    if (!onlinePlayer.hasPotionEffect(potionEffectType)
                            || getPotionEffect(onlinePlayer, potionEffectType) == null)
                        onlinePlayer.addPotionEffect(new PotionEffect(potionEffectType, Integer.MAX_VALUE, effectPlayer.getEffectList().get(potionEffectType) - 1, false, false));
                }, 5L);
            }
        }
    }

    private PotionEffect getPotionEffect(Player player, PotionEffectType potionEffectType) {
        for (PotionEffect activePotionEffect : player.getActivePotionEffects())
            if (potionEffectType == activePotionEffect.getType()) return activePotionEffect;
        return null;
    }
}
