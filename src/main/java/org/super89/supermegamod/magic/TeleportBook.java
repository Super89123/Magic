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

public class TeleportBook implements Listener {

    private Magic plugin;

    public TeleportBook(Magic plugin) {
        this.plugin = plugin;
    }
    PlayerDataController playerDataController = new PlayerDataController(Magic.getPlugin());

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item.getType() == Material.BOOK && event.getAction().name().contains("RIGHT_CLICK") && player.getInventory().getItemInMainHand().getItemMeta().hasCustomModelData() && player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 10000) {


            if(item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == 10000 && playerDataController.getNowPlayerMana(player)>= 20 && event.getClickedBlock().getType().equals(Material.AIR) ) {
                if(player.getCooldown(Material.BOOK) == 0) {

                    playerDataController.setNowPlayerMana(player, playerDataController.getNowPlayerMana(player) - 20);
                    Location location = player.getLocation();
                    Vector direction = location.getDirection().normalize().multiply(5);
                    Location teleportLocation = location.add(direction);
                    player.teleport(teleportLocation);
                    player.playSound(player, Sound.ENTITY_ENDERMAN_TELEPORT, 100, 100);
                    player.setCooldown(Material.BOOK, 10);
                }

            }
        }
    }
}
