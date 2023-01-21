package it.naick.permanenteffects.enums.messages;

import it.naick.permanenteffects.PermanentEffects;
import it.naick.permanenteffects.objects.Replacer;
import it.naick.permanenteffects.utils.Utils;
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
