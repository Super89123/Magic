package org.super89.supermegamod.magic;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;

public class ReflectDamageEffect implements Listener {
    private int reflectDamageDuration;


    private HashMap<Player, Boolean> playerBooleanHashMap = new HashMap<Player, Boolean>();

    public void apply(Player player, double reflectDamagePercentage, int reflectDamageDuration) {
        final int[] cd = {reflectDamageDuration};

        playerBooleanHashMap.put(player, true);
        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                if (cd[0] <= 0) {
                    this.cancel(); // останавливаешь шедулер
                    // Отсчет закончился, удаляешь с hashmap
                    playerBooleanHashMap.remove(player);
                } else {
                    cd[0]--;

                }
            }
        }.runTaskTimer(Magic.getPlugin(), 0, 20L);

        player.sendMessage("Reflect damage effect activated. Duration: " + reflectDamageDuration + " seconds.");
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {

        if (event.getEntity() instanceof Player) {

            Player player = (Player) event.getEntity();
            LivingEntity attacker = (LivingEntity) event.getDamager();
            if(playerBooleanHashMap.get(player) != null){
            double damage = event.getDamage();
            event.setDamage(damage * 0.05);
            attacker.damage(damage*0.95);



        }
        }
    }
}