package org.super89.supermegamod.magic;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class ReviewBook implements Listener {
    PlayerDataController playerDataController = new PlayerDataController(Magic.getPlugin());
    @EventHandler
    public void onPlayerUSE(PlayerInteractEvent event){
        Player player = event.getPlayer();
        Location spawnpoint = player.getBedSpawnLocation();
        Location worldSpawn = player.getWorld().getSpawnLocation();
        if (event.getItem() != null && event.getAction().isRightClick() && event.getItem().getItemMeta().hasCustomModelData() && event.getItem().getItemMeta().getCustomModelData() == 10004 && playerDataController.getNowPlayerMana(player) == 100){
            if(player.getCooldown(Material.BOOK) == 0) {
                if (spawnpoint != null) {
                    player.teleport(spawnpoint);
                } else {
                    player.teleport(worldSpawn);
                }
                playerDataController.setNowPlayerMana(player, 0);
                player.playSound((Entity) player, Sound.BLOCK_PORTAL_TRAVEL, 100, 100);
                event.getItem().setAmount(event.getItem().getAmount() - 1);
                player.setCooldown(Material.BOOK, 40);
            }

        }
    }
}
