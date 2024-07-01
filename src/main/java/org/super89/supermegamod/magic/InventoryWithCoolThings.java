package org.super89.supermegamod.magic;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;

import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.block.structure.Mirror;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


import java.util.ArrayList;

public class InventoryWithCoolThings implements Listener {





    public void OpenInventory(Player player){
        ItemStack puffer = new ItemStack(Material.NOTE_BLOCK);


        if(player.hasPermission("minecraft.*")) {
            Inventory inventory = Bukkit.createInventory(null, 54, "Debug Table");
            setItem(Material.BOOK, 10005, inventory, 0, "Книга Стана");
            setItem(Material.BOOK, 10000, inventory, 1, "Книга телепорта");
            setItem(Material.BOOK, 10007, inventory, 2, "Книга левитации");
            setItem(Material.BOOK, 10006, inventory, 3, "Книга Вызывателя");
            setItem(Material.BOOK, 10002, inventory, 4, "Книга шахтера");
            setItem(Material.BOOK, 10009, inventory, 5, "Книга Взрыва");
            setItem(Material.BOOK, 10003, inventory, 7, "Книга двужизия");
            setItem(Material.BOOK, 1006, inventory, 8, "Книга шипов");
            setItem(Material.BOOK, 10004, inventory, 9, "Книга возвращения");
            setItem(Material.BOOK, 1010, inventory, 10, "Книга Защиты");
            setItem(Material.BOOK, 10008, inventory, 11, "Книга Огня");
            setItem(Material.BOOK, 10001, inventory, 12, "Книга Ветра");
            setItem(Material.CROSSBOW, 1449, inventory, 13, "Автоматон");
            setItem(Material.SHIELD, 1020, inventory, 14, "Незеритовый щит");
            setItem(Material.SHIELD, 1021, inventory, 15, "Железный щит");
            setItem(Material.POTION, 2026, inventory, 16, "§bЗелье Маны");
            setItem(Material.RABBIT_FOOT, 10000, inventory, 17, "Лазуритовая пыль");
            setItem(Material.PAPER, 10261, inventory, 18, "Звездная пыль");
            setItem(Material.PAPER, 10260, inventory, 19, "Аметистовая пыль");
            setItem(Material.POTION, 2030, inventory, 20, "Очищенная вода");
            setItem(Material.POTION, 2031, inventory, 21, "Очищенная вода+");
            setItem(Material.PAPER, 10263, inventory, 22, "Призмариновый порошок");
            setItem(Material.GLASS_BOTTLE, 10000, inventory, 6, "Пустая кружка");
            setItem(Material.POTION, 10000, inventory, 23, "Кружка с водой");
            setItem(Material.POTION, 2035, inventory, 24, "Кружка с водой+");


            player.openInventory(inventory);

        }
    }
    public void setItem(Material material, int custommodeldata, Inventory inv, int slot, String s){
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(custommodeldata);
        meta.setDisplayName("§f" + s);

        ArrayList<String> lore = new ArrayList<String>();
        lore.add("§fCustom Model Data: "+ custommodeldata);
        meta.setLore(lore);
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