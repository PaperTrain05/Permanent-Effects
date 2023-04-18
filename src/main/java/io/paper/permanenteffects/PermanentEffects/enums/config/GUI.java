package io.paper.permanenteffects.PermanentEffects.enums.config;

import com.google.common.collect.Lists;
import io.paper.permanenteffects.PermanentEffects.PermanentEffects;
import io.paper.permanenteffects.PermanentEffects.config.objects.ConfigFile;
import io.paper.permanenteffects.PermanentEffects.objects.Replacer;
import io.paper.permanenteffects.PermanentEffects.utils.ItemBuilder;
import io.paper.permanenteffects.PermanentEffects.utils.Utils;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.Inventory;

import java.util.List;

@AllArgsConstructor
public enum GUI {

    TITLE("title"),
    SIZE("slots"),
    POTION_DISPLAY("potion-item.displayname"),
    POTION_LORE("potion-item.lore"),
    POTION_LORE_MAXED("potion-item.maxed-lore");

    private final String path;
    private final ConfigFile file = PermanentEffects.getInstance().getFileManager().getFile("gui");

    public static Inventory getInventory() {

        Inventory inventory = Bukkit.createInventory(null, (Integer) GUI.SIZE.get(), GUI.TITLE.getMessage());

        ConfigFile file = PermanentEffects.getInstance().getFileManager().getFile("gui");

        for (String item : file.getConfig().getConfigurationSection("items").getKeys(false)) {
            inventory.setItem(Integer.parseInt(item), new ItemBuilder(
                    Material.valueOf(file.getConfig().getString("items." + item + ".material")),
                    (short) file.getConfig().getInt("items." + item + ".durability"))
                    .setName(Utils.textColor(file.getConfig().getString("items." + item + ".displayname")))
                    .setLore(Utils.textColor(file.getConfig().getStringList("items." + item + ".lore")))
                    .build());
        }
        return inventory;
    }

    public void send(CommandSender sender) {
        if (file.getConfig().get(path) instanceof List)
            for (String message : file.getConfig().getStringList(path))
                sender.sendMessage(Utils.textColor(message));
        else sender.sendMessage(Utils.textColor(file.getConfig().getString(path)));
    }

    public String getMessage() {
        return Utils.textColor(file.getConfig().getString(path));
    }

    public Object getMessage(Replacer... replacers) {
        if (file.getConfig().get(path) instanceof List) {
            List<String> messageList = Lists.newArrayList();
            for (String s : Utils.textColor(file.getConfig().getStringList(path))) {
                String message = s;
                for (Replacer replacer : replacers)
                    message = message.replace(replacer.getPlaceholder(), replacer.getReplacement());
                messageList.add(message);
            }
            return messageList;
        } else {
            String message = Utils.textColor(file.getConfig().getString(path));
            for (Replacer replacer : replacers) {
                message = message.replace(replacer.getPlaceholder(), replacer.getReplacement());
            }
            return message;
        }
    }

    public Object get() {
        return file.getConfig().get(path);
    }
}
