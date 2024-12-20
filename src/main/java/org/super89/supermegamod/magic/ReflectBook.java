package org.super89.supermegamod.magic;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ReflectBook implements Listener {
    private final Magic plugin;

    public Map<Player, Long> activePlayers = new HashMap<>();


    public ReflectBook(Magic plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        PlayerDataController playerDataController = new PlayerDataController(plugin);
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (item != null && item.getType().equals(Material.BOOK) && item.hasItemMeta() && item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == 1006 && playerDataController.getNowPlayerMana(player) >=50){
                if(player.getCooldown(Material.BOOK) == 0) {

                    long endTime = System.currentTimeMillis() + 5 * 60 * 1000;
                    activePlayers.put(player, endTime);
                    player.sendMessage(ChatColor.GREEN + "Шипы активированы!");
                    player.playSound(player, Sound.ENTITY_FIREWORK_ROCKET_SHOOT, 100, 100);
                    playerDataController.setNowPlayerMana(player, playerDataController.getNowPlayerMana(player) - 50);
                    player.setCooldown(Material.BOOK, 40);
                }

            }
        }
    }
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof LivingEntity && event.getDamager() instanceof LivingEntity) {
            LivingEntity defender = (LivingEntity) event.getEntity();
            LivingEntity attacker = (LivingEntity) event.getDamager();

            if (activePlayers.containsKey(defender) && System.currentTimeMillis() < activePlayers.get(defender)) {

                double damage = event.getDamage();
                event.setDamage(damage * 0.05);


                attacker.damage(damage * 0.95);
            }
        }
    }
}