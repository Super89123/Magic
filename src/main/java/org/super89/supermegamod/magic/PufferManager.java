package org.super89.supermegamod.magic;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.super89.supermegamod.magic.Utils.ItemUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PufferManager implements Listener {

    public final Map<Location, Inventory> pufferInventories = new HashMap<>();
    private FileConfiguration config;
    private File configFile;
    @EventHandler
    public void onNoteBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlockPlaced();
        if (block.getType() == Material.NOTE_BLOCK) {
            // Создаём инвентарь для нотного блока
            Inventory inventory = Bukkit.createInventory(null, 45, "§4Очиститель " + block.getLocation().getBlockX() + " " + block.getLocation().getBlockY() + " " + block.getLocation().getBlockZ());

            pufferInventories.put(block.getLocation(), inventory);
        }
    }
    public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }



    public Inventory getPufferInventory(Block noteBlock) {
        return pufferInventories.get(noteBlock.getLocation());
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.NOTE_BLOCK) {
            Block noteBlock =  event.getClickedBlock();
            Inventory inventory = getPufferInventory(noteBlock);
            for(int i = 0; i < 45; i++){

                if(i != 10 && i != 12 && i != 21 && i != 30){
                    inventory.setItem(i, ItemUtils.create(Material.PURPLE_STAINED_GLASS_PANE, " "));

                }
            }

            inventory.setItem(12, ItemUtils.create(Material.RED_WOOL, " "));
            inventory.setItem(21, ItemUtils.create(Material.RED_WOOL, " "));
            inventory.setItem(30, ItemUtils.create(Material.RED_WOOL, " "));

            inventory.setItem(10, ItemUtils.create(Material.LIGHT_GRAY_STAINED_GLASS_PANE, " "));

            inventory.setItem(11, ItemUtils.create(Material.PURPLE_STAINED_GLASS_PANE, " "));
            inventory.setItem(9, ItemUtils.create(Material.PURPLE_STAINED_GLASS_PANE, " "));
            inventory.setItem(18, ItemUtils.create(Material.PURPLE_STAINED_GLASS_PANE, " "));
            inventory.setItem(19, ItemUtils.create(Material.PURPLE_STAINED_GLASS_PANE, " "));
            inventory.setItem(20, ItemUtils.create(Material.PURPLE_STAINED_GLASS_PANE, " "));
            event.getPlayer().openInventory(inventory);
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void invclick(InventoryClickEvent event){

            Inventory inventory = event.getClickedInventory();
            if (pufferInventories.containsValue(event.getClickedInventory())) {
                Location location = getKeyByValue(pufferInventories, inventory);
                if (Objects.requireNonNull(event.getCurrentItem()).getType().equals(Material.RED_WOOL) || event.getCurrentItem().getType().equals(Material.PURPLE_STAINED_GLASS_PANE) || event.getCurrentItem().getType().equals(Material.GREEN_WOOL)) {
                    event.setCancelled(true);




                }
                int slot = 10;
                if(event.getCursor() != null){
                if (event.getSlot() == slot && Objects.requireNonNull(event.getCursor()).getType().equals(Material.WATER_BUCKET)) {
                    assert inventory != null;
                    if (Objects.requireNonNull(inventory.getItem(30)).getType().equals(Material.RED_WOOL)) {
                        inventory.setItem(10, new ItemStack(Material.BUCKET));
                        inventory.setItem(30, ItemUtils.create(Material.LIME_WOOL, " "));
                        event.setCursor(new ItemStack(Material.AIR));
                        event.setCancelled(true);
                        return;
                    }
                    if (Objects.requireNonNull(inventory.getItem(21)).getType().equals(Material.RED_WOOL) && Objects.requireNonNull(inventory.getItem(30)).getType().equals(Material.LIME_WOOL)) {
                        inventory.setItem(10, new ItemStack(Material.BUCKET));
                        inventory.setItem(21, ItemUtils.create(Material.LIME_WOOL, " "));
                        event.setCursor(new ItemStack(Material.AIR));
                        event.setCancelled(true);
                        return;
                    }
                    if (Objects.requireNonNull(inventory.getItem(12)).getType().equals(Material.RED_WOOL) && Objects.requireNonNull(inventory.getItem(30)).getType().equals(Material.LIME_WOOL) && Objects.requireNonNull(inventory.getItem(21)).getType().equals(Material.LIME_WOOL)) {
                        inventory.setItem(10, new ItemStack(Material.BUCKET));
                        inventory.setItem(12, ItemUtils.create(Material.LIME_WOOL, " "));
                        event.setCursor(new ItemStack(Material.AIR));
                        event.setCancelled(true);
                        return;

                    }
                    pufferInventories.replace(location, inventory);

                }
                }

            }

    }

}
