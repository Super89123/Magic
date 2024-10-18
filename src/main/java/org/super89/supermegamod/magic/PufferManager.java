package org.super89.supermegamod.magic;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;


import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PufferManager implements Listener {

    public final Map<Location, Inventory> pufferInventories = new HashMap<>();
    public final Map<Location, Inventory> pufferUpgradeInventories = new HashMap<>();
    public final FileConfiguration config;
    private final File configFile;
    private final Map<Location, Integer> pufferCookTime = new HashMap<>(); // Время приготовления для каждого очистителя
    InventoryWithCoolThings inventoryWithCoolThings = new InventoryWithCoolThings();
    WaitAsync waitAsync = new WaitAsync(Bukkit.getScheduler());

    public PufferManager(Magic plugin) throws IOException, InvalidConfigurationException {
        configFile = new File(plugin.getDataFolder(), "puffers.yml");
        config = new YamlConfiguration();
        config.load(configFile);
    }


}



