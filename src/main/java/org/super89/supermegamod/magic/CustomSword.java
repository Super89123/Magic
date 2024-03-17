package org.super89.supermegamod.magic;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class CustomSword implements Listener {

    private Magic plugin;

    public CustomSword(Magic plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        // Проверяем, что атакующий - игрок
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();
            ItemStack item = player.getInventory().getItemInMainHand();

            // Проверяем, что предмет в руке игрока - меч
            if (item.getType() == Material.IRON_SWORD && player.getInventory().getItemInMainHand().getItemMeta().hasCustomModelData() && player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 1488) {
                // Наносим урон врагу и отнимаем голод у игрока
                event.setDamage(6); // 3 сердца = 6 единиц урона
                player.setFoodLevel(player.getFoodLevel() - 6);
            }
        }
    }
}