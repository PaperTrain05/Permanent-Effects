package it.naick.permanenteffects;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import it.naick.permanenteffects.commands.PermanentEffectsCommand;
import it.naick.permanenteffects.config.FileManager;
import it.naick.permanenteffects.config.IFileManager;
import it.naick.permanenteffects.config.objects.ConfigFile;
import it.naick.permanenteffects.datas.EffectData;
import it.naick.permanenteffects.listeners.InventoryListener;
import it.naick.permanenteffects.objects.EffectPlayer;
import it.naick.permanenteffects.tasks.EffectsTask;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.LinkedList;

@Getter
public final class PermanentEffects extends JavaPlugin {

    public final static HashMap<String, HashMap<PotionEffectType, Integer>> PLAYERS = Maps.newHashMap();
    public final static HashMap<PotionEffectType, EffectData> EFFECTS = Maps.newHashMap();
    public final static HashMap<Integer, PotionEffectType> SLOTS = Maps.newHashMap();

    @Getter
    private static PermanentEffects instance;

    private IFileManager fileManager;
    private Economy econ;

    @Override
    public void onLoad() {
        instance = this;

        this.fileManager = new FileManager();
    }

    @Override
    public void onEnable() {
        // Plugin startup logic

        saveDefaultConfig();



        this.getCommand("permanenteffect").setExecutor(new PermanentEffectsCommand());

        this.loadEffects();

        EffectPlayer.loadAll();

        new InventoryListener(this);

        new EffectsTask().runTaskTimer(this, 0L, 20L);

        if (!setupEconomy()) {
            Bukkit.getConsoleSender().sendMessage("§4|§c You need Vault to load this plugin");
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        Bukkit.getScheduler().cancelTasks(this);
    }

    private void loadEffects() {
        ConfigFile file = PermanentEffects.getInstance().getFileManager().getFile("gui");

        for (String effect : file.getConfig().getConfigurationSection("effects").getKeys(false)) {
            int slot = file.getConfig().getConfigurationSection("effects").getInt(effect + ".slot");
            String displayName = file.getConfig().getConfigurationSection("effects").getString(effect + ".displayname");
            LinkedList<Double> costsList = Lists.newLinkedList();
            file.getConfig().getConfigurationSection("effects").getConfigurationSection(effect).getConfigurationSection("cost").getKeys(false).forEach(s -> costsList.add(file.getConfig().getDouble("effects." + effect + ".cost." + s)));

            EFFECTS.put(PotionEffectType.getByName(effect), new EffectData(slot, displayName, costsList.size(), costsList));
            SLOTS.put(slot, PotionEffectType.getByName(effect));
        }
    }

    private boolean setupEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
}
