package org.super89.supermegamod.magic;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

public class WaitAsync {
    private final BukkitScheduler scheduler;

    public WaitAsync(BukkitScheduler scheduler) {
        this.scheduler = scheduler;
    }

    public void waitAsync(int time, ItemStack item, Inventory inventory) {
        scheduler.runTaskLaterAsynchronously(Magic.getPlugin(), () -> {
            // Do nothing, just wait
        }, time * 20L); // Convert seconds to ticks (20 ticks per second)
    }
}