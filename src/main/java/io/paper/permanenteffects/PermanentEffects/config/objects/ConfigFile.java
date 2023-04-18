package io.paper.permanenteffects.PermanentEffects.config.objects;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public interface ConfigFile {

    YamlConfiguration getConfig();

    File getFile();

    String getName();

    void save() throws IOException;

    void reload();

    default void saveAndReload() throws IOException {
        save();
        reload();
    }
}
