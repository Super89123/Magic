package org.super89.supermegamod.magic;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class ReviewBook implements Listener {
    @EventHandler
    public void onPlayerUSE(PlayerInteractEvent event){
        Player player = event.getPlayer();
        Location spawnpoint = player.getBedSpawnLocation();
        Location worldSpawn = player.getWorld().getSpawnLocation();
        if (event.getItem() != null && event.getAction().isRightClick() && event.getItem().getItemMeta().hasCustomModelData() && event.getItem().getItemMeta().getCustomModelData() == 1007) {
            if(spawnpoint != null) {
                player.teleport(spawnpoint);
            }
            else {
                player.teleport(worldSpawn);
            }
        }
    }
}
