package org.super89.supermegamod.magic;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class LevitationBook implements Listener {
    PlayerDataController playerDataController = new PlayerDataController(Magic.getPlugin());

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(player.getInventory().getItemInMainHand().getType() == Material.BOOK && event.getAction().name().contains("RIGHT_CLICK") && player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 10007 && player.getInventory().getItemInMainHand().getItemMeta().hasCustomModelData() && playerDataController.getNowPlayerMana(player)>=2){
            player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 10 ,1, false, false,false));
            player.playSound(player, Sound.ENTITY_SHULKER_BULLET_HIT, 100 , 100);
            playerDataController.setNowPlayerMana(player, playerDataController.getNowPlayerMana(player)-2);

        }
    }
}

