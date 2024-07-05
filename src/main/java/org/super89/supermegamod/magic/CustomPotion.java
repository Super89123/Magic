package org.super89.supermegamod.magic;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CustomPotion implements Listener {
    PlayerDataController playerDataController = new PlayerDataController(Magic.getPlugin());
    @EventHandler
    public void brewevent(BrewEvent event1){
        ItemStack item = event1.getContents().getIngredient();
        if(item != null && item.getType() == Material.RABBIT_FOOT && item.hasItemMeta() && item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == 10000){
            BrewerInventory inventory = event1.getContents();
            ItemStack potion = new ItemStack(Material.POTION);
            ItemMeta meta = potion.getItemMeta();
            meta.setCustomModelData(2026);
            meta.setDisplayName("§bЗелье Маны");
            potion.setItemMeta(meta);
            if(!inventory.getItem(0).getType().equals(Material.AIR)) {
                inventory.setItem(0, potion);
            }
            if(!inventory.getItem(1).getType().equals(Material.AIR)) {
                inventory.setItem(1, potion);
            }
            if(!inventory.getItem(2).getType().equals(Material.AIR)) {
                inventory.setItem(2, potion);
            }
            item.setAmount(item.getAmount()-1);
            inventory.setItem(3, item);
            event1.setCancelled(true);



        }
    }

    @EventHandler
    public void onPlayerDrink(PlayerItemConsumeEvent event){
        ItemStack item = event.getItem();
        if (item.getType() == Material.POTION && item.hasItemMeta() && item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == 2026){
            Player player = event.getPlayer();
            int addmana = playerDataController.getNowPlayerMana(player)+2;
            playerDataController.setNowPlayerMana(player, Math.min(addmana, playerDataController.getMaxPlayerMana(player)));



        }
    }
}
