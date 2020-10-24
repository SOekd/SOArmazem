package me.soekd.soarmazem.listeners;

import com.intellectualcrafters.plot.object.Plot;
import me.soekd.soarmazem.Main;
import me.soekd.soarmazem.inventory.OptionsInventory;
import me.soekd.soarmazem.inventory.StorageInventory;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickOptions implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (OptionsInventory.openedOptions.containsKey(player)) {
            if (event.getCurrentItem() == null) return;
            if (event.getCurrentItem().getType() == Material.AIR) return;
            if (event.getCurrentItem().getItemMeta().getDisplayName() == null) return;
            event.setCancelled(true);
            Plot plot = OptionsInventory.openedOptions.get(player);
            String funcao = OptionsInventory.optionsConfig.getString("Inventory." + event.getSlot() + ".Function");
            if(plot == null) return;
            if(funcao == null) return;
            switch (funcao) {
                case "trust":
                    Main.storageList.get(plot).setTrustedPermission(!Main.storageList.get(plot).isTrustedPermission());
                    player.closeInventory();
                    OptionsInventory.openInventory(player, plot);
                    player.playSound(player.getLocation(), Sound.valueOf(OptionsInventory.optionsConfig.getString("InventoryConfig.ClickSound")), 1.0f, 1.0f);
                    break;
                case "member":
                    Main.storageList.get(plot).setMemberPermission(!Main.storageList.get(plot).isMemberPermission());
                    player.closeInventory();
                    OptionsInventory.openInventory(player, plot);
                    player.playSound(player.getLocation(), Sound.valueOf(OptionsInventory.optionsConfig.getString("InventoryConfig.ClickSound")), 1.0f, 1.0f);
                    break;
                case "warn":
                    Main.storageList.get(plot).setWarnWhenFull(!Main.storageList.get(plot).isWarnWhenFull());
                    player.closeInventory();
                    OptionsInventory.openInventory(player, plot);
                    player.playSound(player.getLocation(), Sound.valueOf(OptionsInventory.optionsConfig.getString("InventoryConfig.ClickSound")), 1.0f, 1.0f);
                    break;
                case "return":
                    player.closeInventory();
                    StorageInventory.openInventory(player, plot);
                    player.playSound(player.getLocation(), Sound.valueOf(OptionsInventory.optionsConfig.getString("InventoryConfig.ReturnSound")), 1.0f, 1.0f);
                    break;
                default:
                    break;
            }
        }
    }

}
