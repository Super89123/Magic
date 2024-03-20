package org.super89.supermegamod.magic;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class FireBook implements Listener {
    public void createFireParticlesAroundPlayer(Player player) {
        Location loc = player.getLocation();
        World world = loc.getWorld();

        for (int x = -3; x <= 3; x++) {
            for (int y = -3; y <= 3; y++) {
                for (int z = -3; z <= 3; z++) {
                    if (Math.sqrt(x * x + y * y + z * z) <= 3) {
                        Location particleLoc = loc.add(x, y, z);
                        world.playEffect(particleLoc, Effect.MOBSPAWNER_FLAMES, 20);
                    }
                }
            }
        }
    }
    @EventHandler
    public void onplayer(PlayerInteractEvent e){
        Player player = e.getPlayer();
        ItemStack itemStack = e.getItem();
        if(e.getItem() != null && e.getAction() == Action.RIGHT_CLICK_AIR && itemStack.getItemMeta().hasCustomModelData() && itemStack.getItemMeta().getCustomModelData() == 1011){
            Location playerLocation = player.getLocation();
            for (Entity entity : player.getNearbyEntities(5, 5, 5)) {
                if (entity instanceof LivingEntity) {
                    createFireParticlesAroundPlayer(player);
                    entity.setFireTicks(100);
                    player.playSound(player, Sound.ENTITY_BLAZE_SHOOT, 100, 100);
                }
            }

        }

    }
}
