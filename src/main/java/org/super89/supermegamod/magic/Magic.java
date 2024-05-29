package org.super89.supermegamod.magic;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.damage.DamageEffect;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
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
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.super89.supermegamod.magic.Utils.ItemUtils;

import java.io.File;
import java.io.IOException;
import java.util.*;

public final class Magic extends JavaPlugin implements Listener {


    PlayerDataController playerDataController = new PlayerDataController(this);
    ReflectBook reflectBook = new ReflectBook();
    PufferManager pufferManager = new PufferManager();
    private static Magic plugin;
    private FileConfiguration config;
    private File configFile;

    public  Map<Location, Block> BarrierBlocks = new HashMap<>();






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
        getServer().getPluginManager().registerEvents(new SonicBook(), this);

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
        container.set(new NamespacedKey("your-plugin-namespace", "custom-model-data"), PersistentDataType.INTEGER, 10000001);
        paperMeta.setCustomModelData(4000);
        paper.setAmount(5);
        paper.setItemMeta(paperMeta);

        StonecuttingRecipe recipe123 = new StonecuttingRecipe(NamespacedKey.minecraft("nether_star_to_paper"), paper, Material.NETHER_STAR);

        Bukkit.addRecipe(recipe123);

        ItemStack rabbit_foot = new ItemStack(Material.RABBIT_FOOT);

        ItemMeta rabrMeta = rabbit_foot.getItemMeta();
        assert rabrMeta != null;
        PersistentDataContainer container1 = rabrMeta.getPersistentDataContainer();
        container1.set(new NamespacedKey("your-plugin-namespace", "custom-model-data"), PersistentDataType.INTEGER, 2025);
        rabrMeta.setCustomModelData(2025);
        rabbit_foot.setItemMeta(rabrMeta);
        rabbit_foot.setAmount(5);

        StonecuttingRecipe recipe1234 = new StonecuttingRecipe(NamespacedKey.minecraft("lapis_lazuli_to_rabbit_foot"), rabbit_foot, Material.LAPIS_LAZULI);

        Bukkit.addRecipe(recipe1234);


        configFile = new File(getDataFolder(), "puffers.yml");
        config = YamlConfiguration.loadConfiguration(configFile);
        loadInventories();


