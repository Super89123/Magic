package org.super89.supermegamod.magic;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;

public class Mana implements Listener {
    private Magic plugin;
    public Mana(Magic plugin){this.plugin=plugin;}


    public int getNowPlayerMana(Player player) {
        String playerUUID = player.getUniqueId().toString();
        File playerDataFile = new File(Magic.getPlugin().getDataFolder(), "playerdata.yml");
        FileConfiguration playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);
        int a = playerDataConfig.getInt(playerUUID + "." + "nowmana");

        try {
            playerDataConfig.save(playerDataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return a;
    }
    public void setNowPlayerMana(Player player, int mana) {
        String playerUUID = player.getUniqueId().toString();
        File playerDataFile = new File(Magic.getPlugin().getDataFolder(), "playerdata.yml");
        FileConfiguration playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);
        playerDataConfig.set(playerUUID + "." + "nowmana", mana);

        try {
            playerDataConfig.save(playerDataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }






    }
    public int getNowPlayerProkachka(Player player) {
        String playerUUID = player.getUniqueId().toString();
        File playerDataFile = new File(Magic.getPlugin().getDataFolder(), "playerdata.yml");
        FileConfiguration playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);
        int a = playerDataConfig.getInt(playerUUID + "." + "prokachka");

        try {
            playerDataConfig.save(playerDataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return a;
    }
    public int getMaxPlayerMana(Player player) {
        String playerUUID = player.getUniqueId().toString();
        File playerDataFile = new File(Magic.getPlugin().getDataFolder(), "playerdata.yml");
        FileConfiguration playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);
        int a = playerDataConfig.getInt(playerUUID + "." + "maxmana");

        try {
            playerDataConfig.save(playerDataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return a;
    }
    public void setNowPlayerProkachka(Player player, int prokachka) {
        String playerUUID = player.getUniqueId().toString();
        File playerDataFile = new File(Magic.getPlugin().getDataFolder(), "playerdata.yml");
        FileConfiguration playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);
        playerDataConfig.set(playerUUID + "." + "prokachka", prokachka);

        try {
            playerDataConfig.save(playerDataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setMaxPlayerMana(Player player, int maxmana) {
        String playerUUID = player.getUniqueId().toString();
        File playerDataFile = new File(Magic.getPlugin().getDataFolder(), "playerdata.yml");
        FileConfiguration playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);
        playerDataConfig.set(playerUUID + "." + "maxmana", maxmana);

        try {
            playerDataConfig.save(playerDataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }






    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        String uuid = player.getUniqueId().toString();
        File playerDataFile = new File(Magic.getPlugin().getDataFolder(), "playerdata.yml");
        FileConfiguration playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);
        if(!player.hasPlayedBefore()){
            playerDataConfig.set(uuid + "." + "maxmana", 10);
            playerDataConfig.set(uuid + "." + "nowmana", 10);
            playerDataConfig.set(uuid + "." + "prokachka", 0);
            int slot = 17;
            player.getInventory().setItem(slot, new ItemStack(Material.NETHER_STAR));


        }
        try {
            playerDataConfig.save(playerDataFile);
        }catch (IOException e){
            e.printStackTrace();
        }


    }
}
