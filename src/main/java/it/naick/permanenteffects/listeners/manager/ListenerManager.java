package it.naick.permanenteffects.listeners.manager;

import it.naick.permanenteffects.PermanentEffects;
import lombok.Getter;
import org.bukkit.event.Listener;

@Getter
public class ListenerManager implements Listener {
    private final PermanentEffects plugin;

    public ListenerManager(PermanentEffects plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
}
