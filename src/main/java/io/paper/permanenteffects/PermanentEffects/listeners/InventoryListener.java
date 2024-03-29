package io.paper.permanenteffects.PermanentEffects.listeners;

import io.paper.permanenteffects.PermanentEffects.listeners.manager.ListenerManager;
import io.paper.permanenteffects.PermanentEffects.objects.EffectPlayer;
import io.paper.permanenteffects.PermanentEffects.objects.Replacer;
import io.paper.permanenteffects.PermanentEffects.PermanentEffects;
import io.paper.permanenteffects.PermanentEffects.enums.config.GUI;
import io.paper.permanenteffects.PermanentEffects.enums.messages.Messages;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.potion.PotionEffectType;

import static io.paper.permanenteffects.PermanentEffects.PermanentEffects.EFFECTS;
import static io.paper.permanenteffects.PermanentEffects.PermanentEffects.SLOTS;

public class InventoryListener extends ListenerManager {

    public InventoryListener(PermanentEffects plugin) {
        super(plugin);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null || !e.getClickedInventory().getTitle().equals(GUI.TITLE.getMessage()))
            return;
        e.setCancelled(true);
        if (!SLOTS.containsKey(e.getSlot())) return;
        Player player = (Player) e.getWhoClicked();
        PotionEffectType potionEffectType = SLOTS.get(e.getSlot());
        EffectPlayer effectPlayer = new EffectPlayer(player.getName());

        if (effectPlayer.getLevel(potionEffectType) == EFFECTS.get(potionEffectType).getMaxLevel()) {
            Messages.MAXED.send(player);
            player.closeInventory();
            return;
        }

        if (!PermanentEffects.getInstance().getEcon().has(player, EFFECTS.get(potionEffectType).getLevelCosts().get(effectPlayer.getLevel(potionEffectType)))) {
            Messages.NO_MONEY.send(player);
            player.closeInventory();
            return;
        }

        PermanentEffects.getInstance().getEcon().withdrawPlayer(player, EFFECTS.get(potionEffectType).getLevelCosts().get(effectPlayer.getLevel(potionEffectType)));

        if (effectPlayer.getLevel(potionEffectType) == 0) {
            Messages.BOUGHT.send(player, new Replacer("%effect%", EFFECTS.get(potionEffectType).getDisplayName()));
            effectPlayer.add(potionEffectType);
        } else {
            Messages.UPGRADED.send(player, new Replacer("%effect%", EFFECTS.get(potionEffectType).getDisplayName()), new Replacer("%level%", String.valueOf(effectPlayer.getLevel(potionEffectType) + 1)));
            effectPlayer.upgrade(potionEffectType);
        }

        player.closeInventory();
    }
}
