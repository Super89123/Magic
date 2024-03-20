package org.super89.supermegamod.magic;

import org.bukkit.*;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BossBar;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import org.bukkit.inventory.StonecuttingRecipe;

import java.io.File;
import java.io.IOException;
import java.util.List;

public final class Magic extends JavaPlugin implements Listener {


    Mana mana = new Mana(this);
    private static Magic plugin;



    @Override
    public void onEnable() {
        ItemStack stanbook = new ItemStack(Material.BOOK);
        ItemStack TeleportBook = new ItemStack(Material.BOOK);
        ItemStack ExplosionBook = new ItemStack(Material.BOOK);
        ItemMeta TeleportBookMeta = TeleportBook.getItemMeta();
        ItemMeta stanbookmeta = stanbook.getItemMeta();
        stanbookmeta.setCustomModelData(1000);
        stanbookmeta.setDisplayName(ChatColor.DARK_GRAY + "Книга Стана");
        stanbook.setItemMeta(stanbookmeta);
        getServer().getPluginManager().registerEvents(this, this);
        ShapedRecipe shapedRecipe = new ShapedRecipe(stanbook);
        shapedRecipe.shape("PPP", "OBO", "PPP");
        shapedRecipe.setIngredient('O', Material.DIAMOND);
        shapedRecipe.setIngredient('B', Material.BOOK);
        shapedRecipe.setIngredient('P', Material.HONEY_BOTTLE);
        Bukkit.addRecipe(shapedRecipe);

        getServer().getPluginManager().registerEvents(new LevitationBook(), this);

        TeleportBookMeta.setCustomModelData(1002);
        TeleportBookMeta.setDisplayName(ChatColor.DARK_PURPLE + "Книга Телепорта");
        TeleportBook.setItemMeta(TeleportBookMeta);
        ShapedRecipe TeleportBookRecipe = new ShapedRecipe(TeleportBook);
        TeleportBookRecipe.shape("EEE", "DXD", "EEE");
        TeleportBookRecipe.setIngredient('E', Material.ENDER_PEARL);
        TeleportBookRecipe.setIngredient('X', Material.BOOK);
        TeleportBookRecipe.setIngredient('D', Material.DIAMOND);
        Bukkit.addRecipe(TeleportBookRecipe);

        ItemMeta ExplosionBookMeta = ExplosionBook.getItemMeta();
        ExplosionBookMeta.setCustomModelData(1001);
        ExplosionBookMeta.setDisplayName(ChatColor.GOLD + "Книга Взрыва");
        ExplosionBook.setItemMeta(ExplosionBookMeta);
        ShapedRecipe ExplosionBookRecipe = new ShapedRecipe(ExplosionBook);
        ExplosionBookRecipe.shape("BBB", "DPD", "BBB");
        ExplosionBookRecipe.setIngredient('P', Material.BOOK);
        ExplosionBookRecipe.setIngredient('D', Material.DIAMOND);
        ExplosionBookRecipe.setIngredient('B', Material.GUNPOWDER);
        Bukkit.addRecipe(ExplosionBookRecipe);

        Bukkit.getPluginManager().registerEvents(new TeleportBook(this), this);
        Bukkit.getPluginManager().registerEvents(new ExplosionBook(this), this);
        Bukkit.getPluginManager().registerEvents(mana, this);
        Bukkit.getPluginManager().registerEvents(new Prokachka(this), this);
        Bukkit.getPluginManager().registerEvents(new CustomSword(this), this);
        Bukkit.getPluginManager().registerEvents(new EvokerFangsBook(), this);
        Bukkit.getPluginManager().registerEvents(new MineBook(), this);
        Bukkit.getPluginManager().registerEvents(new LevitationBook(), this);
        Bukkit.getPluginManager().registerEvents(new InvetoryWithBooks(), this);
        Bukkit.getPluginManager().registerEvents(new HealthBook(), this);
        Bukkit.getPluginManager().registerEvents(new ReflectBook(), this);
        Bukkit.getPluginManager().registerEvents(new ReflectDamageEffect() , this);
        Bukkit.getPluginManager().registerEvents(new ReviewBook(), this);
        Bukkit.getPluginManager().registerEvents(new ResistanceBook(), this);
        Bukkit.getPluginManager().registerEvents(new WindBook(), this);
        Bukkit.getPluginManager().registerEvents(new FireBook(), this);
        Bukkit.getPluginManager().registerEvents(new CustomPotion(), this);


        ItemStack netherStar = new ItemStack(Material.NETHER_STAR);
        ItemStack paper = new ItemStack(Material.PAPER);

        // Set the custom model data on the paper
        ItemMeta paperMeta = paper.getItemMeta();
        assert paperMeta != null;
        PersistentDataContainer container = paperMeta.getPersistentDataContainer();
        container.set(new NamespacedKey("your-plugin-namespace", "custom-model-data"), PersistentDataType.INTEGER, 10000001);
        paper.setItemMeta(paperMeta);

        StonecuttingRecipe recipe123 = new StonecuttingRecipe(NamespacedKey.minecraft("nether_star_to_paper"), paper, Material.NETHER_STAR);

        Bukkit.addRecipe(recipe123);

        ItemStack Hungry_sword = new ItemStack(Material.IRON_SWORD);
        ItemMeta Hungry_swordMeta = Hungry_sword.getItemMeta();
        Hungry_swordMeta.setCustomModelData(1488);
        Hungry_swordMeta.setDisplayName(ChatColor.DARK_RED + "Ненасытный меч");
        Hungry_sword.setItemMeta(Hungry_swordMeta);
        ShapedRecipe Hungry_swordRecipe = new ShapedRecipe(Hungry_sword);
        Hungry_swordRecipe.shape("BEB", "MSM", "BNB");
        Hungry_swordRecipe.setIngredient('B', Material.BONE);
        Hungry_swordRecipe.setIngredient('S', Material.IRON_SWORD);
        Hungry_swordRecipe.setIngredient('E', Material.SPIDER_EYE);
        Hungry_swordRecipe.setIngredient('M', Material.ROTTEN_FLESH);
        Hungry_swordRecipe.setIngredient('N', Material.ENDER_PEARL);
        Bukkit.addRecipe(Hungry_swordRecipe);
        plugin = this;




        // Plugin startup logic

        new BukkitRunnable() {
            @Override
            public void run() {
                if(Bukkit.getOnlinePlayers().isEmpty()){
                    return;
                }
                for (Player player : Bukkit.getOnlinePlayers()){
                    String uuid = player.getUniqueId().toString();
                    File playerDataFile = new File(getPlugin(Magic.class).getDataFolder(), "playerdata.yml");
                    FileConfiguration playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);
                    int maxmana = playerDataConfig.getInt(uuid + "." + "maxmana");
                    int nowmana = playerDataConfig.getInt(uuid + "." + "nowmana");
                    int add = 0;
                    if(nowmana<maxmana){
                        int newmana = (int) (maxmana * 0.05);
                        if(newmana+nowmana > maxmana){
                            add = maxmana;
                        }
                        else {
                            add = newmana+nowmana;

                        }
                        playerDataConfig.set(uuid + "." + "nowmana", add);
                        try {
                            playerDataConfig.save(playerDataFile);
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "title "+player.getName()+" actionbar [{\"text\":\"Мана:" + nowmana + "/"+ maxmana + "\",\"color\":\"aqua\"}]");

                }
            }
        }.runTaskTimer(this, 0L, 40L);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        // Проверяем, что игрок правым кликом использовал книгу
        if (player.getInventory().getItemInMainHand().getType() == Material.BOOK && event.getAction().name().contains("RIGHT_CLICK") && player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 1000 && player.getInventory().getItemInMainHand().getItemMeta().hasCustomModelData() && mana.getNowPlayerMana(player)>=10) {
            mana.setNowPlayerMana(player, mana.getNowPlayerMana(player)-10);
            // Получаем позицию, на которую игрок смотрит
            Location targetLocation = player.getTargetBlock(null, 100).getLocation();

            // Создаем куб из черных партиклов
            createParticleCube(targetLocation, 5, 5, 5, Particle.SCULK_SOUL);

            // Замораживаем игроков, попавших в партиклы, и наносим им урон
            freezeAndDamagePlayersInParticles(targetLocation, 5, 5, 5, 3, 0.1, player.getWorld());
        }
    }

