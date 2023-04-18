package io.paper.permanenteffects.PermanentEffects.commands;

import io.paper.permanenteffects.PermanentEffects.enums.config.GUI;
import io.paper.permanenteffects.PermanentEffects.objects.EffectPlayer;
import io.paper.permanenteffects.PermanentEffects.objects.Replacer;
import io.paper.permanenteffects.PermanentEffects.utils.Utils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

import static io.paper.permanenteffects.PermanentEffects.PermanentEffects.EFFECTS;

@SuppressWarnings("unchecked")
public class PermanentEffectsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;

        Inventory inventory = GUI.getInventory();

        for (PotionEffectType potionEffectType : EFFECTS.keySet()) {
            int slot = EFFECTS.get(potionEffectType).getSlot();
            inventory.setItem(slot, this.potion(player, potionEffectType));
        }

        player.openInventory(inventory);

        return true;
    }

    private ItemStack potion(Player player, PotionEffectType potionEffectType) {
        EffectPlayer effectPlayer = new EffectPlayer(player.getName());
        ItemStack itemStack = new ItemStack(Material.POTION, 1);
        PotionMeta itemMeta = (PotionMeta) itemStack.getItemMeta();

        itemMeta.setDisplayName((String) GUI.POTION_DISPLAY.getMessage(new Replacer("%effect%", EFFECTS.get(potionEffectType).getDisplayName())));

        itemMeta.setMainEffect(potionEffectType);
        itemMeta.addCustomEffect(potionEffectType.createEffect(Integer.MAX_VALUE, Integer.MAX_VALUE), true);
        itemMeta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);

        if (EFFECTS.get(potionEffectType).getMaxLevel() <= effectPlayer.getLevel(potionEffectType))
            itemMeta.setLore(Utils.textColor((List<String>) GUI.POTION_LORE_MAXED.get()));
        else
            itemMeta.setLore((List<String>) GUI.POTION_LORE.getMessage(
                    new Replacer("%level%", String.valueOf(effectPlayer.getLevel(potionEffectType))),
                    new Replacer("%price%", String.valueOf(EFFECTS.get(potionEffectType).getLevelCosts().get(effectPlayer.getLevel(potionEffectType))))));

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }
}
