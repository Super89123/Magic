package org.super89.supermegamod.magic;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Halloween implements Listener{
    public ArrayList<Zombie> zombieList = new ArrayList<>();
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
        Player player = event.getPlayer();
        createSigil(event.getTo());
        spawnLightningsAroundPlayer(event.getTo());
        spawnKonus(event.getTo());


    }

    private void spawnParticlesAroundPlayer(Location location) {
        // Радиус круга, в котором будут спавниться частицы
        double radius = 1;

        // Количество частиц
        int particleCount = 100;

        // Создаем цикл для спавна частиц по кругу
        for (int i = 0; i < particleCount; i++) {
            double angle = (double) i / particleCount * 2 * Math.PI;
            double x = location.getX() + radius * Math.cos(angle);
            double z = location.getZ() + radius * Math.sin(angle);

            // Спавним частицы
            location.getWorld().spawnParticle(Particle.SCULK_SOUL, x, location.getY() + 1, z, 0, 0, 0, 0, 1);
        }
    }
    private void spawnLightningsAroundPlayer(Location location) {
        // Радиус круга, в котором будут спавниться частицы
        double radius = 3;

        // Количество частиц
        int particleCount = 10;

        // Создаем цикл для спавна частиц по кругу
        for (int i = 0; i < particleCount; i++) {
            double angle = (double) i / particleCount * 2 * Math.PI;
            double x = location.getX() + radius * Math.cos(angle);
            double z = location.getZ() + radius * Math.sin(angle);

            // Спавним частицы
            location.getWorld().spawnEntity(new Location(location.getWorld(), x, location.getY(), z), EntityType.ZOMBIE);
        }
    }
    private void spawnKonus(Location playerLocation){
        double radius = 1;
        int height = 5;

        // Количество частиц
        int particleCount = 1000;
        double centerX = playerLocation.getX();
        double centerY = playerLocation.getY();
        double centerZ = playerLocation.getZ();

        // Генерация частиц в форме конуса
        for (int i = 0; i < particleCount; i++) {
            double angle = Math.random() * 2 * Math.PI; // Случайный угол
            double h = Math.random() * height; // Высота частицы
            double r = radius * (1 - (h / height)); // Радиус на данной высоте

            double x = centerX + r * Math.cos(angle);
            double z = centerZ + r * Math.sin(angle);
            double y = centerY + h;

            // Спавн частицы
            playerLocation.getWorld().spawnParticle(Particle.SCULK_SOUL, x, y, z, 0);
        }
    }
    public void createSigil(Location center) {
        double RADIUS = 3.0; // Радиус круга
        int PARTICLES_PER_CIRCLE = 60; // Количество частиц в круге
        double STAR_POINT_OFFSET = 0.5;

        // Создаем круг из частиц
        for (int i = 0; i < PARTICLES_PER_CIRCLE; i++) {
            double angle = 2 * Math.PI * i / PARTICLES_PER_CIRCLE;
            double x = center.getX() + RADIUS * Math.cos(angle);
            double z = center.getZ() + RADIUS * Math.sin(angle);
            Location particleLocation = new Location(center.getWorld(), x, center.getY(), z);
            // Для Хеллуина можно использовать частицы Particle.REDSTONE с красным цветом
            center.getWorld().spawnParticle(Particle.FLAME, particleLocation, 1, 0, 0, 0, 0);
        }

        // Создаем звезду в центре
        createStarLine(center, 0, STAR_POINT_OFFSET);
        createStarLine(center, Math.PI / 2, STAR_POINT_OFFSET);
        createStarLine(center, Math.PI / 4, STAR_POINT_OFFSET);
        createStarLine(center, 3 * Math.PI / 4, STAR_POINT_OFFSET);
    }

    // Вспомогательная функция для создания линии звезды
    private  void createStarLine(Location center, double angleOffset, double offset) {
        double angle = angleOffset;
        for (int i = 0; i <= 2; i++) {
            double x = center.getX() + offset * Math.cos(angle);
            double z = center.getZ() + offset * Math.sin(angle);
            Location particleLocation = new Location(center.getWorld(), x, center.getY(), z);
            // Для Хеллуина можно использовать частицы Particle.REDSTONE с оранжевым цветом
            center.getWorld().spawnParticle(Particle.FLAME, particleLocation, 1, 0, 0, 0, 0);
            offset += 0.5; // Увеличиваем смещение для следующей точки линии
        }
    }


    @EventHandler
    public void spawne(EntitySpawnEvent event) {
        EntityType type = event.getEntityType();
        Entity entity = event.getEntity();

        // Check if the spawned entity is a Zombie
        if (type.equals(EntityType.ZOMBIE)) {
                Random random = new Random();

            // Cancel the event to prevent the default Zombie spawn


            // Create a new Zombie with custom attributes
            Zombie zombie = (Zombie) entity.getWorld().spawnEntity(entity.getLocation(), EntityType.ZOMBIE);
            zombie.setCanBreakDoors(true);
            zombie.setShouldBurnInDay(false);
            zombie.setGlowing(true);
            Objects.requireNonNull(zombie.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(100);
            Objects.requireNonNull(zombie.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE)).setBaseValue(10);
            Objects.requireNonNull(zombie.getAttribute(Attribute.GENERIC_SCALE)).setBaseValue(2.5);

            // Set the equipment for the Zombie
            zombie.getEquipment().setHelmet(new ItemStack(Material.JACK_O_LANTERN));

            // Notify nearby players
            for (Player player : entity.getWorld().getNearbyPlayers(entity.getLocation(), 3)) {
                player.playSound(player, Sound.ENTITY_WITHER_AMBIENT, 1.0f, 1.0f);
                player.sendMessage("§"+random.nextInt(10)+"Но никто не пришел (;");
            }
            entity.remove();
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "kill @e[type=minecraft:chicken]");
            zombieList.add(zombie);
        }
    }
    @EventHandler
    public void entitydeath(EntityDeathEvent event){
        if(event.getEntityType().equals(EntityType.ZOMBIE)){
            Zombie zombie = (Zombie) event.getEntity();
            zombieList.remove(zombie);
        }
    }
}

