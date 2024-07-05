package org.super89.supermegamod.magic;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ShieldThings implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            ItemStack shield = player.getInventory().getItemInOffHand();


            // Проверяем, экипирован ли щит
            if (shield.getType() == Material.SHIELD && shield.hasItemMeta() && shield.getItemMeta().hasCustomModelData()) {
                // Получаем тип щита по CustomModelData
                ShieldType shieldType = ShieldType.getShieldType(shield.getItemMeta().getCustomModelData());

                // Если тип щита найден, уменьшаем урон
                if (shieldType != null) {
                    if(event.getDamage() > shieldType.getDamageReduction()){
                        player.damage(event.getDamage());
                        event.setCancelled(true);
                    }


                }
                else{
                    if(event.getDamage() > 5){
                        player.damage(event.getDamage());
                        event.setCancelled(true);
                    }
                }
            } else if (shield.getType().equals(Material.SHIELD) && !shield.hasItemMeta() && !shield.getItemMeta().hasCustomModelData()) {
                if(event.getDamage() > 5){
                    player.damage(event.getDamage());
                    event.setCancelled(true);
                }



            }
        }
    }

}
