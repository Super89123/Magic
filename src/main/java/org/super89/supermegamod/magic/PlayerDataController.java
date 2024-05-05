package org.super89.supermegamod.magic;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.io.File;
import java.io.IOException;

public class PlayerDataController implements Listener {
    private Magic plugin;
    public PlayerDataController(Magic plugin){this.plugin=plugin;}


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
    public int getNowPlayerState(Player player) {
        String playerUUID = player.getUniqueId().toString();
        File playerDataFile = new File(Magic.getPlugin().getDataFolder(), "playerdata.yml");
        FileConfiguration playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);
        int a = playerDataConfig.getInt(playerUUID + "." + "ostatokprm");

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
        public void setNowPlayerPkm(Player player, int ostatokprm) {
            String playerUUID = player.getUniqueId().toString();
            File playerDataFile = new File(Magic.getPlugin().getDataFolder(), "playerdata.yml");
            FileConfiguration playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);
            playerDataConfig.set(playerUUID + "." + "ostatokprm", ostatokprm);

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
            playerDataConfig.set(uuid + "." + "ostatokprm", -1);
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
                player.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, 25 * 20, 2));
                player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 25 * 20, 2));
                setNowPlayerThrist(player, Math.min(getNowPlayerThrist(player) + 1, 20));

            }
            if(meta.hasCustomModelData() && meta.getCustomModelData() == 1013 && getNowPlayerThrist(player) < 20){
                setNowPlayerThrist(player, Math.min(getNowPlayerThrist(player) + 2, 20));
            }
        }
    }
    public String calculatePlayerThirst(Player player){
        switch (getNowPlayerThrist(player)){
            case 0:
                return "-1 -1 -1 -1 -1 -1 -1 -1 -1 -1";
            case 1:
                return "0 -1 -1 -1 -1 -1 -1 -1 -1 -1";
            case 2:
                return "1 -1 -1 -1 -1 -1 -1 -1 -1 -1";
            case 3:
                return "1 0 -1 -1 -1 -1 -1 -1 -1 -1";
            case 4:
                return "1 1 -1 -1 -1 -1 -1 -1 -1 -1";
            case 5:
                return "1 1 0 -1 -1 -1 -1 -1 -1 -1";
            case 6:
                return "1 1 1 -1 -1 -1 -1 -1 -1 -1";
            case 7:
                return "1 1 1 0 -1 -1 -1 -1 -1 -1";
            case 8:
                return "1 1 1 1 -1 -1 -1 -1 -1 -1";
            case 9:
                return "1 1 1 1 0 -1 -1 -1 -1 -1";
            case 10:
                return "1 1 1 1 1 -1 -1 -1 -1 -1";
            case  11:
                return "1 1 1 1 1 0 -1 -1 -1 -1";
            case  12:
                return "1 1 1 1 1 1 -1 -1 -1 -1";
            case 13:
                return "1 1 1 1 1 1 0 -1 -1 -1";
            case 14:
                return "1 1 1 1 1 1 1 -1 -1 -1";
            case 15:
                return "1 1 1 1 1 1 1 0 -1 -1";
            case 16:
                return "1 1 1 1 1 1 1 1 -1 -1";
            case 17:
                return "1 1 1 1 1 1 1 1 0 -1";
            case 18:
                return "1 1 1 1 1 1 1 1 1 -1";
            case 19:
                return "1 1 1 1 1 1 1 1 1 0";
            default:
                return "1 1 1 1 1 1 1 1 1 1";
        }

    }
    @EventHandler
    public void playerDeath(PlayerDeathEvent event){
        setNowPlayerThrist(event.getPlayer(), 20);
    }

}
