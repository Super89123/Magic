package org.super89.supermegamod.magic;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
    private final FileConfiguration config;
    private final File configFile;
    private final Map<Location, Integer> pufferCookTime = new HashMap<>(); // Время приготовления для каждого очистителя
    InventoryWithCoolThings inventoryWithCoolThings = new InventoryWithCoolThings();
    WaitAsync waitAsync = new WaitAsync(Bukkit.getScheduler());

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
            inventory.setItem(10, ItemUtils.create(Material.LIGHT_GRAY_STAINED_GLASS_PANE, " "));

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
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK && Objects.requireNonNull(event.getClickedBlock()).getType() == Material.NOTE_BLOCK) {
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
            if (!event.getCursor().getType().equals(Material.AIR)) {
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
                        event.setCancelled(true); // Убираем отмену, чтобы предмет клался
                        // Запускаем готовку
                        return;
                    }
                    pufferInventories.replace(location, inventory);
                }
                if(event.getSlot() == 10 && (event.getCursor().getType().equals(Material.BUCKET) || event.getCurrentItem().getType().equals(Material.BUCKET))){
                    player.getInventory().addItem(new ItemStack(Material.BUCKET));
                    event.setCursor(new ItemStack(Material.AIR));
                    event.setCurrentItem(new ItemStack(Material.AIR));
                    assert inventory != null;
                    inventory.setItem(10, new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE));
                    event.setCancelled(true);
                }
                if(event.getSlot() != 10 && event.getSlot() != 37 && event.getSlot() != 25 && event.getSlot() != 22 && event.getSlot() != 23 &&  event.getSlot() != 53){
                    event.setCancelled(true);
                    return;
                }
                if(event.getSlot() == 23){
                    startCooking(inventory, player);
                    player.sendMessage("Ку ку братик, я это еще делаю");
                    event.setCancelled(true);
                    return;
                }


                if(event.getSlot() == 53){
                    player.openInventory(getUpgradeInventory(Objects.requireNonNull(getKeyByValue(pufferInventories, inventory)).getBlock()));
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
        result3ItemMeta.setCustomModelData(2034);
        result3ItemMeta.setDisplayName(ChatColor.WHITE + "Кружка с водой");
        result3.setItemMeta(result2ItemMeta);

        ItemStack result4 = new ItemStack(Material.POTION);
        ItemMeta result4ItemMeta = result1.getItemMeta();
        result4ItemMeta.setCustomModelData(2035);
        result4ItemMeta.setDisplayName(ChatColor.WHITE + "Кружка с водой+");
        result4.setItemMeta(result2ItemMeta);

        ItemStack cup = new ItemStack(Material.GLASS_BOTTLE);
        ItemMeta cupmeta = cup.getItemMeta();
        cupmeta.setCustomModelData(2033);
        cupmeta.setDisplayName(ChatColor.WHITE + "Кружка");
        cup.setItemMeta(cupmeta);

        if(inventory.getItem(12).getType().equals(Material.LIME_WOOL) || inventory.getItem(21).getType().equals(Material.LIME_WOOL) ||  inventory.getItem(30).getType().equals(Material.LIME_WOOL)){
            if(inventory.getItem(12).getType().equals(Material.LIME_WOOL)){
                if(inventory.getItem(22).getType().equals(Material.GLASS_BOTTLE)){
                    if(inventory.getItem(37).getType().equals(Material.PAPER) && inventory.getItem(37).hasItemMeta() && inventory.getItem(37).getItemMeta().hasCustomModelData()){
                        if(inventory.getItem(37).getItemMeta().getCustomModelData() == 2029){

                            inventory.setItem(12, ItemUtils.create(Material.RED_WOOL, " "));
                            inventory.setItem(22, ItemUtils.create(Material.GLASS_BOTTLE, "", inventory.getItem(22).getAmount()-1, (byte) 0));
                            ItemStack newDust = inventory.getItem(37);
                            newDust.setAmount(newDust.getAmount()-1);
                            inventory.setItem(37, newDust);
                            waitAsync.waitAsync(1);
                            inventory.setItem(25, result1);
                        } 
                        else if (inventory.getItem(37).getItemMeta().getCustomModelData() == 2032) {
                            inventory.setItem(12, ItemUtils.create(Material.RED_WOOL, " "));
                            inventory.setItem(22, ItemUtils.create(Material.GLASS_BOTTLE, "", inventory.getItem(22).getAmount()-1, (byte) 0));
                            ItemStack newDust = inventory.getItem(37);
                            newDust.setAmount(newDust.getAmount()-1);
                            inventory.setItem(37, newDust);
                            waitAsync.waitAsync(1);
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
                        if(inventory.getItem(37).getItemMeta().getCustomModelData() == 2029){

                            inventory.setItem(12, ItemUtils.create(Material.RED_WOOL, " "));
                            ItemStack newCup = new ItemStack(cup);
                            newCup.setAmount(inventory.getItem(22).getAmount()-1);
                            inventory.setItem(22, newCup);
                            ItemStack newDust = inventory.getItem(37);
                            newDust.setAmount(newDust.getAmount()-1);
                            inventory.setItem(37, newDust);
                            waitAsync.waitAsync(1);
                            inventory.setItem(25, result3);
                        }
                        else if (inventory.getItem(37).getItemMeta().getCustomModelData() == 2032) {
                            inventory.setItem(12, ItemUtils.create(Material.RED_WOOL, " "));
                            ItemStack newCup = new ItemStack(cup);
                            newCup.setAmount(inventory.getItem(22).getAmount()-1);
                            inventory.setItem(22, newCup);
                            ItemStack newDust = inventory.getItem(37);
                            newDust.setAmount(newDust.getAmount()-1);
                            inventory.setItem(37, newDust);
                            waitAsync.waitAsync(1);
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
                        if(inventory.getItem(37).getItemMeta().getCustomModelData() == 2029){

                            inventory.setItem(12, ItemUtils.create(Material.RED_WOOL, " "));
                            inventory.setItem(22, ItemUtils.create(Material.GLASS_BOTTLE, "", inventory.getItem(22).getAmount()-1, (byte) 0));
                            ItemStack newDust = inventory.getItem(37);
                            newDust.setAmount(newDust.getAmount()-1);
                            inventory.setItem(37, newDust);
                            waitAsync.waitAsync(1);
                            inventory.setItem(25, result1);
                        }
                        else if (inventory.getItem(37).getItemMeta().getCustomModelData() == 2032) {
                            inventory.setItem(21, ItemUtils.create(Material.RED_WOOL, " "));
                            inventory.setItem(22, ItemUtils.create(Material.GLASS_BOTTLE, "", inventory.getItem(22).getAmount()-1, (byte) 0));
                            ItemStack newDust = inventory.getItem(37);
                            newDust.setAmount(newDust.getAmount()-1);
                            inventory.setItem(37, newDust);
                            waitAsync.waitAsync(1);
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
                        if(inventory.getItem(37).getItemMeta().getCustomModelData() == 2029){

                            inventory.setItem(21, ItemUtils.create(Material.RED_WOOL, " "));
                            ItemStack newCup = new ItemStack(cup);
                            newCup.setAmount(inventory.getItem(22).getAmount()-1);
                            inventory.setItem(22, newCup);
                            ItemStack newDust = inventory.getItem(37);
                            newDust.setAmount(newDust.getAmount()-1);
                            inventory.setItem(37, newDust);
                            waitAsync.waitAsync(1);
                            inventory.setItem(25, result3);
                        }
                        else if (inventory.getItem(37).getItemMeta().getCustomModelData() == 2032) {
                            inventory.setItem(21, ItemUtils.create(Material.RED_WOOL, " "));
                            ItemStack newCup = new ItemStack(cup);
                            newCup.setAmount(inventory.getItem(22).getAmount()-1);
                            inventory.setItem(22, newCup);
                            ItemStack newDust = inventory.getItem(37);
                            newDust.setAmount(newDust.getAmount()-1);
                            inventory.setItem(37, newDust);
                            waitAsync.waitAsync(1);
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
                        if(inventory.getItem(37).getItemMeta().getCustomModelData() == 2029){

                            inventory.setItem(30, ItemUtils.create(Material.RED_WOOL, " "));
                            inventory.setItem(22, ItemUtils.create(Material.GLASS_BOTTLE, "", inventory.getItem(22).getAmount()-1, (byte) 0));
                            ItemStack newDust = inventory.getItem(37);
                            newDust.setAmount(newDust.getAmount()-1);
                            inventory.setItem(37, newDust);
                            waitAsync.waitAsync(1);
                            inventory.setItem(25, result1);
                        }
                        else if (inventory.getItem(37).getItemMeta().getCustomModelData() == 2032) {
                            inventory.setItem(30, ItemUtils.create(Material.RED_WOOL, " "));
                            inventory.setItem(22, ItemUtils.create(Material.GLASS_BOTTLE, "", inventory.getItem(22).getAmount()-1, (byte) 0));
                            ItemStack newDust = inventory.getItem(37);
                            newDust.setAmount(newDust.getAmount()-1);
                            inventory.setItem(37, newDust);
                            waitAsync.waitAsync(1);
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
                        if(inventory.getItem(37).getItemMeta().getCustomModelData() == 2029){

                            inventory.setItem(30, ItemUtils.create(Material.RED_WOOL, " "));
                            ItemStack newCup = new ItemStack(cup);
                            newCup.setAmount(inventory.getItem(22).getAmount()-1);
                            inventory.setItem(22, newCup);
                            ItemStack newDust = inventory.getItem(37);
                            newDust.setAmount(newDust.getAmount()-1);
                            inventory.setItem(37, newDust);
                            waitAsync.waitAsync(1);
                            inventory.setItem(25, result3);
                        }
                        else if (inventory.getItem(37).getItemMeta().getCustomModelData() == 2032) {
                            inventory.setItem(30, ItemUtils.create(Material.RED_WOOL, " "));
                            ItemStack newCup = new ItemStack(cup);
                            newCup.setAmount(inventory.getItem(22).getAmount()-1);
                            inventory.setItem(22, newCup);
                            ItemStack newDust = inventory.getItem(37);
                            newDust.setAmount(newDust.getAmount()-1);
                            inventory.setItem(37, newDust);
                            waitAsync.waitAsync(1);
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



