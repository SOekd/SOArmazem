package me.soekd.soarmazem.listeners;

import com.intellectualcrafters.plot.object.Plot;
import me.soekd.soarmazem.Main;
import me.soekd.soarmazem.Messages;
import me.soekd.soarmazem.inventory.OptionsInventory;
import me.soekd.soarmazem.inventory.StorageInventory;
import me.soekd.soarmazem.system.CactusSystem;
import me.soekd.soarmazem.system.Check;
import me.soekd.soarmazem.tasks.ResetSellTimes;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickStorage implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (StorageInventory.openedStorage.containsKey(player)) {
            if (event.getCurrentItem() == null) return;
            if (event.getCurrentItem().getType() == Material.AIR) return;
            if (event.getCurrentItem().getItemMeta().getDisplayName() == null) return;
            event.setCancelled(true);
            Plot plot = StorageInventory.openedStorage.get(player);
            String funcao = StorageInventory.storageConfig.getString("Inventory." + event.getSlot() + ".Function");
            if(plot == null) return;
            if(funcao == null) return;
            switch (funcao) {
                case "options":
                    if (plot.isOwner(player.getUniqueId()) || player.hasPermission("soarmazem.admin")) {
                        player.closeInventory();
                        OptionsInventory.openInventory(player, plot);
                        player.playSound(player.getLocation(), Sound.valueOf(StorageInventory.storageConfig.getString("InventoryConfig.OpenOptionsSound")), 1.0f, 1.0f);
                    } else {
                        player.closeInventory();
                        player.sendMessage(Messages.getSimpleMessage("OnlyOwner"));
                        player.playSound(player.getLocation(), Sound.valueOf(StorageInventory.storageConfig.getString("InventoryConfig.ErrorSound")), 1.0f, 1.0f);
                    }
                    break;
                case "pickup":
                    if (player.getInventory().firstEmpty() != -1) {
                        if (Main.storageList.get(plot).getCactuses() > 0) {
                            if (Check.hasPermission(player, plot)) {
                                player.sendMessage(Messages.getSimpleMessage("PickupCactus").replace("{amount}", String.valueOf(CactusSystem.pickupCactus(player, plot))));
                                player.playSound(player.getLocation(), Sound.valueOf(StorageInventory.storageConfig.getString("InventoryConfig.PickupSound")), 1.0f, 1.0f);
                            } else {
                                player.sendMessage(Messages.getSimpleMessage("NoPermPickup"));
                                player.playSound(player.getLocation(), Sound.valueOf(StorageInventory.storageConfig.getString("InventoryConfig.ErrorSound")), 1.0f, 1.0f);
                            }
                        } else {
                            player.sendMessage(Messages.getSimpleMessage("NoCactus"));
                            player.playSound(player.getLocation(), Sound.valueOf(StorageInventory.storageConfig.getString("InventoryConfig.ErrorSound")), 1.0f, 1.0f);
                        }
                    } else {
                        player.sendMessage(Messages.getSimpleMessage("InventoryFull"));
                        player.playSound(player.getLocation(), Sound.valueOf(StorageInventory.storageConfig.getString("InventoryConfig.ErrorSound")), 1.0f, 1.0f);
                    }
                    player.closeInventory();
                    break;
                case "sell":
                    if (Main.storageList.get(plot).getCactuses() > 0) {
                        if (Check.hasPermission(player, plot)) {
                            ResetSellTimes.checkPlot(plot);
                            if (Main.storageList.get(plot).canSell()) {
                                player.sendMessage(Messages.getSimpleMessage("SellCactus").replace("{money}", Main.getEconomy().format(CactusSystem.sellCactus(player, plot))));
                                Main.storageList.get(plot).removeTimesSell();
                                ResetSellTimes.addResetTime(plot);
                            } else {
                                if(ResetSellTimes.getMinutes(plot) > 0) {
                                    player.sendMessage(Messages.getSimpleMessage("SellResetTimeMinutes").replace("{seconds}", ResetSellTimes.formatSeconds(ResetSellTimes.getSeconds(plot))).replace("{minutes}", ResetSellTimes.formatMinutes(ResetSellTimes.getMinutes(plot))));
                                } else {
                                    player.sendMessage(Messages.getSimpleMessage("SellResetTimeSeconds").replace("{seconds}", ResetSellTimes.formatSeconds(ResetSellTimes.getSeconds(plot))));
                                }
                                player.playSound(player.getLocation(), Sound.valueOf(StorageInventory.storageConfig.getString("InventoryConfig.ErrorSound")), 1.0f, 1.0f);
                            }
                        } else {
                            player.sendMessage(Messages.getSimpleMessage("NoPermSell"));
                            player.playSound(player.getLocation(), Sound.valueOf(StorageInventory.storageConfig.getString("InventoryConfig.ErrorSound")), 1.0f, 1.0f);
                        }
                    } else {
                        player.sendMessage(Messages.getSimpleMessage("NoCactus"));
                        player.playSound(player.getLocation(), Sound.valueOf(StorageInventory.storageConfig.getString("InventoryConfig.ErrorSound")), 1.0f, 1.0f);
                    }
                    player.closeInventory();
                    break;
                case "addhopper":
                    if(Check.hasHopper(player)) {
                        if(Check.hasPermission(player, plot)) {
                            ClickType click = event.getClick();
                            if(click.isRightClick()) {
                                player.sendMessage(Messages.getSimpleMessage("AddHoppers").replace("{amount}", String.valueOf(CactusSystem.addAllHoppersToStorage(player, plot))));
                            } else {
                                player.sendMessage(Messages.getSimpleMessage("AddHoppers").replace("{amount}", String.valueOf(CactusSystem.addOneHopperToStorage(player, plot))));
                            }
                            player.closeInventory();
                            StorageInventory.openInventory(player, plot);
                            player.playSound(player.getLocation(), Sound.valueOf(StorageInventory.storageConfig.getString("InventoryConfig.AddHopperSound")), 1.0f, 1.0f);
                        } else {
                            player.sendMessage(Messages.getSimpleMessage("NoPermAddHopper"));
                            player.closeInventory();
                            player.playSound(player.getLocation(), Sound.valueOf(StorageInventory.storageConfig.getString("InventoryConfig.ErrorSound")), 1.0f, 1.0f);
                        }
                    } else {
                        player.sendMessage(Messages.getSimpleMessage("NoHopperInv"));
                        player.closeInventory();
                        player.playSound(player.getLocation(), Sound.valueOf(StorageInventory.storageConfig.getString("InventoryConfig.ErrorSound")), 1.0f, 1.0f);
                    }
                    break;
                case "return":
                    player.closeInventory();
                    StorageInventory.openInventory(player, plot);
                    break;
                default:
                    break;
            }
        }
    }

}
