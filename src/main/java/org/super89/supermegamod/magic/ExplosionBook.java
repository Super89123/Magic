package org.super89.supermegamod.magic;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.Objects;

public class ExplosionBook implements Listener {
    private Magic plugin;


    public ExplosionBook(Magic plugin) {
        this.plugin = plugin;
    }
    Mana mana = new Mana(Magic.getPlugin());


    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        // Проверяем, является ли предмет книгой
        if (event.getAction().name().contains("RIGHT_CLICK") && item.getType() == Material.BOOK) {
            if(item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == 1001 && mana.getNowPlayerMana(player) >= 25 && Objects.requireNonNull(event.getClickedBlock()).getType() != Material.AIR){
                mana.setNowPlayerMana(player, mana.getNowPlayerMana(player)-25);




                // Проверяем, является ли название книги "Explosion Book"
                // Создаем взрыв в направлении курсора игрока
                Location location = player.getLocation();
                Vector direction = location.getDirection();
                location.add(direction);
                location.getWorld().createExplosion(location, 4.0f);
                ItemMeta meta = item.getItemMeta();

            }
        }
    }
}
