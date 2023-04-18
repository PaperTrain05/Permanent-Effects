package io.paper.permanenteffects.PermanentEffects.utils;

import com.google.common.collect.Lists;
import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

import java.util.List;

@UtilityClass
public class Utils {
    public String textColor(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public List<String> textColor(List<String> text) {
        List<String> colored = Lists.newArrayList();
        text.forEach(s -> colored.add(ChatColor.translateAlternateColorCodes('&', s)));
        return colored;
    }
}
