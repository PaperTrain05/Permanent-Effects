package io.paper.permanenteffects.PermanentEffects.config;

import io.paper.permanenteffects.PermanentEffects.config.objects.ConfigFile;
import io.paper.permanenteffects.PermanentEffects.config.objects.DefaultConfigFile;
import io.paper.permanenteffects.PermanentEffects.PermanentEffects;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.HashSet;
import java.util.Set;

public class FileManager implements IFileManager {
    @Getter
    private final Set<ConfigFile> files = new HashSet<>();

    public FileManager() {
        addFile(new DefaultConfigFile(PermanentEffects.getInstance(), "gui"));
        addFile(new DefaultConfigFile(PermanentEffects.getInstance(), "data"));
    }

    @Override
    public ConfigFile getFile(String name) {
        for (ConfigFile file : files) {
            if (file.getName().equalsIgnoreCase(name))
                return file;
        }
        return null;
    }

    @Override
    public YamlConfiguration getConfig(String name) {
        return getFile(name).getConfig();
    }

    @Override
    public void addFile(ConfigFile file) {
        files.add(file);
    }

    @Override
    public void removeFile(String name) {
        files.remove(getFile(name));
    }
}