package org.super89.supermegamod.magic;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryWithCoolThings implements Listener {
    public void OpenInventory(Player player){

        if(player.hasPermission("minecraft.*")) {
            Inventory inventory = Bukkit.createInventory(null, 54, "Все книги");
            setItem(Material.BOOK, 1000, inventory, 0, "Книга Стана");
            setItem(Material.BOOK, 1002, inventory, 1, "Книга телепорта");
            setItem(Material.BOOK, 1230, inventory, 2, "Книга левитации");
            setItem(Material.BOOK, 1010101, inventory, 3, "Книга Вызывателя");
            setItem(Material.BOOK, 1231, inventory, 4, "Книга шахтера");
            setItem(Material.BOOK, 1001, inventory, 5, "Книга Взрыва");
            setItem(Material.IRON_SWORD, 1488, inventory, 6, "Ненасытный меч");
            setItem(Material.BOOK, 1005, inventory, 7, "Книга двужизия");
            setItem(Material.BOOK, 1006, inventory, 8, "Книга шипов");
            setItem(Material.BOOK, 1007, inventory, 9, "Книга возвращения");
            setItem(Material.BOOK, 1010, inventory, 10, "Книга Защиты");
            setItem(Material.BOOK, 1011, inventory, 11, "Книга Огня");
            setItem(Material.BOOK, 1012, inventory, 12, "Книга Ветра");
            setItem(Material.CROSSBOW, 1449, inventory, 13, "Арболет ХАОСА");

            player.openInventory(inventory);

        }
    }
    public void setItem(Material material, int custommodeldata, Inventory inv, int slot, String s){
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(custommodeldata);
        meta.setDisplayName("§f" + s);
        item.setItemMeta(meta);
        inv.setItem(slot, item);


    }
    @EventHandler
    public void PlayerInteractE(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(event.getClickedBlock() != null && event.getAction().name().contains("RIGHT_CLICK") && event.getClickedBlock().getType() == Material.FLETCHING_TABLE && event.getClickedBlock() != null){
            OpenInventory(player);
        }
    }

}