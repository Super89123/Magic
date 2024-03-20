package org.super89.supermegamod.magic;

import jdk.dynalink.linker.LinkerServices;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class WindBook implements Listener {
    @EventHandler
    public void entity(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Player){
            Player player = (Player) event.getDamager();
            ItemStack item = player.getInventory().getItemInMainHand();
            if (item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == 1012){
                Entity entity = event.getEntity();
                Location playerLocation = player.getLocation();
                Location entityLocation = entity.getLocation();
                Vector direction = playerLocation.toVector().subtract(entityLocation.toVector()).normalize();
                Vector knockbackVelocity = direction.multiply(30);
                entity.setVelocity(knockbackVelocity);


            }
        }
    }
}
