package org.super89.supermegamod.magic;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class TimerTask extends BukkitRunnable {
    private int duration;

    public TimerTask(int duration) {
        this.duration = duration;
    }

    @Override
    public void run() {
        // Do something when the timer runs out
        System.out.println("Timer ran out!");

        // Cancel the task so it doesn't repeat
        this.cancel();
    }

    public void start() {
        // Convert the duration from seconds to ticks (20 ticks per second)
        int ticks = this.duration * 20;

        // Run the timer task once per tick
        this.runTaskTimer(Magic.getPlugin(), 0, 1);

        // After the timer has run for the specified duration, cancel the task
        new BukkitRunnable() {
            @Override
            public void run() {
                this.cancel();
                TimerTask.this.cancel();
            }
        }.runTaskLater(Magic.getPlugin(), ticks);
    }
}