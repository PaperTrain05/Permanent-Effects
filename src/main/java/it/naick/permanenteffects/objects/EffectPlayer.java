package it.naick.permanenteffects.objects;

import com.google.common.collect.Maps;
import it.naick.permanenteffects.PermanentEffects;
import it.naick.permanenteffects.config.objects.ConfigFile;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.potion.PotionEffectType;

import java.io.IOException;
import java.util.HashMap;

import static it.naick.permanenteffects.PermanentEffects.PLAYERS;

public class EffectPlayer {

    @Getter
    private final String playerName;
    @Getter
    private final HashMap<PotionEffectType, Integer> effectList;
    private final ConfigFile config = PermanentEffects.getInstance().getFileManager().getFile("data");
    private final YamlConfiguration yaml = PermanentEffects.getInstance().getFileManager().getConfig("data");

    public EffectPlayer(String playerName) {
        this.playerName = playerName;
        this.effectList = PLAYERS.getOrDefault(playerName, Maps.newHashMap());
    }

    public static void loadAll() {
        ConfigFile file = PermanentEffects.getInstance().getFileManager().getFile("data");

        for (String key : file.getConfig().getKeys(false)) {
            HashMap<PotionEffectType, Integer> effects = Maps.newHashMap();
            for (String s : file.getConfig().getConfigurationSection(key).getKeys(false)) {
                effects.put(PotionEffectType.getByName(s), file.getConfig().getConfigurationSection(key).getInt(s));
            }
            PLAYERS.put(key, effects);
        }
    }

    public int getLevel(PotionEffectType potionEffectType) {
        return this.effectList.getOrDefault(potionEffectType, 0);
    }

    public void add(PotionEffectType potionEffectType) {
        this.effectList.put(potionEffectType, 1);

        PLAYERS.put(playerName, this.effectList);

        yaml.set(this.playerName + "." + potionEffectType.getName(), 1);
        try {
            config.saveAndReload();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void add(PotionEffectType potionEffectType, Integer level) {
        this.effectList.put(potionEffectType, level);

        PLAYERS.put(playerName, this.effectList);

        yaml.set(this.playerName + "." + potionEffectType.getName(), level);
        try {
            config.saveAndReload();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void upgrade(PotionEffectType potionEffectType) {
        this.effectList.put(potionEffectType, this.effectList.getOrDefault(potionEffectType, 0) + 1);

        PLAYERS.put(playerName, this.effectList);

        yaml.set(this.playerName + "." + potionEffectType.getName(), this.effectList.get(potionEffectType));
        try {
            config.saveAndReload();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
