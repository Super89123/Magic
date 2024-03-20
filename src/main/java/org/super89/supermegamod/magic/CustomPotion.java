package org.super89.supermegamod.magic;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;

public class CustomPotion implements Listener {
    @EventHandler
    public void brewevent(BrewEvent event1){
        ItemStack item = event1.getContents().getIngredient();
        if(item != null && item.getType() == Material.RABBIT_FOOT && item.hasItemMeta() && item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == 2025){
            BrewerInventory inventory = event1.getContents();
            ItemStack potion = new ItemStack(Material.POTION);
            ItemMeta meta = potion.getItemMeta();
            meta.setCustomModelData(2026);
            meta.setDisplayName("§bЗелье Маны");
            potion.setItemMeta(meta);
            inventory.setItem(0, potion);
            inventory.setItem(1, potion);
            inventory.setItem(2, potion);
            inventory.setItem(3, new ItemStack(Material.AIR));
            event1.setCancelled(true);



        }
    }
    @EventHandler
    public  void  Playerr(InventoryInteractEvent event){
        if(event.getInventory() instanceof BrewerInventory){
            BrewerInventory inventory = (BrewerInventory) event.getInventory();
        }
    }
}
