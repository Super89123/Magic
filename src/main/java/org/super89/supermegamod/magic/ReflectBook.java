package org.super89.supermegamod.magic;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ReflectBook implements Listener {
    private final ReflectDamageEffect reflectDamageEffect = new ReflectDamageEffect();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            ItemStack itemStack = event.getItem();
            if (itemStack != null && itemStack.getType() == Material.BOOK && itemStack.hasItemMeta() && itemStack.getItemMeta().hasCustomModelData() && itemStack.getItemMeta().getCustomModelData() == 1006) {
                reflectDamageEffect.apply(event.getPlayer(), 5, 5 * 60 * 20); // 5 minutes
            }
        }
    }
}