package org.super89.supermegamod.magic;


import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;


import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import org.super89.supermegamod.magic.Utils.ItemUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;

import static net.dv8tion.jda.api.interactions.commands.OptionType.STRING;

public final class Magic extends JavaPlugin implements Listener {

    public final String NAME = "Quantov";




    private final PlayerDataController playerDataController = new PlayerDataController(this);
    private final ReflectBook reflectBook = new ReflectBook();
    private final PufferManager pufferManager = new PufferManager(this);
    private InventoryWithCoolThings inventoryWithCoolThings = new InventoryWithCoolThings();
    private static Magic plugin;
    private FileConfiguration config1;
    private File configFile1;
    private int hp;
    private boolean isdiscord;
    private final WaitAsync waitAsync = new WaitAsync(Bukkit.getScheduler());
    private String token;
    private boolean supersays;


    public  Map<Location, Block> BarrierBlocks = new HashMap<>();

    public Magic() throws IOException, InvalidConfigurationException {
    }


    @Override
    public void onEnable() {


        configFile1 = new File(this.getDataFolder(), "puffers.yml");
        config1 = YamlConfiguration.loadConfiguration(configFile1);
        try {
            config1.load(configFile1);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }

        // Регистрация ивентов
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new LevitationBook(), this);
        getServer().getPluginManager().registerEvents(new SonicBook(), this);
        Bukkit.getPluginManager().registerEvents(new TeleportBook(this), this);
        Bukkit.getPluginManager().registerEvents(new ExplosionBook(this), this);
        Bukkit.getPluginManager().registerEvents(playerDataController, this);
        Bukkit.getPluginManager().registerEvents(new EvokerFangsBook(), this);
        Bukkit.getPluginManager().registerEvents(new MineBook(), this);
        Bukkit.getPluginManager().registerEvents(new LevitationBook(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryWithCoolThings(), this);
        Bukkit.getPluginManager().registerEvents(pufferManager, this);
        Bukkit.getPluginManager().registerEvents(new ShieldThings(), this);
        Bukkit.getPluginManager().registerEvents(new ReflectBook(), this);
        Bukkit.getPluginManager().registerEvents(new ReviewBook(), this);

        Bukkit.getPluginManager().registerEvents(new WindBook(), this);
        Bukkit.getPluginManager().registerEvents(new FireBook(), this);
        Bukkit.getPluginManager().registerEvents(new CustomPotion(), this);


        ItemStack netherStar = new ItemStack(Material.NETHER_STAR);
        ItemStack paper = new ItemStack(Material.PAPER);

        ItemMeta paperMeta = paper.getItemMeta();
        assert paperMeta != null;
        PersistentDataContainer container = paperMeta.getPersistentDataContainer();
        container.set(new NamespacedKey("your-plugin-namespace", "custom-model-data"), PersistentDataType.INTEGER, 10261);
        paperMeta.setCustomModelData(10261);
        paperMeta.setDisplayName("Звезднаяя пыль");
        paper.setAmount(5);
        paper.setItemMeta(paperMeta);

        StonecuttingRecipe recipe123 = new StonecuttingRecipe(NamespacedKey.minecraft("nether_star_to_paper"), paper, Material.NETHER_STAR);

        Bukkit.addRecipe(recipe123);

        ItemStack rabbit_foot = new ItemStack(Material.RABBIT_FOOT);

        ItemMeta rabrMeta = rabbit_foot.getItemMeta();
        assert rabrMeta != null;
        PersistentDataContainer container1 = rabrMeta.getPersistentDataContainer();
        container1.set(new NamespacedKey(this, "custom-model-data"), PersistentDataType.INTEGER, 10000);
        rabrMeta.setCustomModelData(10000);
        rabrMeta.setDisplayName("Лазуритовый порошок");
        rabbit_foot.setItemMeta(rabrMeta);
        rabbit_foot.setAmount(5);

        StonecuttingRecipe recipe1234 = new StonecuttingRecipe(NamespacedKey.minecraft("lapis_lazuli_to_rabbit_foot"), rabbit_foot, Material.LAPIS_LAZULI);

        Bukkit.addRecipe(recipe1234);


        ItemStack amethyst_dust = new ItemStack(Material.PAPER);
        ItemMeta amethyst_dust_meta = amethyst_dust.getItemMeta();
        assert amethyst_dust_meta != null;
        PersistentDataContainer container2 = amethyst_dust_meta.getPersistentDataContainer();
        container2.set(new NamespacedKey(this, "custom-model-data"), PersistentDataType.INTEGER, 10260);
        amethyst_dust_meta.setCustomModelData(10260);
        amethyst_dust_meta.setDisplayName("Аметистовый порошок");
        amethyst_dust.setItemMeta(amethyst_dust_meta);
        amethyst_dust.setAmount(3);
        StonecuttingRecipe recipe12345 = new StonecuttingRecipe(NamespacedKey.minecraft("amethyst_dust1"), amethyst_dust, Material.AMETHYST_SHARD);
        Bukkit.addRecipe(recipe12345);

        ItemStack prismarine_dust = new ItemStack(Material.PAPER);
        ItemMeta prismarine_dust_meta = prismarine_dust.getItemMeta();
        assert prismarine_dust_meta != null;
        PersistentDataContainer container3 = prismarine_dust_meta.getPersistentDataContainer();
        container3.set(new NamespacedKey(this, "custom-model-data"), PersistentDataType.INTEGER, 10263);
        prismarine_dust_meta.setCustomModelData(10263);
        prismarine_dust_meta.setDisplayName("Призмариновый порошок");
        prismarine_dust.setItemMeta(prismarine_dust_meta);
        prismarine_dust.setAmount(5);
        StonecuttingRecipe recipe123456 = new StonecuttingRecipe(NamespacedKey.minecraft("prismarine_dust"), prismarine_dust, Material.PRISMARINE_SHARD);
        Bukkit.addRecipe(recipe123456);


        plugin = this;
        loadInventories();
        YamlConfiguration config = new YamlConfiguration();
        File file = new File(getDataFolder(), "config.yml");
        try {
            config.load(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
        if(file.exists()){
            this.hp = (int) config.get("hp");
            this.token = (String) config.get("token");
            this.isdiscord = config.getBoolean("discord_enabled");
            this.supersays = config.getBoolean("super_says");

        }
        //ffff
        else{
            file.mkdirs();
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            config.set("hp", 5);
            config.set("token", "YOUR_TOKEN_BOT");
            config.set("discord_enabled", true);
            config.set("super_says", true);
             this.token = (String) config.get("token");
        }
        if(isdiscord) {
            JDA jda = JDABuilder.createLight(token, Collections.emptyList())
                    .addEventListeners(new SlashCommandListener(plugin))
                    .build();

            // Register your commands to make them visible globally on Discord:

            CommandListUpdateAction commands = jda.updateCommands();

            // Add all your commands on this action instance
            commands.addCommands(
                    Commands.slash("players", "Написать игроков в сети")
                            .setGuildOnly(true), // Accepting a user input
                    Commands.slash("leave", "Makes the bot leave the server")
                            .setGuildOnly(true) // this doesn't make sense in DMs
                            .setDefaultPermissions(DefaultMemberPermissions.DISABLED),
                    Commands.slash("supersay" , "Уникальная мини-игра!")
                            .addOption(STRING, "event", "Что ДОЛЖНО пройзойти?")// only admins should be able to use this command.
            );

            // Then finally send your commands to discord using the API
            commands.queue();
        }
        else {
            getLogger().log(Level.INFO, "Важно! Вы выключили дискорд! Если вы хотите включить его - измените discord_enabled в конфигурации.");
        }


        new BukkitRunnable() {
            @Override
            public void run() {
                if (Bukkit.getOnlinePlayers().isEmpty()) {
                    return;
                }
                for (Player player : Bukkit.getOnlinePlayers()) {
                    String uuid = player.getUniqueId().toString();
                    File playerDataFile = new File(getPlugin(Magic.class).getDataFolder(), "playerdata.yml");
                    FileConfiguration playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);
                    int maxmana = playerDataConfig.getInt(uuid + "." + "maxmana");
                    int nowmana = playerDataConfig.getInt(uuid + "." + "nowmana");
                    int add;
                    if (nowmana < 75) {
                        int newmana = (int) (maxmana * 0.01);
                        add = Math.min(newmana + nowmana, maxmana);
                        playerDataConfig.set(uuid + "." + "nowmana", add);
                        try {
                            playerDataConfig.save(playerDataFile);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    ItemStack item = player.getInventory().getItemInOffHand();
                    if (item.hasItemMeta() && item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == 1010) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 50, 2, false, false, false));
                    }
                    if (item.hasItemMeta() && item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == 10003) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 50, hp, false, false, false));
                    }
                    TextComponent actionbarMessage = Component.text("Мана: " + nowmana + "/" + maxmana + "    Жажда?", Style.style(TextColor.color(59, 223, 235), TextDecoration.BOLD));


                    player.sendActionBar(actionbarMessage);


                    if (playerDataController.getNowPlayerThrist(player) == 0) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 50, 5, false, false, false));
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
                        player.sendMessage(ChatColor.RED + "Эффект шипов истек!");
                    }
                }


            }
        }.runTaskTimer(this, 0, 20);
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    int a = playerDataController.getNowPlayerState(player);
                    Location location = new Location(player.getWorld(), player.getX(), player.getY() + 1, player.getZ());
                    Block block = location.getBlock();
                    if (a != -1) {

                        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 2, false, false, false));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 40, 2, false, false, false));


                    }
                    if (a == -1) {

                    }


                }
            }
        }.runTaskTimer(plugin, 0, 10);
        plugin.getLogger().log(Level.INFO, Bukkit.getIp());

        plugin = this;
    }
    @Override
    public void onDisable(){

    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if(player.getCooldown(Material.BOOK) == 0){
        if (player.getInventory().getItemInMainHand().getType() == Material.BOOK && event.getAction().name().contains("RIGHT_CLICK") && player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 10005 && player.getInventory().getItemInMainHand().getItemMeta().hasCustomModelData() && playerDataController.getNowPlayerMana(player)>=10) {
            playerDataController.setNowPlayerMana(player, playerDataController.getNowPlayerMana(player)-10);

            Location targetLocation = player.getTargetBlock(null, 100).getLocation();
            createParticleCube(targetLocation, 5, 5, 5, Particle.SCULK_SOUL);
            freezeAndDamagePlayersInParticles(targetLocation, 5, 5, 5, 3, 0.1, player.getWorld());
            player.setCooldown(Material.BOOK, 10);
        }


        }

    }

    private void createParticleCube(Location location, int width, int height, int depth, Particle particle) {
        double offsetX = 0.5;
        double offsetY = 0.5;
        double offsetZ = 0.5;

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

                freezePlayer(player, freezeDurationSeconds);

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
        entity.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, durationSeconds * 20, 255, false, false, false)); // Устанавливаем скорость ходьбы 0



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


            /* public void damageEvent(EntityDamageEvent event){
        if(event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
            if (player.getHealth()-event.getDamage() > 2) {
                return;
            }
            if (player.getHealth()-event.getDamage() <= 2 && playerDataController.getNowPlayerState(player) == -1){
                playerDataController.setNowPlayerPkm(player, 9);
                Location location = player.getLocation();
                location.setY(location.getY()+1);
                Block block = location.getBlock();
                if(block.getType().equals(Material.AIR)){
                    block.setType(Material.BARRIER);
                    BarrierBlocks.put(location, block);
                }

                new BukkitRunnable() {
                    int ticks = 0;

                    @Override
                    public void run() {

                        ticks++;

                        if (ticks >= 60){
                            if(playerDataController.getNowPlayerState(player) != -1) {
                                player.damage(1024);


                            }
                            cancel();
                        }
                    }
                }.runTaskTimer(this, 0L, 20L);
            }            Location location = new Location(player.getWorld(), player.getX(), player.getY()+1, player.getZ());
            if(playerDataController.getNowPlayerState(player) != -1 && (event.getCause().equals(EntityDamageEvent.DamageCause.SUFFOCATION) || event.getCause().equals(EntityDamageEvent.DamageCause.DROWNING) || event.getCause().equals(EntityDamageEvent.DamageCause.CONTACT))){
                event.setCancelled(true);
            }




        }

    }*/


    @EventHandler
    public void regenerationevent(EntityRegainHealthEvent event){
        if(event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            if(playerDataController.getNowPlayerState(player) != -1 && !event.getRegainReason().equals(EntityRegainHealthEvent.RegainReason.MAGIC)) {
                event.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void clickevent(PlayerInteractEntityEvent event){
        if(event.getRightClicked() instanceof Player){
            Player player = (Player) event.getRightClicked();
            if(playerDataController.getNowPlayerState(player) != -1){
                event.getPlayer().playSound(event.getPlayer(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 100, 100);
                playerDataController.setNowPlayerPkm(player, playerDataController.getNowPlayerState(player) -1);
                if(playerDataController.getNowPlayerState(player) == -1){
                    player.setHealth(player.getHealth()+2);
                    player.playSound(player, Sound.ENTITY_ENDER_DRAGON_GROWL, 100, 100);

                }

            }
        }
    }

    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        if (!(event.getEntity() instanceof Arrow)) return;
        Arrow arrow = (Arrow) event.getEntity();
        if (!(event.getEntity().getShooter() instanceof Player)) return;
        Player shooter = (Player) event.getEntity().getShooter();
        if(shooter.getInventory().getItemInMainHand().hasItemMeta() && shooter.getInventory().getItemInMainHand().getType().equals(Material.CROSSBOW) && shooter.getInventory().getItemInMainHand().getItemMeta().hasCustomModelData() && shooter.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 10000){

            Entity nearbyEntity = arrow.getNearbyEntities(10, 10, 10).stream()
                    .filter(entity -> entity instanceof LivingEntity && entity != shooter) // Исключаем стрелка из поиска
                    .min(Comparator.comparingDouble(entity -> entity.getLocation().distance(arrow.getLocation())))
                    .orElse(null);
            if (nearbyEntity != null) {
                @NotNull Vector direction = nearbyEntity.getLocation().subtract(arrow.getLocation()).toVector().normalize();
                arrow.setVelocity(direction.multiply(2));
                arrow.getWorld().spawnParticle(Particle.FLAME, arrow.getLocation(), 10, 0.2, 0.2, 0.2, 0.1);
            }
        }

    }

    @EventHandler
    public void deathEvenet(PlayerDeathEvent event){
        Player player = event.getPlayer();
        playerDataController.setNowPlayerPkm(player, -1);
    }
    @EventHandler
    public void targetEvent(EntityTargetLivingEntityEvent event){
        if(event.getTarget() instanceof Player){
            Player player = (Player) event.getTarget();
            if(playerDataController.getNowPlayerState(player) != -1){
                event.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void onPotionSplash(PotionSplashEvent event) {
        ThrownPotion potion = event.getPotion();
        @NotNull Collection<PotionEffect> effectType = potion.getEffects();

        if (effectType.contains(PotionEffectType.HEALTH_BOOST) || effectType.contains(PotionEffectType.INSTANT_HEALTH) || effectType.contains(PotionEffectType.REGENERATION)) {
            for (LivingEntity entity : event.getAffectedEntities()) {
                if (entity instanceof Player) {
                    Player player = (Player) entity;
                    playerDataController.setNowPlayerPkm(player, -1);

                }
            }
        }
    }
    @EventHandler
    public void onNoteBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (block.getType() == Material.NOTE_BLOCK && pufferManager.pufferInventories.containsKey(block.getLocation())){
            Inventory inventory = pufferManager.pufferInventories.get(block.getLocation());
            removeInventoryFromFile(block.getLocation());
            pufferManager.pufferUpgradeInventories.remove(block.getLocation());
            Location location = block.getLocation();
            saveInventories();
            for (ItemStack item : inventory.getContents()) {
                if ((item != null) && !item.getType().equals(Material.LIME_WOOL) && !item.getType().equals(Material.RED_WOOL) && !item.getType().equals(Material.PURPLE_STAINED_GLASS_PANE) && !item.getType().equals(Material.LIGHT_GRAY_STAINED_GLASS_PANE) && !item.getType().equals(Material.GRAY_STAINED_GLASS_PANE) && !item.getType().equals(Material.GREEN_WOOL)) {
                    location.getWorld().dropItemNaturally(location, item);
                }
            }
        }
    }




    // Метод для создания меню улучшений
    private void createUpgradeInventory(Location location) {
        Inventory upgradeInventory = Bukkit.createInventory(null, 9, "§4Улучшения Очистителя");
        for (int i = 0; i < 9; i++) {
            if (i != 3 && i != 4 && i != 5) {
                upgradeInventory.setItem(i, ItemUtils.create(Material.LIGHT_GRAY_STAINED_GLASS_PANE, " "));
            }
        }
        pufferManager.pufferUpgradeInventories.put(location, upgradeInventory);
    }

    // Метод для получения инвентаря очистителя
    public Inventory getPufferInventory(Block noteBlock) {
        return pufferManager.pufferInventories.get(noteBlock.getLocation());
    }

    // Метод для получения инвентаря улучшений
    public Inventory getUpgradeInventory(Block noteBlock) {
        return pufferManager.pufferUpgradeInventories.get(noteBlock.getLocation());
    }






    // Обработчик кликов в инвентаре очистителя
    @EventHandler
    public void invclick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getClickedInventory();
        if (pufferManager.pufferInventories.containsValue(event.getClickedInventory())) {
            Location location = getKeyByValue(pufferManager.pufferInventories, inventory);

            // Логика взаимодействия с очистителем
            if (event.getCursor() != null) {
                if (event.getSlot() == 10 && (Objects.requireNonNull(event.getCursor()).getType().equals(Material.WATER_BUCKET))) {
                    if (Objects.requireNonNull(Objects.requireNonNull(inventory).getItem(30)).getType().equals(Material.RED_WOOL)) {

                        inventory.setItem(10, new ItemStack(Material.BUCKET)); // Используем Material

                        inventory.setItem(30, ItemUtils.create(Material.LIME_WOOL, " "));
                        event.setCursor(new ItemStack(Material.AIR));
                        event.setCancelled(true); // Убираем отмену, чтобы предмет клался
                        // Запускаем готовку
                        return;
                    }
                    if (Objects.requireNonNull(inventory.getItem(21)).getType().equals(Material.RED_WOOL) && Objects.requireNonNull(inventory.getItem(30)).getType().equals(Material.LIME_WOOL)) {

                        inventory.setItem(10, new ItemStack(Material.BUCKET)); // Используем Material

                        inventory.setItem(21, ItemUtils.create(Material.LIME_WOOL, " "));
                        event.setCursor(new ItemStack(Material.AIR));
                        event.setCancelled(true); // Убираем отмену, чтобы предмет клался
                        // Запускаем готовку
                        return;
                    }
                    if (Objects.requireNonNull(inventory.getItem(12)).getType().equals(Material.RED_WOOL) && Objects.requireNonNull(inventory.getItem(30)).getType().equals(Material.LIME_WOOL) && Objects.requireNonNull(inventory.getItem(21)).getType().equals(Material.LIME_WOOL)) {

                        inventory.setItem(10, new ItemStack(Material.BUCKET)); // Используем Material

                        inventory.setItem(12, ItemUtils.create(Material.LIME_WOOL, " "));
                        event.setCursor(new ItemStack(Material.AIR));
                        event.setCancelled(true);
                        return;
                    }
                    pufferManager.pufferInventories.replace(location, inventory);
                }
                if(event.getSlot() == 10 && (event.getCursor().getType().equals(Material.BUCKET) || event.getCurrentItem().getType().equals(Material.BUCKET))){
                    player.getInventory().addItem(new ItemStack(Material.BUCKET));
                    event.setCursor(new ItemStack(Material.AIR));
                    event.setCurrentItem(new ItemStack(Material.AIR));
                    assert inventory != null;
                    inventory.setItem(10, ItemUtils.create(Material.LIGHT_GRAY_STAINED_GLASS_PANE, " "));
                    event.setCancelled(true);
                }
                if(event.getSlot() == 10 && !(event.getCursor().getType().equals(Material.BUCKET) || event.getCurrentItem().getType().equals(Material.BUCKET))){
                    event.setCancelled(true);
                }
                if(event.getSlot() != 10 && event.getSlot() != 37 && event.getSlot() != 25 && event.getSlot() != 22 && event.getSlot() != 23 &&  event.getSlot() != 53){
                    event.setCancelled(true);
                    return;
                }
                if(event.getSlot() == 23){
                    startCooking(inventory, player);
                    event.setCancelled(true);
                    return;
                }


                if(event.getSlot() == 53){
                    event.setCancelled(true);
                    player.openInventory(getUpgradeInventory(Objects.requireNonNull(getKeyByValue(pufferManager.pufferInventories, inventory)).getBlock()));

                }

            }


            pufferManager.pufferInventories.replace(location, inventory);
            saveInventories();
        } else if (pufferManager.pufferUpgradeInventories.containsValue(event.getClickedInventory())) {
            // Логика взаимодействия с меню улучшений
            Location location = getKeyByValue(pufferManager.pufferUpgradeInventories, inventory);
            if (Objects.requireNonNull(event.getCurrentItem()).getType().equals(Material.LIGHT_GRAY_STAINED_GLASS_PANE)) {
                event.setCancelled(true);
                return;
            }
            if (event.getSlot() == 3) {
                if (event.getCursor().getType() == Material.IRON_NUGGET) {
                    event.setCursor(new ItemStack(Material.AIR));
                    // Устанавливаем ускорение в меню улучшений
                    inventory.setItem(3, ItemUtils.create(Material.IRON_NUGGET, "§aУскорение"));
                    pufferManager.pufferUpgradeInventories.replace(location, inventory);
                }
            }
            if (event.getSlot() == 4) {
                if (event.getCursor().getType() == Material.WATER_BUCKET) {
                    event.setCursor(new ItemStack(Material.AIR));
                    // Устанавливаем воронку в меню улучшений
                    inventory.setItem(4, ItemUtils.create(Material.WATER_BUCKET, "§aАвтоматическое наполнение"));
                    pufferManager.pufferUpgradeInventories.replace(location, inventory);
                }
            }
        }
    }

    // Метод для запуска процесса приготовления
    private void startCooking(Inventory inventory, Player player) {
        ItemStack result1 = new ItemStack(Material.POTION);
        ItemMeta result1ItemMeta = result1.getItemMeta();
        result1ItemMeta.setCustomModelData(2030);
        result1ItemMeta.setDisplayName(ChatColor.WHITE + "Очищенная вода");
        result1.setItemMeta(result1ItemMeta);

        ItemStack result2 = new ItemStack(Material.POTION);
        ItemMeta result2ItemMeta = result1.getItemMeta();
        result2ItemMeta.setCustomModelData(2031);
        result2ItemMeta.setDisplayName(ChatColor.WHITE + "Очищенная вода+");
        result2.setItemMeta(result2ItemMeta);

        ItemStack result3 = new ItemStack(Material.POTION);
        ItemMeta result3ItemMeta = result1.getItemMeta();
        result3ItemMeta.setCustomModelData(10000);
        result3ItemMeta.setDisplayName(ChatColor.WHITE + "Кружка с водой");
        result3.setItemMeta(result2ItemMeta);

        ItemStack result4 = new ItemStack(Material.POTION);
        ItemMeta result4ItemMeta = result1.getItemMeta();
        result4ItemMeta.setCustomModelData(10000);
        result4ItemMeta.setDisplayName(ChatColor.WHITE + "Кружка с водой+");
        result4.setItemMeta(result2ItemMeta);

        ItemStack cup = new ItemStack(Material.GLASS_BOTTLE);
        ItemMeta cupmeta = cup.getItemMeta();
        cupmeta.setCustomModelData(10000);
        cupmeta.setDisplayName(ChatColor.WHITE + "Кружка");
        cupmeta.setLore(Collections.singletonList("Остаток: " + 0 + "/" + 27));
        cup.setItemMeta(cupmeta);

        if(inventory.getItem(12).getType().equals(Material.LIME_WOOL) || inventory.getItem(21).getType().equals(Material.LIME_WOOL) ||  inventory.getItem(30).getType().equals(Material.LIME_WOOL)){
            if(inventory.getItem(12).getType().equals(Material.LIME_WOOL)){
                if(inventory.getItem(22).getType().equals(Material.GLASS_BOTTLE)){
                    if(inventory.getItem(37).getType().equals(Material.PAPER) && inventory.getItem(37).hasItemMeta() && inventory.getItem(37).getItemMeta().hasCustomModelData()){
                        if(inventory.getItem(37).getItemMeta().getCustomModelData() == 10260){

                            inventory.setItem(12, ItemUtils.create(Material.RED_WOOL, " "));
                            inventory.setItem(22, ItemUtils.create(Material.GLASS_BOTTLE, "", inventory.getItem(22).getAmount()-1, (byte) 0));
                            ItemStack newDust = inventory.getItem(37);
                            newDust.setAmount(newDust.getAmount()-1);
                            inventory.setItem(37, newDust);
                            waitAsync.waitAsync(10, result1, inventory);
                            inventory.setItem(25, result1);
                        }
                        else if (inventory.getItem(37).getItemMeta().getCustomModelData() == 10263) {
                            inventory.setItem(12, ItemUtils.create(Material.RED_WOOL, " "));
                            inventory.setItem(22, ItemUtils.create(Material.GLASS_BOTTLE, "", inventory.getItem(22).getAmount()-1, (byte) 0));
                            ItemStack newDust = inventory.getItem(37);
                            newDust.setAmount(newDust.getAmount()-1);
                            inventory.setItem(37, newDust);
                            waitAsync.waitAsync(10, result2, inventory);
                            inventory.setItem(25, result2);

                        }
                        else {
                            player.sendMessage(ChatColor.RED+"Неправильный ингридиент!");

                        }
                    }
                    else {
                        player.sendMessage(ChatColor.RED + "Неправильный ингридиент!");

                    }

                }
                else if (inventory.getItem(22).hasItemMeta() && inventory.getItem(22).getItemMeta().hasCustomModelData() && inventory.getItem(22).getItemMeta().getCustomModelData() == 2033) {
                    if(inventory.getItem(37).getType().equals(Material.PAPER) && inventory.getItem(37).hasItemMeta() && inventory.getItem(37).getItemMeta().hasCustomModelData()){
                        if(inventory.getItem(37).getItemMeta().getCustomModelData() == 10260){

                            inventory.setItem(12, ItemUtils.create(Material.RED_WOOL, " "));
                            ItemStack newCup = new ItemStack(cup);
                            ItemMeta newCupMeta = newCup.getItemMeta();
                            newCupMeta.setCustomModelData(2033);
                            newCupMeta.setDisplayName(ChatColor.WHITE + "Кружка");
                            newCup.setAmount(inventory.getItem(22).getAmount()-1);
                            newCupMeta.setLore(Collections.singletonList("Остаток: " + 0 + "/" + 27));
                            newCup.setItemMeta(newCupMeta);
                            inventory.setItem(22, newCup);
                            ItemStack newDust = inventory.getItem(37);
                            newDust.setAmount(newDust.getAmount()-1);
                            ItemMeta newDustMeta = newDust.getItemMeta();
                            newDustMeta.setLore(Collections.singletonList("Остаток: " + 27 + "/" + 27));
                            inventory.setItem(37, newDust);
                            waitAsync.waitAsync(10, result4, inventory);
                            inventory.setItem(25, result3);
                        }
                        else if (inventory.getItem(37).getItemMeta().getCustomModelData() == 10263) {
                            inventory.setItem(12, ItemUtils.create(Material.RED_WOOL, " "));
                            ItemStack newCup = new ItemStack(cup);
                            ItemMeta newCupMeta = newCup.getItemMeta();
                            newCupMeta.setCustomModelData(2033);
                            newCupMeta.setDisplayName(ChatColor.WHITE + "Кружка");
                            newCup.setAmount(inventory.getItem(22).getAmount()-1);
                            newCupMeta.setLore(Collections.singletonList("Остаток: " + 0 + "/" + 27));
                            newCup.setItemMeta(newCupMeta);
                            ItemStack newDust = inventory.getItem(37);
                            newDust.setAmount(newDust.getAmount()-1);
                            inventory.setItem(37, newDust);
                            waitAsync.waitAsync(10, result4, inventory);
                            inventory.setItem(25, result4);

                        }
                        else {
                            player.sendMessage(ChatColor.RED+"Неправильный ингридиент!");

                        }
                    }
                    else {
                        player.sendMessage(ChatColor.RED + "Неправильный ингридиент!");

                    }


                }
                else {
                    player.sendMessage(ChatColor.RED + "Неправильная ёмкость для воды");
                }

            }
            else if (inventory.getItem(12).getType().equals(Material.RED_WOOL) && inventory.getItem(21).getType().equals(Material.LIME_WOOL)) {
                if(inventory.getItem(22).getType().equals(Material.GLASS_BOTTLE)){
                    if(inventory.getItem(37).getType().equals(Material.PAPER) && inventory.getItem(37).hasItemMeta() && inventory.getItem(37).getItemMeta().hasCustomModelData()){
                        if(inventory.getItem(37).getItemMeta().getCustomModelData() == 10260){

                            inventory.setItem(12, ItemUtils.create(Material.RED_WOOL, " "));
                            inventory.setItem(22, ItemUtils.create(Material.GLASS_BOTTLE, "", inventory.getItem(22).getAmount()-1, (byte) 0));
                            ItemStack newDust = inventory.getItem(37);
                            newDust.setAmount(newDust.getAmount()-1);
                            inventory.setItem(37, newDust);
                            waitAsync.waitAsync(10, result1, inventory);
                            inventory.setItem(25, result1);

                        }
                        else if (inventory.getItem(37).getItemMeta().getCustomModelData() == 10263) {
                            inventory.setItem(21, ItemUtils.create(Material.RED_WOOL, " "));
                            inventory.setItem(22, ItemUtils.create(Material.GLASS_BOTTLE, "", inventory.getItem(22).getAmount()-1, (byte) 0));
                            ItemStack newDust = inventory.getItem(37);
                            newDust.setAmount(newDust.getAmount()-1);
                            inventory.setItem(37, newDust);
                            waitAsync.waitAsync(10, result2, inventory);
                            inventory.setItem(25, result2);


                        }
                        else {
                            player.sendMessage(ChatColor.RED+"Неправильный ингридиент!");

                        }
                    }
                    else {
                        player.sendMessage(ChatColor.RED + "Неправильный ингридиент!");

                    }

                }
                else if (inventory.getItem(22).hasItemMeta() && inventory.getItem(22).getItemMeta().hasCustomModelData() && inventory.getItem(22).getItemMeta().getCustomModelData() == 2033) {
                    if(inventory.getItem(37).getType().equals(Material.PAPER) && inventory.getItem(37).hasItemMeta() && inventory.getItem(37).getItemMeta().hasCustomModelData()){
                        if(inventory.getItem(37).getItemMeta().getCustomModelData() == 10260){

                            inventory.setItem(21, ItemUtils.create(Material.RED_WOOL, " "));
                            ItemStack newCup = new ItemStack(cup);
                            ItemMeta newCupMeta = newCup.getItemMeta();
                            newCupMeta.setCustomModelData(2033);
                            newCupMeta.setDisplayName(ChatColor.WHITE + "Кружка");
                            newCup.setAmount(inventory.getItem(22).getAmount()-1);
                            newCupMeta.setLore(Collections.singletonList("Остаток: " + 0 + "/" + 27));
                            newCup.setItemMeta(newCupMeta);
                            ItemStack newDust = inventory.getItem(37);
                            newDust.setAmount(newDust.getAmount()-1);
                            inventory.setItem(37, newDust);
                            waitAsync.waitAsync(10, result3, inventory);
                            inventory.setItem(25, result3);

                        }
                        else if (inventory.getItem(37).getItemMeta().getCustomModelData() == 10263) {
                            inventory.setItem(21, ItemUtils.create(Material.RED_WOOL, " "));
                            ItemStack newCup = new ItemStack(cup);
                            ItemMeta newCupMeta = newCup.getItemMeta();
                            newCupMeta.setCustomModelData(2033);
                            newCupMeta.setDisplayName(ChatColor.WHITE + "Кружка");
                            newCup.setAmount(inventory.getItem(22).getAmount()-1);
                            newCupMeta.setLore(Collections.singletonList("Остаток: " + 0 + "/" + 27));
                            newCup.setItemMeta(newCupMeta);
                            ItemStack newDust = inventory.getItem(37);
                            newDust.setAmount(newDust.getAmount()-1);
                            inventory.setItem(37, newDust);
                            waitAsync.waitAsync(10, result4, inventory);
                            inventory.setItem(25, result4);


                        }
                        else {
                            player.sendMessage(ChatColor.RED+"Неправильный ингридиент!");

                        }
                    }
                    else {
                        player.sendMessage(ChatColor.RED + "Неправильный ингридиент!");

                    }


                }
                else {
                    player.sendMessage(ChatColor.RED + "Неправильная ёмкость для воды");
                }


            }
            else if (inventory.getItem(12).getType().equals(Material.RED_WOOL) && inventory.getItem(21).getType().equals(Material.RED_WOOL) && inventory.getItem(30).getType().equals(Material.LIME_WOOL)) {
                if(inventory.getItem(22).getType().equals(Material.GLASS_BOTTLE)){
                    if(inventory.getItem(37).getType().equals(Material.PAPER) && inventory.getItem(37).hasItemMeta() && inventory.getItem(37).getItemMeta().hasCustomModelData()){
                        if(inventory.getItem(37).getItemMeta().getCustomModelData() == 10260){

                            inventory.setItem(30, ItemUtils.create(Material.RED_WOOL, " "));
                            inventory.setItem(22, ItemUtils.create(Material.GLASS_BOTTLE, "", inventory.getItem(22).getAmount()-1, (byte) 0));
                            ItemStack newDust = inventory.getItem(37);
                            newDust.setAmount(newDust.getAmount()-1);
                            inventory.setItem(37, newDust);
                            waitAsync.waitAsync(10, result1, inventory);
                            inventory.setItem(25, result1);

                        }
                        else if (inventory.getItem(37).getItemMeta().getCustomModelData() == 10263) {
                            inventory.setItem(30, ItemUtils.create(Material.RED_WOOL, " "));
                            inventory.setItem(22, ItemUtils.create(Material.GLASS_BOTTLE, "", inventory.getItem(22).getAmount()-1, (byte) 0));
                            ItemStack newDust = inventory.getItem(37);
                            newDust.setAmount(newDust.getAmount()-1);
                            inventory.setItem(37, newDust);
                            waitAsync.waitAsync(10, result2, inventory);
                            inventory.setItem(25, result2);


                        }
                        else {
                            player.sendMessage(ChatColor.RED+"Неправильный ингридиент!");

                        }
                    }
                    else {
                        player.sendMessage(ChatColor.RED + "Неправильный ингридиент!");

                    }

                }
                else if (inventory.getItem(22).hasItemMeta() && inventory.getItem(22).getItemMeta().hasCustomModelData() && inventory.getItem(22).getItemMeta().getCustomModelData() == 2033) {
                    if(inventory.getItem(37).getType().equals(Material.PAPER) && inventory.getItem(37).hasItemMeta() && inventory.getItem(37).getItemMeta().hasCustomModelData()){
                        if(inventory.getItem(37).getItemMeta().getCustomModelData() == 10260){

                            inventory.setItem(30, ItemUtils.create(Material.RED_WOOL, " "));
                            ItemStack newCup = new ItemStack(cup);
                            ItemMeta newCupMeta = newCup.getItemMeta();
                            newCupMeta.setCustomModelData(2033);
                            newCupMeta.setDisplayName(ChatColor.WHITE + "Кружка");
                            newCup.setAmount(inventory.getItem(22).getAmount()-1);
                            newCupMeta.setLore(Collections.singletonList("Остаток: " + 0 + "/" + 27));
                            newCup.setItemMeta(newCupMeta);
                            ItemStack newDust = inventory.getItem(37);
                            newDust.setAmount(newDust.getAmount()-1);
                            inventory.setItem(37, newDust);
                            waitAsync.waitAsync(10, result3, inventory);
                            inventory.setItem(25, result3);

                        }
                        else if (inventory.getItem(37).getItemMeta().getCustomModelData() == 10263) {
                            inventory.setItem(30, ItemUtils.create(Material.RED_WOOL, " "));
                            ItemStack newCup = new ItemStack(cup);
                            ItemMeta newCupMeta = newCup.getItemMeta();
                            newCupMeta.setCustomModelData(2033);
                            newCupMeta.setDisplayName(ChatColor.WHITE + "Кружка");
                            newCup.setAmount(inventory.getItem(22).getAmount()-1);
                            newCupMeta.setLore(Collections.singletonList("Остаток: " + 0 + "/" + 27));
                            newCup.setItemMeta(newCupMeta);
                            ItemStack newDust = inventory.getItem(37);
                            newDust.setAmount(newDust.getAmount()-1);
                            inventory.setItem(37, newDust);
                            waitAsync.waitAsync(10, result4, inventory);
                            inventory.setItem(25, result4);


                        }
                        else {
                            player.sendMessage(ChatColor.RED+"Неправильный ингридиент!");

                        }
                    }
                    else {
                        player.sendMessage(ChatColor.RED + "Неправильный ингридиент!");

                    }


                }
                else {
                    player.sendMessage(ChatColor.RED + "Неправильная ёмкость для воды");
                }



            }
            else {
                player.sendMessage(ChatColor.RED + "Нет Воды");
            }

        }
        else {
            player.sendMessage(ChatColor.RED + "Нет Воды");
        }

    }

    public  void loadInventories() {
        for (String key : config1.getKeys(false)) {
            Location location = locationFromString(key);
            if (location != null) {
                ItemStack[] contents = ((List<ItemStack>) config1.get(key)).toArray(new ItemStack[0]);
                Inventory inventory = Bukkit.createInventory(null, 54, "§4Очиститель " + location.getBlockZ() + " " + location.getBlockY() + " " + location.getBlockZ());
                inventory.setContents(contents);
                pufferManager.pufferInventories.put(location, inventory);
            }
        }
    }

    public  void saveInventories() {
        for (Map.Entry<Location, Inventory> entry : pufferManager.pufferInventories.entrySet()) {
            Location location = entry.getKey();
            Inventory inventory = entry.getValue();
            config1.set(locationToString(location), inventory.getContents());
        }
        try {
            config1.save(configFile1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeInventoryFromFile(Location location) {
        config1.set(locationToString(location), null);
        try {
            config1.save(configFile1);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
    @EventHandler
    public void movementEvent(PlayerMoveEvent event){
       if(playerDataController.getNowPlayerState(event.getPlayer()) != -1){

            Player player = event.getPlayer();
            Location location = player.getLocation();
            location.setY(location.getY() + 1);
            Block block = location.getBlock();
            if(block.getType().equals(Material.AIR)){
                block.setType(Material.BARRIER);
                BarrierBlocks.put(location, block);




        }
    }
       if(playerDataController.getNowPlayerState(event.getPlayer()) == -1){
           for(Block block : BarrierBlocks.values()){
               block.setType(Material.AIR);
               BarrierBlocks.remove(block.getLocation(), block);
           }
       }
    }

    public boolean isSupersays() {
        return supersays;
    }

    public static Magic getPlugin() {
        return plugin;
    }
}