    private void createParticleCube(Location location, int width, int height, int depth, Particle particle) {
        double offsetX = 0.5; // Смещение по X для центрирования куба
        double offsetY = 0.5; // Смещение по Y для центрирования куба
        double offsetZ = 0.5; // Смещение по Z для центрирования куба

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                for (int z = 0; z < depth; z++) {
                    double particleX = location.getX() + offsetX + x;
                    double particleY = location.getY() + offsetY + y;
                    double particleZ = location.getZ() + offsetZ + z;

                    location.getWorld().spawnParticle(particle, particleX, particleY, particleZ, 1);
                }
            }
        }
    }


    private void freezeAndDamagePlayersInParticles(Location location, int width, int height, int depth, int freezeDurationSeconds, double damagePerSecond, World world) {
        List<LivingEntity> entities = world.getLivingEntities();
        for (LivingEntity player : entities ) {
            if (isPlayerInCube(player.getLocation(), location, width, height, depth)) {
                // Замораживаем игрока на freezeDurationSeconds секунд
                freezePlayer(player, freezeDurationSeconds);

                // Наносим игроку урон damagePerSecond сердечка в секунду
                damagePlayer(player, damagePerSecond, freezeDurationSeconds);
            }
        }
    }

    private boolean isPlayerInCube(Location playerLocation, Location cubeLocation, int width, int height, int depth) {
        double playerX = playerLocation.getX();
        double playerY = playerLocation.getY();
        double playerZ = playerLocation.getZ();

        double cubeX = cubeLocation.getX();
        double cubeY = cubeLocation.getY();
        double cubeZ = cubeLocation.getZ();

        return playerX >= cubeX && playerX < cubeX + width &&
                playerY >= cubeY && playerY < cubeY + height &&
                playerZ >= cubeZ && playerZ < cubeZ + depth;
    }

    private void freezePlayer(LivingEntity entity, int durationSeconds) {
        entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, durationSeconds * 20, 255, false, false, false)); // Устанавливаем скорость ходьбы 0



    }
    private void damagePlayer(LivingEntity entity, double damagePerSecond, int durationSeconds) {
        new BukkitRunnable() {
            int ticks = 0;

            @Override
            public void run() {
                entity.damage(damagePerSecond / 2); // Наносим половину урона каждые 10 тиков (0.5 секунды)
                ticks++;

                if (ticks >= durationSeconds * 2) {
                    cancel();
                }
            }
        }.runTaskTimer(this, 0L, 10L); // Запускаем задачу с интервалом 10 тиков (0.5 секунды)
    }
    public static Magic getPlugin() {
        return plugin;
    }
}



