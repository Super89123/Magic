package org.super89.supermegamod.magic;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import org.super89.supermegamod.magic.Utils.ItemUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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



