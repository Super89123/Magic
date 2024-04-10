package org.super89.supermegamod.magic;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
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
import java.util.Map;

public final class Magic extends JavaPlugin implements Listener {


    ManaAndThirst manaAndThirst = new ManaAndThirst(this);
    ReflectBook reflectBook = new ReflectBook();
    PufferManager pufferManager = new PufferManager();
    private static Magic plugin;
    private FileConfiguration config;
    private File configFile;



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
        Bukkit.getPluginManager().registerEvents(manaAndThirst, this);

        Bukkit.getPluginManager().registerEvents(new EvokerFangsBook(), this);
        Bukkit.getPluginManager().registerEvents(new MineBook(), this);
        Bukkit.getPluginManager().registerEvents(new LevitationBook(), this);
        Bukkit.getPluginManager().registerEvents(new InvetoryWithBooks(), this);
        Bukkit.getPluginManager().registerEvents(pufferManager, this);

        Bukkit.getPluginManager().registerEvents(new ReflectBook(), this);
        Bukkit.getPluginManager().registerEvents(new ReviewBook(), this);

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
        paperMeta.setCustomModelData(100000001);
        paper.setAmount(5);
        paper.setItemMeta(paperMeta);

        StonecuttingRecipe recipe123 = new StonecuttingRecipe(NamespacedKey.minecraft("nether_star_to_paper"), paper, Material.NETHER_STAR);

        Bukkit.addRecipe(recipe123);

        ItemStack rabbit_foot = new ItemStack(Material.RABBIT_FOOT);

        // Set the custom model data on the paper
        ItemMeta rabrMeta = rabbit_foot.getItemMeta();
        assert rabrMeta != null;
        PersistentDataContainer container1 = rabrMeta.getPersistentDataContainer();
        container1.set(new NamespacedKey("your-plugin-namespace", "custom-model-data"), PersistentDataType.INTEGER, 2025);
        rabrMeta.setCustomModelData(2025);
        rabbit_foot.setItemMeta(rabrMeta);

        StonecuttingRecipe recipe1234 = new StonecuttingRecipe(NamespacedKey.minecraft("lapis_lazuli_to_rabbit_foot"), rabbit_foot, Material.LAPIS_LAZULI);

        Bukkit.addRecipe(recipe1234);



        configFile = new File(getDataFolder(), "puffers.yml");
        config = YamlConfiguration.loadConfiguration(configFile);
        loadInventories();



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
                    if(nowmana<75){
                        int newmana = (int) (maxmana * 0.01);
                        add = Math.min(newmana + nowmana, maxmana);
                        playerDataConfig.set(uuid + "." + "nowmana", add);
                        try {
                            playerDataConfig.save(playerDataFile);
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                    ItemStack item = player.getInventory().getItemInOffHand();
                    if(item.hasItemMeta() && item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == 1010){
                        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 50, 4, false,false,false));
                    }
                    if(item.hasItemMeta() && item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == 1005){
                        player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 50, 4, false, false,false));
                    }
                    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "title "+player.getName()+" actionbar [{\"text\":\"Мана:" + nowmana + "/"+ maxmana +"ㅤㅤㅤㅤㅤ|ㅤㅤㅤㅤ"+manaAndThirst.calculatePlayerThirst(player)+"\",\"color\":\"aqua\"}]");
                    if(manaAndThirst.getNowPlayerThrist(player) == 0){
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 50, 5, false, false, false));
                    }


                }
            }
        }.runTaskTimer(this, 0L, 40L);
        new BukkitRunnable() {
            @Override
            public void run() {
                long currentTime = System.currentTimeMillis();
                for (Player player : reflectBook.activePlayers.keySet()) {
                    if (currentTime >= reflectBook.activePlayers.get(player)) {
                        reflectBook.activePlayers.remove(player);
                        player.sendMessage(ChatColor.RED + "Damage reflection effect expired!");
                    }
                }
            }
        }.runTaskTimer(this, 0, 20);
        new BukkitRunnable(){
            @Override
            public void run(){
                if(Bukkit.getOnlinePlayers().isEmpty()){
                    return;
                }
                for (Player player : Bukkit.getOnlinePlayers()){
                    if(manaAndThirst.getNowPlayerThrist(player) > 0) {
                        manaAndThirst.setNowPlayerThrist(player, manaAndThirst.getNowPlayerThrist(player) - 1);
                    }
                }
            }
        }.runTaskTimer(this, 0, 20*60);
    }
    @Override
    public void onDisable(){
        saveInventories();
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        // Проверяем, что игрок правым кликом использовал книгу
        if (player.getInventory().getItemInMainHand().getType() == Material.BOOK && event.getAction().name().contains("RIGHT_CLICK") && player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 1000 && player.getInventory().getItemInMainHand().getItemMeta().hasCustomModelData() && manaAndThirst.getNowPlayerMana(player)>=10) {
            manaAndThirst.setNowPlayerMana(player, manaAndThirst.getNowPlayerMana(player)-10);
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
    private String locationToString(Location location) {
        return location.getWorld().getName() + ";" + location.getBlockX() + ";" + location.getBlockY() + ";" + location.getBlockZ();
    }

    private Location locationFromString(String string) {
        String[] parts = string.split(";");
        if (parts.length == 4) {
            return new Location(Bukkit.getWorld(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
        }
        return null;
    }
    public void loadInventories() {
        for (String key : config.getKeys(false)) {
            Location location = locationFromString(key);
            if (location != null) {
                ItemStack[] contents = ((List<ItemStack>) config.get(key)).toArray(new ItemStack[0]);
                Inventory inventory = Bukkit.createInventory(null, 45, "Очиститель " + location.getBlockZ() +" "+ location.getBlockY() +" "+ location.getBlockZ());
                inventory.setContents(contents);
                pufferManager.pufferInventories.put(location, inventory);
            }
        }
    }
    public void saveInventories() {
        for (Map.Entry<Location, Inventory> entry : pufferManager.pufferInventories.entrySet()) {
            Location location = entry.getKey();
            Inventory inventory = entry.getValue();
            config.set(locationToString(location), inventory.getContents());
        }
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public  void removeInventoryFromFile(Location location) {
        config.set(locationToString(location), null);
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @EventHandler
    public void onNoteBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (block.getType() == Material.NOTE_BLOCK && pufferManager.pufferInventories.containsKey(block.getLocation())) {
            // Выбрасываем содержимое инвентаря
            Inventory inventory = pufferManager.pufferInventories.remove(block.getLocation());
            removeInventoryFromFile(block.getLocation());
            Location location = block.getLocation();
            for (ItemStack item : inventory.getContents()) {
                if (item != null) {
                    location.getWorld().dropItemNaturally(location, item);
                }
            }
        }
    }
    public static Magic getPlugin() {
        return plugin;
    }
}



