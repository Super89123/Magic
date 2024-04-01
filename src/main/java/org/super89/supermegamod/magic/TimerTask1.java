package org.super89.supermegamod.magic;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Timer;
import java.util.TimerTask;

public class TimerTask1 {
    boolean flag;
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            flag = false;
        }
    };
    Timer timer = new Timer();
    public boolean getFlag(){
        return flag;
    }
    public void timer(int delay){
        flag = true;
        timer.schedule(task, delay);
    }
}