        plugin = this;



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
                        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 50, 4, false, false, false));
                    }
                    if (item.hasItemMeta() && item.getItemMeta().hasCustomModelData() && item.getItemMeta().getCustomModelData() == 1005) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 50, 4, false, false, false));
                    }
                    TextComponent actionbarMessage = Component.text("Мана: " + nowmana + "/" + maxmana + "       |       " + playerDataController.calculatePlayerThirst(player), Style.style(TextColor.color(59, 223,235), TextDecoration.BOLD));



                    player.sendActionBar(actionbarMessage);



                    if (playerDataController.getNowPlayerThrist(player) == 0) {
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
                        player.sendMessage(ChatColor.RED + "Эффект шипов истек!");
                    }
                }
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (playerDataController.getNowPlayerState(player) != -1) {
                        for (Block block1 : BarrierBlocks.values()) {
                            BarrierBlocks.remove(block1.getLocation(), block1);
                            block1.setType(Material.AIR);


                        }
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
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 2, false, false, false));
                        if (block.getType() != Material.BARRIER) {
                            block.setType(Material.BARRIER);
                            BarrierBlocks.put(location, block);
                        }


                    }


                }
            }
        }.runTaskTimer(plugin, 0, 10);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (Bukkit.getOnlinePlayers().isEmpty()) {
                    return;
                }
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (playerDataController.getNowPlayerThrist(player) > 0) {
                        playerDataController.setNowPlayerThrist(player, playerDataController.getNowPlayerThrist(player) - 1);
                    }
                }
            }
        }.runTaskTimer(this, 0, 20 * 60);
    }
    @Override
    public void onDisable(){
        saveInventories();
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();


        if (player.getInventory().getItemInMainHand().getType() == Material.BOOK && event.getAction().name().contains("RIGHT_CLICK") && player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 1000 && player.getInventory().getItemInMainHand().getItemMeta().hasCustomModelData() && playerDataController.getNowPlayerMana(player)>=10) {
            playerDataController.setNowPlayerMana(player, playerDataController.getNowPlayerMana(player)-10);

            Location targetLocation = player.getTargetBlock(null, 100).getLocation();
            createParticleCube(targetLocation, 5, 5, 5, Particle.SCULK_SOUL);
            freezeAndDamagePlayersInParticles(targetLocation, 5, 5, 5, 3, 0.1, player.getWorld());
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
                Inventory inventory = Bukkit.createInventory(null, 54, "§4Очиститель " + location.getBlockZ() +" "+ location.getBlockY() +" "+ location.getBlockZ());
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

            Inventory inventory = pufferManager.pufferInventories.remove(block.getLocation());
            removeInventoryFromFile(block.getLocation());

            Location location = block.getLocation();
            for (ItemStack item : inventory.getContents()) {
                if ((item != null) && !item.getType().equals(Material.LIME_WOOL) && !item.getType().equals(Material.RED_WOOL) && !item.getType().equals(Material.PURPLE_STAINED_GLASS_PANE) && !item.getType().equals(Material.LIGHT_GRAY_STAINED_GLASS_PANE)) {
                    location.getWorld().dropItemNaturally(location, item);
                }
            }
        }
    }
    @EventHandler
    public void onNoteBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlockPlaced();
        if (block.getType() == Material.NOTE_BLOCK) {
            Inventory inventory = Bukkit.createInventory(null, 54, "§4Очиститель " + block.getLocation().getBlockX() + " " + block.getLocation().getBlockY() + " " + block.getLocation().getBlockZ());
            inventory.setItem(12, ItemUtils.create(Material.RED_WOOL, " "));
            inventory.setItem(21, ItemUtils.create(Material.RED_WOOL, " "));
            inventory.setItem(30, ItemUtils.create(Material.RED_WOOL, " "));
            inventory.setItem(10, ItemUtils.create(Material.LIGHT_GRAY_STAINED_GLASS_PANE, " "));
            inventory.setItem(37, ItemUtils.create(Material.LIGHT_GRAY_STAINED_GLASS_PANE, " "));
            inventory.setItem(22, ItemUtils.create(Material.LIGHT_GRAY_STAINED_GLASS_PANE, " "));
            for(int i = 0; i < 54; i++){

                if(i != 10 && i != 12 && i != 21 && i != 30 && i != 37 && i != 22 && i != 25){
                    inventory.setItem(i, ItemUtils.create(Material.PURPLE_STAINED_GLASS_PANE, " "));

                }
            }

            pufferManager.pufferInventories.put(block.getLocation(), inventory);
        }
    }
    public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }



    public Inventory getPufferInventory(Block noteBlock) {
        return pufferManager.pufferInventories.get(noteBlock.getLocation());
    }
    @EventHandler
    public void onPlayerInteract1(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.NOTE_BLOCK) {
            Block noteBlock =  event.getClickedBlock();
            Inventory inventory = getPufferInventory(noteBlock);
            event.getPlayer().openInventory(inventory);




            event.setCancelled(true);
        }
    }
    @EventHandler
    public void invclick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();

        Inventory inventory = event.getClickedInventory();
        if (pufferManager.pufferInventories.containsValue(event.getClickedInventory())) {
            Location location = getKeyByValue(pufferManager.pufferInventories, inventory);
            if (Objects.requireNonNull(event.getCurrentItem()).getType().equals(Material.RED_WOOL) || event.getCurrentItem().getType().equals(Material.PURPLE_STAINED_GLASS_PANE) || event.getCurrentItem().getType().equals(Material.GREEN_WOOL) || event.getCurrentItem().getType().equals(Material.LIGHT_GRAY_STAINED_GLASS_PANE)) {
                event.setCancelled(true);





            }
            int slot = 10;
            if(event.getCursor() != null){
                if (event.getSlot() == slot && Objects.requireNonNull(event.getCursor()).getType().equals(Material.WATER_BUCKET)) {
                    assert inventory != null;
                    if (Objects.requireNonNull(inventory.getItem(30)).getType().equals(Material.RED_WOOL)) {
                        inventory.setItem(10, new ItemStack(Material.BUCKET));
                        inventory.setItem(30, ItemUtils.create(Material.LIME_WOOL, " "));
                        event.setCursor(new ItemStack(Material.AIR));
                        event.setCancelled(true);
                        return;
                    }
                    if (Objects.requireNonNull(inventory.getItem(21)).getType().equals(Material.RED_WOOL) && Objects.requireNonNull(inventory.getItem(30)).getType().equals(Material.LIME_WOOL)) {
                        inventory.setItem(10, new ItemStack(Material.BUCKET));
                        inventory.setItem(21, ItemUtils.create(Material.LIME_WOOL, " "));
                        event.setCursor(new ItemStack(Material.AIR));
                        event.setCancelled(true);
                        return;
                    }
                    if (Objects.requireNonNull(inventory.getItem(12)).getType().equals(Material.RED_WOOL) && Objects.requireNonNull(inventory.getItem(30)).getType().equals(Material.LIME_WOOL) && Objects.requireNonNull(inventory.getItem(21)).getType().equals(Material.LIME_WOOL)) {
                        inventory.setItem(10, new ItemStack(Material.BUCKET));
                        inventory.setItem(12, ItemUtils.create(Material.LIME_WOOL, " "));
                        event.setCursor(new ItemStack(Material.AIR));
                        event.setCancelled(true);
                        return;

                    }

                    pufferManager.pufferInventories.replace(location, inventory);

                }

                if(!event.getCurrentItem().getType().equals(Material.LIGHT_GRAY_STAINED_GLASS) && !event.getCurrentItem().getType().equals(Material.PURPLE_STAINED_GLASS) && !event.getCurrentItem().getType().equals(Material.LIME_WOOL) && !event.getCurrentItem().getType().equals(Material.RED_WOOL) && !event.getCursor().getType().equals(Material.LIME_WOOL) && !event.getCursor().getType().equals(Material.LIGHT_GRAY_STAINED_GLASS_PANE) && !event.getCursor().getType().equals(Material.PURPLE_STAINED_GLASS) && !event.getCursor().getType().equals(Material.RED_WOOL)){
                    if(event.getSlot() == 22 || event.getSlot() == 10 || event.getSlot() == 37) {
                        if(!event.getCurrentItem().getType().equals(Material.LIGHT_GRAY_STAINED_GLASS)) {
                            player.getInventory().addItem(ItemUtils.create(event.getCurrentItem().getType(), ""));
                            inventory.setItem(event.getSlot(), ItemUtils.create(Material.LIGHT_GRAY_STAINED_GLASS_PANE, " "));
                            event.setCursor(new ItemStack(Material.AIR));
                            event.setCurrentItem(ItemUtils.create(Material.LIGHT_GRAY_STAINED_GLASS_PANE, " "));
                        }
                    }
                    event.setCancelled(true);

                }
                if(event.getSlot() == 22 || event.getSlot() == 10 || event.getSlot() == 37){
                    if(event.getSlot() == 37 && Objects.requireNonNull(event.getCursor()).getType().equals(Material.AMETHYST_SHARD) && (inventory.getItem(30).getType().equals(Material.LIME_WOOL) || Objects.requireNonNull(inventory.getItem(21)).getType().equals(Material.LIME_WOOL) || Objects.requireNonNull(inventory.getItem(12)).getType().equals(Material.LIME_WOOL))){
                        ItemStack itemStack = event.getCursor();
                        itemStack.setAmount(event.getCursor().getAmount()-1);
                        inventory.setItem(25, new ItemStack(Material.POTION));
                        inventory.setItem(event.getSlot(), itemStack);
                        event.setCancelled(true);



                    }
                }

            }



        }

    }
    @EventHandler
    public void damageEvent(EntityDamageEvent event){
        if(event.getEntity() instanceof Player){
            Player player = (Player) event.getEntity();
            if (player.getHealth()-event.getDamage() > 2) {
            return;
            }
            if (player.getHealth()-event.getDamage() <= 2 && playerDataController.getNowPlayerState(player) == -1){
                playerDataController.setNowPlayerPkm(player, 9);
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

    }


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
        if(shooter.getInventory().getItemInMainHand().hasItemMeta() && shooter.getInventory().getItemInMainHand().getType().equals(Material.CROSSBOW) && shooter.getInventory().getItemInMainHand().getItemMeta().hasCustomModelData() && shooter.getInventory().getItemInMainHand().getItemMeta().getCustomModelData() == 1449){

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

        if (effectType.contains(PotionEffectType.HEALTH_BOOST) || effectType.contains(PotionEffectType.HEAL)) {
            for (LivingEntity entity : event.getAffectedEntities()) {
                if (entity instanceof Player) {
                    Player player = (Player) entity;
                    playerDataController.setNowPlayerPkm(player, -1);

                }
            }
        }
    }

    public static Magic getPlugin() {
        return plugin;
    }
}



