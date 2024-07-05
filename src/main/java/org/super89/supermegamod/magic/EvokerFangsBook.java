package org.super89.supermegamod.magic;

import org.bukkit.Location;
import org.bukkit.Sound;
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
    PlayerDataController playerDataController = new PlayerDataController(Magic.getPlugin());
    @EventHandler


    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getItem() != null && event.getItem().getItemMeta().hasCustomModelData() && event.getItem().getItemMeta().getCustomModelData() == 10006 && event.getItem().getItemMeta().hasCustomModelData() && playerDataController.getNowPlayerMana(event.getPlayer()) >=10){

                Player player = event.getPlayer();

                double yaw = player.getLocation().getYaw();
                double pitch = player.getLocation().getPitch();
                double radius = 0.5;
                int numFangs = 20;
                if(event.getClickedBlock() != null) {

                    Location blockLocation = event.getClickedBlock().getLocation();
                    Vector direction = player.getLocation().getDirection().normalize();

                    for (int i = 0; i < numFangs; i++) {
                        double angle = i * 2 * Math.PI / numFangs;
                        double x = blockLocation.getX() + radius * Math.cos(angle) + direction.getX() * i;
                        double z = blockLocation.getZ() + radius * Math.sin(angle) + direction.getZ() * i;
                        Location spawnLocation = new Location(player.getWorld(), x, blockLocation.getY()+1, z, (float) yaw, (float) pitch);
                        player.getWorld().spawnEntity(spawnLocation, EntityType.EVOKER_FANGS);


                    }
                    player.playSound((Entity) player, Sound.ENTITY_EVOKER_CELEBRATE, 100 , 100);
                    playerDataController.setNowPlayerMana(player, playerDataController.getNowPlayerMana(player)-10);
                }
            }
        }
    }
}
