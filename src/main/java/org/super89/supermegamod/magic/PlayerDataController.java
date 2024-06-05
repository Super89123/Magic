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
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import java.io.File;
import java.io.IOException;

public class PlayerDataController implements Listener {
    private Magic plugin;
    public PlayerDataController(Magic plugin){this.plugin=plugin;}
    String bottlefull3 = " ";
    String bottlefull2 = " ";
    String bottlefull = " ";




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
            if(meta.hasCustomModelData() && meta.getCustomModelData() == 2030 && getNowPlayerThrist(player) < 20){
                setNowPlayerThrist(player, Math.min(getNowPlayerThrist(player) + 2, 20));
            }
            if(meta.hasCustomModelData() && meta.getCustomModelData() == 2031 && getNowPlayerThrist(player) < 20){
                setNowPlayerThrist(player, Math.min(getNowPlayerThrist(player) + 3, 20));
                player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 100 * 20, 10, false,false,false));
            }
        }
    }
    public String calculatePlayerThirst(Player player){
        switch (getNowPlayerThrist(player)){
            case 0:
                return bottlefull3+bottlefull3+bottlefull3+bottlefull3+bottlefull3+bottlefull3+bottlefull3+bottlefull3+bottlefull3+bottlefull3;
            case 1:
                return bottlefull3+ bottlefull3+ bottlefull3+ bottlefull3+ bottlefull3+ bottlefull3+ bottlefull3+ bottlefull3+ bottlefull3+ bottlefull2;
            case 2:
                return bottlefull3+ bottlefull3+ bottlefull3+ bottlefull3+ bottlefull3+ bottlefull3+ bottlefull3+ bottlefull3+ bottlefull3+ bottlefull;
            case 3:
                return bottlefull3+ bottlefull3+ bottlefull3+ bottlefull3+ bottlefull3+ bottlefull3+ bottlefull3+ bottlefull3+ bottlefull2+ bottlefull;
            case 4:
                return bottlefull3+ bottlefull3+ bottlefull3 +bottlefull3+ bottlefull3+ bottlefull3+ bottlefull3+ bottlefull3+ bottlefull+ bottlefull;
            case 5:
                return bottlefull3+ bottlefull3+ bottlefull3+ bottlefull3 +bottlefull3 +bottlefull3 +bottlefull3+ bottlefull2+ bottlefull+ bottlefull;
            case 6:
                return bottlefull3+ bottlefull3 +bottlefull3 +bottlefull3 +bottlefull3 +bottlefull3+ bottlefull3+ bottlefull+ bottlefull +bottlefull;
            case 7:
                return bottlefull3+ bottlefull3+ bottlefull3+ bottlefull3 +bottlefull3 +bottlefull3+bottlefull2+ bottlefull +bottlefull +bottlefull;
            case 8:
                return bottlefull3+ bottlefull3+ bottlefull3+ bottlefull3+bottlefull3+ bottlefull3+ bottlefull+ bottlefull+ bottlefull+ bottlefull;
            case 9:
                return bottlefull3 +bottlefull3+ bottlefull3 +bottlefull3+ bottlefull3+ bottlefull2 +bottlefull+ bottlefull+ bottlefull+bottlefull;
            case 10:
                return bottlefull3+bottlefull3 +bottlefull3 +bottlefull3 +bottlefull3+ bottlefull +bottlefull +bottlefull +bottlefull +bottlefull;
            case  11:
                return bottlefull3+ bottlefull3+bottlefull3+ bottlefull3+ bottlefull2+ bottlefull+ bottlefull+ bottlefull+ bottlefull +bottlefull;
            case  12:
                return bottlefull3+ bottlefull3+ bottlefull3+ bottlefull3+ bottlefull+ bottlefull +bottlefull+bottlefull+ bottlefull+ bottlefull;
            case 13:
                return bottlefull3+ bottlefull3+ bottlefull3+ bottlefull2+ bottlefull+ bottlefull+ bottlefull+ bottlefull+ bottlefull+ bottlefull;
            case 14:
                return bottlefull3+ bottlefull3+bottlefull3+ bottlefull+ bottlefull+ bottlefull+ bottlefull+ bottlefull+ bottlefull+ bottlefull;
            case 15:
                return bottlefull3+ bottlefull3+ bottlefull2+ bottlefull+ bottlefull +bottlefull+ bottlefull+ bottlefull+ bottlefull+bottlefull;
            case 16:
                return bottlefull3+ bottlefull3+bottlefull+ bottlefull+ bottlefull+bottlefull+ bottlefull +bottlefull+ bottlefull+ bottlefull;
            case 17:
                return bottlefull3+ bottlefull2+bottlefull+bottlefull+bottlefull+bottlefull+bottlefull+bottlefull+bottlefull+bottlefull;
            case 18:
                return bottlefull3+bottlefull+bottlefull+bottlefull+bottlefull+bottlefull+bottlefull+bottlefull+bottlefull+bottlefull;
            case 19:
               return bottlefull2+bottlefull+bottlefull+bottlefull+bottlefull+bottlefull+bottlefull+bottlefull+bottlefull+bottlefull;
            default:
                return bottlefull+bottlefull3+bottlefull3+bottlefull+bottlefull+bottlefull+bottlefull+bottlefull+bottlefull+bottlefull;
        }

    }
    @EventHandler
    public void playerDeath(PlayerDeathEvent event){
        setNowPlayerThrist(event.getPlayer(), 20);
    }

}
