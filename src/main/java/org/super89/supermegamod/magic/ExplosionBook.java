package org.super89.supermegamod.magic;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Objects;

public class ExplosionBook implements Listener {
    private Magic plugin;


    public ExplosionBook(Magic plugin) {
        this.plugin = plugin;
    }
    PlayerDataController playerDataController = new PlayerDataController(Magic.getPlugin());


    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (event.getAction().name().contains("RIGHT_CLICK") && item.getType() == Material.BOOK) {
            if(item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == 10009 && playerDataController.getNowPlayerMana(player) >= 25 && Objects.requireNonNull(event.getClickedBlock()).getType() != Material.AIR){
                playerDataController.setNowPlayerMana(player, playerDataController.getNowPlayerMana(player)-25);





                Location location = player.getLocation();
                Vector direction = location.getDirection();
                location.add(direction);
                location.getWorld().createExplosion(location, 4.0f);
                player.playSound(player, Sound.ENTITY_CREEPER_PRIMED, 100, 100);


            }
        }
    }
}
