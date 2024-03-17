package org.super89.supermegamod.magic;

import org.super89.supermegamod.magic.Utils.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.Objects;

public class Prokachka implements Listener {
    Mana mana = new Mana(Magic.getPlugin());
    public Magic plugin;
    public Prokachka(Magic plugin){
        this.plugin = plugin;
    }
    public void ProkachkaGUI(Player player){
        String playerUUID = player.getUniqueId().toString();
        File playerDataFile = new File(plugin.getDataFolder(), "playerdata.yml");
        FileConfiguration playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);
        int a = playerDataConfig.getInt(playerUUID + "." + "prokachka");
        Inventory inv = Bukkit.createInventory(null, 54,"§4Дерево прокачки");
        for(int i = 0; i < 46; i +=9){inv.setItem(i, ItemUtils.create(Material.APPLE, 1, (byte) 1, "§6Прокачать ману", "§fТекущие количество очков", "§fпрокачки: "+ a, null,null));
        }

        inv.setItem(20, ItemUtils.create(Material.ARROW, 1, (byte) 1, "§6Прокачать урон", "§fТекущие количество очков", "прокачки: " + a, null,null));
        inv.setItem(30, ItemUtils.create(Material.SPONGE, 1, (byte) 1, "§6Прокачать скорость", "§fТекущие количество очков", "прокачки: " + a, null,null));
        player.openInventory(inv);
    }
    @EventHandler
    public void PlayerInteractEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getView().getTitle().equalsIgnoreCase("§4Дерево прокачки") && event.getCurrentItem() != null && !Objects.equals(event.getCurrentItem(), new ItemStack(Material.AIR))) {
            event.setCancelled(true);
            if (event.getCurrentItem().getType() == Material.APPLE) {
                int currentmana = mana.getMaxPlayerMana(player);
                if (mana.getNowPlayerProkachka(player) > 0) {

                    mana.setNowPlayerProkachka(player,mana.getNowPlayerProkachka(player)-1);
                    mana.setMaxPlayerMana(player, currentmana+10);


                    player.sendMessage("§bВы успешно повысили свое максимальное количество маны!");

                }
            }
        }
        if (17 == event.getSlot()){
            event.setCancelled(true);
            ProkachkaGUI(player);
        }


    }
}