package org.super89.supermegamod.magic;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.Objects;

public class Halloween implements Listener{
    private void playScaryMelody(Player player) {
        // Задержка между звуками в тиках (20 тиков = 1 секунда)
        int delay = 5;

        // Массив звуков с разной тональностью
        Sound[] sounds = {
                Sound.BLOCK_NOTE_BLOCK_BASS, // 0
                Sound.BLOCK_NOTE_BLOCK_BASS, // 1
                Sound.BLOCK_NOTE_BLOCK_BASS, // 2
                Sound.BLOCK_NOTE_BLOCK_SNARE, // 3
                Sound.BLOCK_NOTE_BLOCK_HAT, // 4
                Sound.BLOCK_NOTE_BLOCK_HARP, // 5
                Sound.BLOCK_NOTE_BLOCK_HARP, // 6
                Sound.BLOCK_NOTE_BLOCK_HARP, // 7
                Sound.ENTITY_ENDERMAN_TELEPORT, // 8
                Sound.ENTITY_ZOMBIE_AMBIENT, // 9
                Sound.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, // 10
                Sound.ENTITY_WITHER_SKELETON_AMBIENT, // 11
                Sound.ENTITY_WITHER_SKELETON_HURT, // 12
                Sound.ENTITY_WITHER_AMBIENT, // 13
                Sound.ENTITY_WITCH_AMBIENT, // 14
                Sound.ENTITY_GHAST_SCREAM, // 15
                Sound.ENTITY_ENDER_DRAGON_GROWL, // 16
                Sound.ENTITY_BLAZE_AMBIENT, // 17
                Sound.BLOCK_BELL_USE, // 18
                Sound.ENTITY_LIGHTNING_BOLT_THUNDER // 19
        };

        // Массив тональностей для каждого звука
        float[] pitches = {
                0.5f, 0.6f, 0.7f, 1.0f, 1.5f, 0.8f, 0.9f, 1.1f, 0.7f, 0.9f, 1.2f, 0.6f, 0.8f, 1.0f,
                1.2f, 0.5f, 0.7f, 0.9f, 1.1f, 1.3f
        };

        // Проигрывание мелодии с задержкой
        for (int i = 0; i < sounds.length; i++) {
            final int index = i;
            Bukkit.getScheduler().scheduleSyncDelayedTask(Magic.getPlugin(), () -> {
                if(!Objects.requireNonNull(player.getInventory().getChestplate()).getType().equals(Material.AIR)) {
                    player.playSound(player.getLocation(), sounds[index], 1.0f, pitches[index]);
                }
            }, delay * i);
        }
    }

    @EventHandler
    public void onJoinEvent(PlayerJoinEvent event){
        Player player = event.getPlayer();
        playScaryMelody(player);
    }
    @EventHandler
    public void ontp(PlayerTeleportEvent event){


    }


}
