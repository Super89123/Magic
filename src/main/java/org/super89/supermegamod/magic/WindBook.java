package org.super89.supermegamod.magic;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class WindBook implements Listener {
    PlayerDataController playerDataController = new PlayerDataController(Magic.getPlugin());
    @EventHandler
    public void entity(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Player){
            Player player = (Player) event.getDamager();
            ItemStack item = player.getInventory().getItemInMainHand();
            if (item.getType() == Material.BOOK && item.hasItemMeta() && item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == 10001){
                if(playerDataController.getNowPlayerMana(player) >= 25) {
                    Entity entity = event.getEntity();
                    Location playerLocation = player.getLocation();
                    Location entityLocation = entity.getLocation();
                    Vector direction = playerLocation.toVector().subtract(entityLocation.toVector()).normalize();
                    Vector knockbackVelocity = direction.multiply(-30);
                    player.playSound(playerLocation, Sound.ENTITY_BREEZE_SHOOT, 100, 100);
                    entity.setVelocity(knockbackVelocity);
                }
                else {
                    event.setCancelled(true);
                    player.sendMessage(ChatColor.RED + "Недостаточно маны");
                }

            }
        }
    }
}
