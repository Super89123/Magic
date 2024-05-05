package org.super89.supermegamod.magic;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class MineBook implements Listener {
    PlayerDataController playerDataController = new PlayerDataController(Magic.getPlugin());
    @EventHandler
    public void PlayerInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        player.getInventory().getItemInMainHand();
        if(player.getInventory().getItemInMainHand().hasItemMeta() && player.getInventory().getItemInMainHand().getItemMeta().hasCustomModelData() && player.getInventory().getItemInMainHand().getType() == Material.BOOK && event.getAction().name().contains("RIGHT_CLICK") && player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 1231 && playerDataController.getNowPlayerMana(player) >= 10){
            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, 300 *20, 1, false, false, false));
            player.playSound(player, Sound.ENTITY_ENDERMAN_HURT, 100, 100);

        }
    }
}
