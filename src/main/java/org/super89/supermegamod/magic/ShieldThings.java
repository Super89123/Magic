package org.super89.supermegamod.magic;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class ShieldThings implements Listener {
    @EventHandler
    public void entitybyentity(EntityDamageByEntityEvent event){
        if(event.getEntity() instanceof Player){
            Player player =  (Player) event.getEntity();
            if(event.getDamage() > 5){
                if(player.isBlocking()){
                    player.damage(event.getDamage());
                }
            }
        }
    }
}
