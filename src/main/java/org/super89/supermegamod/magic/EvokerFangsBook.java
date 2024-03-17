package org.super89.supermegamod.magic;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;
import org.super89.supermegamod.magic.Utils.ItemUtils;

public class EvokerFangsBook implements Listener {
    @EventHandler


    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getItem() != null && event.getItem().getItemMeta().hasCustomModelData() && event.getItem().getItemMeta().getCustomModelData() == 1010101 && event.getItem().getItemMeta().hasCustomModelData()) {
                Player player = event.getPlayer();

                double yaw = player.getLocation().getYaw();
                double pitch = player.getLocation().getPitch();
                double radius = 0.5; // Adjust the radius as needed
                int numFangs = 20; // Adjust the number of fangs as needed

                Location playerLocation = player.getLocation();
                Vector direction = playerLocation.getDirection().normalize();

                for (int i = 0; i < numFangs; i++) {
                    double angle = i * 2 * Math.PI / numFangs;
                    double x = playerLocation.getX()+0.25 + radius * Math.cos(angle) + direction.getX() * i;
                    double z = playerLocation.getZ()+0.25 + radius * Math.sin(angle) + direction.getZ() * i;
                    Location spawnLocation = new Location(player.getWorld(), x, playerLocation.getY(), z, (float) yaw, (float) pitch);
                    player.getWorld().spawnEntity(spawnLocation, EntityType.EVOKER_FANGS);
                    ItemUtils.setDelay(20);
                }
            }
        }
    }
}
