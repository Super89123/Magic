package org.super89.supermegamod.magic;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class HealthBook implements Listener {
    @EventHandler
    public void playerin(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(event.getItem() != null && event.getItem().getItemMeta().hasCustomModelData() && event.getItem().getItemMeta().getCustomModelData() == 1005 && event.getAction() == Action.RIGHT_CLICK_AIR){
            player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "give "+player.getName()+" book{CustomModelData:1004,AttributeModifiers:[{AttributeName:\"generic.max_health\",Amount:20.0,Slot:offhand,Name:\"generic.max_health\",UUID:[I;-124215,19378,122247,-38756]}]} 1");
        }
    }
}
