package org.super89.supermegamod.magic;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class ReflectDamageEffect implements Listener {
    TimerTask1 timerTask1 = new TimerTask1();
    private double reflectDamagePercentage = 0.95;
    private int reflectDamageDuration = 5 * 60 * 20; // 5 minutes
    int a = 0;
    private int reflectDamageTicksLeft;

    public void apply(Player player, double reflectDamagePercentage, int reflectDamageDuration) {
        this.reflectDamagePercentage = reflectDamagePercentage;
        this.reflectDamageDuration = reflectDamageDuration;
        this.reflectDamageTicksLeft = reflectDamageDuration;
        player.sendMessage("Reflect damage effect activated. Duration: " + reflectDamageDuration / 20 + " seconds.");
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        timerTask1.timer(30000);
        if (event.getEntity() instanceof Player && reflectDamageTicksLeft > 0 && timerTask1.getFlag()) {
            Player player = (Player) event.getEntity();
            double damage = event.getDamage();
            event.setDamage(damage * reflectDamagePercentage);
            player.setLastDamage(damage * (1 - reflectDamagePercentage));



        }
    }
}