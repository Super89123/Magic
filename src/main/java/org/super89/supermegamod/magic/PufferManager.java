package org.super89.supermegamod.magic;

import com.sun.tools.javac.comp.Todo;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.super89.supermegamod.magic.Utils.ItemUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PufferManager implements Listener {

    public final Map<Location, Inventory> pufferInventories = new HashMap<>();
    public final Map<Location, Inventory> pufferUpgradeInventories = new HashMap<>();
    private FileConfiguration config;
    private File configFile;
    private Map<Location, Integer> pufferCookTime = new HashMap<>(); // Время приготовления для каждого очистителя
    InventoryWithCoolThings inventoryWithCoolThings = new InventoryWithCoolThings();

    public PufferManager(Magic plugin) throws IOException, InvalidConfigurationException {
        configFile = new File(plugin.getDataFolder(), "puffers.yml");
        config = new YamlConfiguration();
        config.load(configFile);
    }

    @EventHandler
    public void onNoteBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (block.getType() == Material.NOTE_BLOCK && pufferInventories.containsKey(block.getLocation())) {
            Inventory inventory = pufferInventories.remove(block.getLocation());
            removeInventoryFromFile(block.getLocation());
            pufferUpgradeInventories.remove(block.getLocation());
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

            inventory.setItem(23, ItemUtils.create(Material.GREEN_WOOL, "§aЗапуск"));
            inventory.setItem(53, ItemUtils.create(Material.GRAY_STAINED_GLASS_PANE, "§aУлучшения")); // Кнопка для меню улучшений
            for (int i = 0; i < 54; i++) {
                if (i != 10 && i != 12 && i != 21 && i != 30 && i != 37 && i != 22 && i != 25 && i != 53 && i != 23) {
                    inventory.setItem(i, ItemUtils.create(Material.PURPLE_STAINED_GLASS_PANE, " "));
                }
            }
            pufferInventories.put(block.getLocation(), inventory);
            createUpgradeInventory(block.getLocation()); // Создаем меню улучшений
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
        pufferUpgradeInventories.put(location, upgradeInventory);
    }

    // Метод для получения инвентаря очистителя
    public Inventory getPufferInventory(Block noteBlock) {
        return pufferInventories.get(noteBlock.getLocation());
    }

    // Метод для получения инвентаря улучшений
    public Inventory getUpgradeInventory(Block noteBlock) {
        return pufferUpgradeInventories.get(noteBlock.getLocation());
    }

    // Обработчик взаимодействия с очистителем
    @EventHandler
    public void onPlayerInteract1(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.NOTE_BLOCK) {
            Block noteBlock = event.getClickedBlock();
            Inventory inventory = getPufferInventory(noteBlock);
            if (inventory != null) {
                event.getPlayer().openInventory(inventory);
                event.setCancelled(true); // Отменяем стандартное взаимодействие
            } else {
                // Проверяем, не нажали ли на кнопку улучшений
                if (event.getClickedBlock().getLocation().getBlockX() == noteBlock.getLocation().getBlockX() && event.getClickedBlock().getLocation().getBlockY() == noteBlock.getLocation().getBlockY() && event.getClickedBlock().getLocation().getBlockZ() == noteBlock.getLocation().getBlockZ()) {
                    if (event.getHand() == EquipmentSlot.HAND && event.getClickedBlock() != null && event.getClickedBlock().getType() == Material.NOTE_BLOCK) {
                        Inventory upgradeInventory = getUpgradeInventory(noteBlock);
                        if (upgradeInventory != null) {
                            event.getPlayer().openInventory(upgradeInventory);
                            event.setCancelled(true); // Отменяем стандартное взаимодействие
                        }
                    }
                }
            }
        }
    }

    // Обработчик кликов в инвентаре очистителя
    @EventHandler
    public void invclick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getClickedInventory();
        if (pufferInventories.containsValue(event.getClickedInventory())) {
            Location location = getKeyByValue(pufferInventories, inventory);
            if (Objects.requireNonNull(event.getCurrentItem()).getType().equals(Material.RED_WOOL) || event.getCurrentItem().getType().equals(Material.PURPLE_STAINED_GLASS_PANE) || event.getCurrentItem().getType().equals(Material.LIME_WOOL)) {
                event.setCancelled(true);
                return;
            }
            // Логика взаимодействия с очистителем
            if (event.getCursor() != null) {
                if (event.getSlot() == 10 && (Objects.requireNonNull(event.getCursor()).getType().equals(Material.WATER_BUCKET))) {
                    if (Objects.requireNonNull(inventory.getItem(30)).getType().equals(Material.RED_WOOL)) {

                        inventory.setItem(10, new ItemStack(Material.BUCKET)); // Используем Material

                        inventory.setItem(30, ItemUtils.create(Material.LIME_WOOL, " "));
                        event.setCursor(new ItemStack(Material.AIR));
                        event.setCancelled(true); // Убираем отмену, чтобы предмет клался
                        startCooking(location, event.getCursor()); // Запускаем готовку
                        return;
                    }
                    if (Objects.requireNonNull(inventory.getItem(21)).getType().equals(Material.RED_WOOL) && Objects.requireNonNull(inventory.getItem(30)).getType().equals(Material.LIME_WOOL)) {

                        inventory.setItem(10, new ItemStack(Material.BUCKET)); // Используем Material

                        inventory.setItem(21, ItemUtils.create(Material.LIME_WOOL, " "));
                        event.setCursor(new ItemStack(Material.AIR));
                       event.setCancelled(true); // Убираем отмену, чтобы предмет клался
                        startCooking(location, event.getCursor()); // Запускаем готовку
                        return;
                    }
                    if (Objects.requireNonNull(inventory.getItem(12)).getType().equals(Material.RED_WOOL) && Objects.requireNonNull(inventory.getItem(30)).getType().equals(Material.LIME_WOOL) && Objects.requireNonNull(inventory.getItem(21)).getType().equals(Material.LIME_WOOL)) {

                        inventory.setItem(10, new ItemStack(Material.BUCKET)); // Используем Material

                        inventory.setItem(12, ItemUtils.create(Material.LIME_WOOL, " "));
                        event.setCursor(new ItemStack(Material.AIR));
                        event.setCancelled(true); // Убираем отмену, чтобы предмет клался
                        // Запускаем готовку
                        return;
                    }
                    pufferInventories.replace(location, inventory);
                }
                if(event.getSlot() != 10 && event.getSlot() != 37 && event.getSlot() != 25 && event.getSlot() != 22){
                    event.setCancelled(true);
                    return;
                }
                if(event.getSlot() == 23){
                    startCooking(location, event.getCurrentItem());
                    player.sendMessage("Ку ку братик, я это еще делаю");
                    event.setCancelled(true);
                    return;
                }


                if(event.getSlot() == 53){
                    player.openInventory(getUpgradeInventory(getKeyByValue(pufferInventories, inventory).getBlock()));
                    event.setCancelled(true);
                }

            }


            pufferInventories.replace(location, inventory);
        } else if (pufferUpgradeInventories.containsValue(event.getClickedInventory())) {
            // Логика взаимодействия с меню улучшений
            Location location = getKeyByValue(pufferUpgradeInventories, inventory);
            if (Objects.requireNonNull(event.getCurrentItem()).getType().equals(Material.LIGHT_GRAY_STAINED_GLASS_PANE)) {
                event.setCancelled(true);
                return;
            }
            if (event.getSlot() == 3) {
                if (event.getCursor().getType() == Material.IRON_NUGGET) {
                    event.setCursor(new ItemStack(Material.AIR));
                    // Устанавливаем ускорение в меню улучшений
                    inventory.setItem(3, ItemUtils.create(Material.IRON_NUGGET, "§aУскорение"));
                    pufferUpgradeInventories.replace(location, inventory);
                }
            }
            if (event.getSlot() == 4) {
                if (event.getCursor().getType() == Material.WATER_BUCKET) {
                    event.setCursor(new ItemStack(Material.AIR));
                    // Устанавливаем воронку в меню улучшений
                    inventory.setItem(4, ItemUtils.create(Material.WATER_BUCKET, "§aАвтоматическое наполнение"));
                    pufferUpgradeInventories.replace(location, inventory);
                }
            }
        }
    }

    // Метод для запуска процесса приготовления
    private void startCooking(Location location, ItemStack item) {
       ; // TODO
    }

    public  void loadInventories() {
        for (String key : config.getKeys(false)) {
            Location location = locationFromString(key);
            if (location != null) {
                ItemStack[] contents = ((List<ItemStack>) config.get(key)).toArray(new ItemStack[0]);
                Inventory inventory = Bukkit.createInventory(null, 54, "§4Очиститель " + location.getBlockZ() + " " + location.getBlockY() + " " + location.getBlockZ());
                inventory.setContents(contents);
                pufferInventories.put(location, inventory);
            }
        }
    }

    public  void saveInventories() {
        for (Map.Entry<Location, Inventory> entry : pufferInventories.entrySet()) {
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

    public void removeInventoryFromFile(Location location) {
        config.set(locationToString(location), null);
        try {
            config.save(configFile);
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
}