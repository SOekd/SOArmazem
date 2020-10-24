package me.soekd.soarmazem.inventory;

import com.intellectualcrafters.plot.object.Plot;
import me.soekd.soarmazem.Main;
import me.soekd.soarmazem.Messages;
import me.soekd.soarmazem.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StorageInventory {

    public static HashMap<Player, Plot> openedStorage = new HashMap<>();

    public static Config storageConfig = new Config("storage.yml");

    public static void openInventory(Player player, Plot plot) {
        if (!Main.storageList.containsKey(plot)) return;
        Inventory inventory = Bukkit.createInventory(null, storageConfig.getInt("InventoryConfig.Size"), Messages.formatMessage(storageConfig.getString("InventoryConfig.Name"), plot));
        for (String key : storageConfig.getConfig().getConfigurationSection("Inventory").getKeys(false)) {
            ItemStack item = new ItemStack(Material.getMaterial(storageConfig.getInt("Inventory." + key + ".ID")), 1, (short) storageConfig.getInt("Inventory." + key + ".Data"));
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(Messages.formatMessage(storageConfig.getString("Inventory." + key + ".Name"), plot));
            List<String> lore = new ArrayList<>();
            for (String loreFormat : storageConfig.getStringList("Inventory." + key + ".Lore")) {
                lore.add(Messages.formatMessage(loreFormat, plot));
            }
            itemMeta.setLore(lore);
            item.setItemMeta(itemMeta);
            inventory.setItem(Integer.parseInt(key), item);
        }
        player.openInventory(inventory);
        player.playSound(player.getLocation(), Sound.valueOf(storageConfig.getString("InventoryConfig.OpenSound")), 1.0f, 1.0f);
        openedStorage.put(player, plot);
    }

}
