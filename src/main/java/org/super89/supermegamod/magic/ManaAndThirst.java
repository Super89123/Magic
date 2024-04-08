package org.super89.supermegamod.magic;

import org.bukkit.Material;
import org.bukkit.PortalType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

public class ManaAndThirst implements Listener {
    private Magic plugin;
    public ManaAndThirst(Magic plugin){this.plugin=plugin;}


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
    public void setNowPlayerThrist(Player player, int thrist) {
        String playerUUID = player.getUniqueId().toString();
        File playerDataFile = new File(Magic.getPlugin().getDataFolder(), "playerdata.yml");
        FileConfiguration playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);
        playerDataConfig.set(playerUUID + "." + "nowthrist", thrist);

        try {
            playerDataConfig.save(playerDataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }






    }
    public int getNowPlayerThrist(Player player) {
        String playerUUID = player.getUniqueId().toString();
        File playerDataFile = new File(Magic.getPlugin().getDataFolder(), "playerdata.yml");
        FileConfiguration playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);
        int a = playerDataConfig.getInt(playerUUID + "." + "nowthrist");

        try {
            playerDataConfig.save(playerDataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return a;
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
            playerDataConfig.set(uuid + "." + "maxmana", 100);
            playerDataConfig.set(uuid + "." + "nowmana", 100);
            playerDataConfig.set(uuid + "." + "prokachka", 0);
            playerDataConfig.set(uuid + "." + "nowthrist", 20);
            int slot = 17;
            try {
                playerDataConfig.save(playerDataFile);
            }catch (IOException e){
                e.printStackTrace();
            }



        }



    }
    @EventHandler
    public void onConsume(PlayerItemConsumeEvent event){
        ItemStack item = event.getItem();
        Player player = event.getPlayer();
        if(item.hasItemMeta() && item.getItemMeta() instanceof PotionMeta) {
            PotionMeta meta = (PotionMeta) item.getItemMeta();
            if(meta.getBasePotionType().equals(PotionType.WATER) || meta.getBasePotionType().equals(PotionType.AWKWARD) || meta.getBasePotionType().equals(PotionType.MUNDANE) && getNowPlayerThrist(player) < 20){
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60 * 20, 2));
                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60 * 20, 2));
                player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 60 * 20, 2));
                setNowPlayerThrist(player, Math.min(getNowPlayerThrist(player) + 2, 20));

            }
            if(meta.hasCustomModelData() && meta.getCustomModelData() == 1013 && getNowPlayerThrist(player) < 20){
                setNowPlayerThrist(player, Math.min(getNowPlayerThrist(player) + 2, 20));
            }
        }
    }
}
