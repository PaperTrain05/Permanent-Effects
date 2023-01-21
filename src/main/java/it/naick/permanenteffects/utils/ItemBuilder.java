package it.naick.permanenteffects.utils;

import com.google.common.collect.Lists;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ItemBuilder {

    private final ItemStack itemStack;
    private final ItemMeta itemMeta;

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder(Material material, short durability) {
        this.itemStack = new ItemStack(material, 1, durability);
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder setName(String text) {
        itemMeta.setDisplayName(Utils.textColor(text));
        return this;
    }


    public ItemBuilder setLore(String... lore) {
        List<String> stringList = Lists.newArrayList();

        for (String s : lore)
            stringList.add(Utils.textColor(s));

        itemMeta.setLore(stringList);
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        List<String> stringList = Lists.newArrayList();

        for (String s : lore)
            stringList.add(Utils.textColor(s));

        itemMeta.setLore(stringList);
        return this;
    }

    public ItemStack build() {
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }
}
