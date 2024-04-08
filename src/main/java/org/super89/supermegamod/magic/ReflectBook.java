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
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;

public class ReflectBook implements Listener {

    public Map<Player, Long> activePlayers = new HashMap<>();
    public ItemStack createItem() {
        ItemStack item = new ItemStack(Material.BOOK); // Replace with desired item
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(1006);
        meta.setDisplayName(ChatColor.AQUA + "Damage Reflector");
        item.setItemMeta(meta);
        return item;
    }


    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (item != null && item.getType().equals(Material.BOOK) && item.hasItemMeta() && item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == 1006) {
                // Activate the effect for 5 minutes
                long endTime = System.currentTimeMillis() + 5 * 60 * 1000;
                activePlayers.put(player, endTime);
                player.sendMessage(ChatColor.GREEN + "Damage reflection activated for 5 minutes!");
                player.playSound(player, Sound.ENTITY_FIREWORK_ROCKET_SHOOT, 100, 100);
            }
        }
    }
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof LivingEntity && event.getDamager() instanceof LivingEntity) {
            LivingEntity defender = (LivingEntity) event.getEntity();
            LivingEntity attacker = (LivingEntity) event.getDamager();

            if (activePlayers.containsKey(defender) && System.currentTimeMillis() < activePlayers.get(defender)) {
                // Reduce damage taken by defender
                double damage = event.getDamage();
                event.setDamage(damage * 0.05); // Defender takes only 5% damage

                // Reflect damage back to attacker
                attacker.damage(damage * 0.95); // Attacker takes 95% damage
            }
        }
    }
}