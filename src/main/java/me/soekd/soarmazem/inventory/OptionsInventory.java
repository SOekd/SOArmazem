package me.soekd.soarmazem.inventory;

import com.intellectualcrafters.plot.object.Plot;
import me.soekd.soarmazem.Main;
import me.soekd.soarmazem.Messages;
import me.soekd.soarmazem.objects.Storage;
import me.soekd.soarmazem.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OptionsInventory {

    public static HashMap<Player, Plot> openedOptions = new HashMap<>();

    public static Config optionsConfig = new Config("options.yml");

    public static void openInventory(Player player, Plot plot) {
        if(!Main.storageList.containsKey(plot)) return;
        Inventory inventory = Bukkit.createInventory(null, optionsConfig.getInt("InventoryConfig.Size"), Messages.formatMessage(optionsConfig.getString("InventoryConfig.Name"), plot));
        for(String key: optionsConfig.getConfig().getConfigurationSection("Inventory").getKeys(false)) {
            ItemStack item = new ItemStack(Material.getMaterial(optionsConfig.getInt("Inventory." + key + ".ID")), 1, (short) optionsConfig.getInt("Inventory." + key + ".Data"));
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setDisplayName(Messages.formatMessage(optionsConfig.getString("Inventory." + key + ".Name"), plot));
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            List<String> lore = new ArrayList<>();
            for(String loreFormat:  optionsConfig.getStringList("Inventory." + key + ".Lore")) {
                lore.add(Messages.formatMessage(loreFormat, plot));
            }
            Storage storage = Main.storageList.get(plot);
            String function = optionsConfig.getString("Inventory." + key + ".Function");
            if(function.equals("trust")) if(storage.isTrustedPermission()) itemMeta.addEnchant(Enchantment.LURE, 1, false);
            if(function.equals("member")) if(storage.isMemberPermission()) itemMeta.addEnchant(Enchantment.LURE, 1, false);
            if(function.equals("warn")) if(storage.isWarnWhenFull()) itemMeta.addEnchant(Enchantment.LURE, 1, false);
            itemMeta.setLore(lore);
            item.setItemMeta(itemMeta);
            inventory.setItem(Integer.parseInt(key), item);
        }
        player.openInventory(inventory);
        openedOptions.put(player, plot);
    }


}
