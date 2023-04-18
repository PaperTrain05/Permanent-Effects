package io.paper.permanenteffects.PermanentEffects.enums.messages;

import io.paper.permanenteffects.PermanentEffects.objects.Replacer;
import io.paper.permanenteffects.PermanentEffects.utils.Utils;
import io.paper.permanenteffects.PermanentEffects.PermanentEffects;
import lombok.AllArgsConstructor;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

import java.util.List;

@AllArgsConstructor
public enum Messages {

    MAXED("messages.maxed"),
    BOUGHT("messages.bought"),
    UPGRADED("messages.upgraded"),
    NO_MONEY("messages.no-money");

    private final String path;

    private final Configuration file = PermanentEffects.getInstance().getConfig();

    public void send(Player player, Replacer... replacers) {
        if (file.get(path) instanceof List) {
            for (String s : Utils.textColor(file.getStringList(path))) {
                String message = s;
                for (Replacer replacer : replacers)
                    message = message.replace(replacer.getPlaceholder(), replacer.getReplacement());
                player.sendMessage(message);
            }
        } else {
            String message = Utils.textColor(file.getString(path));
            for (Replacer replacer : replacers) {
                message = message.replace(replacer.getPlaceholder(), replacer.getReplacement());
            }
            player.sendMessage(message);
        }
    }
}
