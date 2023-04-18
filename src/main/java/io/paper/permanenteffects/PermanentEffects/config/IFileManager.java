package io.paper.permanenteffects.PermanentEffects.config;

import io.paper.permanenteffects.PermanentEffects.config.objects.ConfigFile;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.Set;

public interface IFileManager {

    ConfigFile getFile(String name);

    Set<ConfigFile> getFiles();

    YamlConfiguration getConfig(String name);

    void addFile(ConfigFile file);

    void removeFile(String name);
}
