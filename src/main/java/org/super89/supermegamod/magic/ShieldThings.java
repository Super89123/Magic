package org.super89.supermegamod.magic;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ShieldThings implements Listener {
    public ItemStack createShield(ShieldType type) {
        ItemStack shield = new ItemStack(Material.SHIELD);
        ItemMeta meta = shield.getItemMeta();
        meta.setCustomModelData(type.getModelData());
        shield.setItemMeta(meta);
        return shield;
    }
    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            ItemStack shield = player.getInventory().getItemInOffHand();

            // Проверяем, экипирован ли щит
            if (shield.getType() == Material.SHIELD) {
                // Получаем тип щита по CustomModelData
                ShieldType shieldType = ShieldType.getShieldType(shield.getItemMeta().getCustomModelData());

                // Если тип щита найден, уменьшаем урон
                if (shieldType != null) {
                    double damage = event.getDamage();
                    double reducedDamage = Math.max(0, damage - shieldType.getDamageReduction());
                    event.setDamage(reducedDamage);
                }
                else{
                    if(event.getDamage() > 5){
                        event.setDamage(event.getDamage());
                    }
                }
            }
        }
    }

}
