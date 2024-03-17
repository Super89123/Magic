package org.super89.supermegamod.magic;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ResistanceBook implements Listener {
    private void addResistanceEffect(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 3, false,false,false));
    }

    private void removeResistanceEffect(Player player) {
        player.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
    }
    @EventHandler
    public void onEntityEquip(PlayerSwapHandItemsEvent event) {

            Player player = event.getPlayer();
            ItemStack item = event.getOffHandItem();


            if (item.getType() == Material.BOOK && item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == 1010) { // Check if the item is a diamond pickaxe
                addResistanceEffect(player);

            } else {
                removeResistanceEffect(player);

        }
    }
}
