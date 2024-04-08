package org.super89.supermegamod.magic;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.NoteBlock;
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

import java.util.HashMap;

public class PufferManager implements Listener {

    private final HashMap<NoteBlock, Location> blockLocations = new HashMap<>();

    public void registerBlock(NoteBlock block, Location location) {
        blockLocations.put(block, location);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block clickedBlock = event.getClickedBlock();
            if (blockLocations.containsKey(clickedBlock)) {
                openMenu(event.getPlayer(), clickedBlock);
            }
        }
    }

    private void openMenu(Player player, Block block) {
        Inventory inventory = Bukkit.createInventory(null, 9, "Меню блока");

        // Создайте предметы для меню и добавьте их в инвентарь
        ItemStack infoItem = new ItemStack(Material.PAPER);
        ItemMeta meta = infoItem.getItemMeta();
        meta.setDisplayName("Информация о блоке");
        infoItem.setItemMeta(meta);
        inventory.setItem(0, infoItem);

        // Добавьте другие предметы...

        player.openInventory(inventory);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals("Меню блока")) {
            event.setCancelled(true);

            // Обработайте клики на предметы в меню
            Player player = (Player) event.getWhoClicked();
            ItemStack clickedItem = event.getCurrentItem();
            if (clickedItem != null && clickedItem.getType() == Material.PAPER) {
                player.sendMessage("Вы кликнули на информацию о блоке!");
                // ...
            }
        }
    }
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        if(event.getBlock() instanceof NoteBlock){
            NoteBlock noteBlock = (NoteBlock) event.getBlock();
            registerBlock(noteBlock, event.getBlock().getLocation());

        }
    }
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        if(null != blockLocations.get(event.getBlock().getLocation())){
            blockLocations.remove(event.getBlock().getLocation());
        }
    }
}