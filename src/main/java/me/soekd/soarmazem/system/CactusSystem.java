package me.soekd.soarmazem.system;

import com.intellectualcrafters.plot.object.Plot;
import me.soekd.soarmazem.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class CactusSystem {

    public static int pickupCactus(Player player, Plot plot) {
        if (!Main.storageList.containsKey(plot)) return 0;
        int amount = Main.storageList.get(plot).getCactuses();
        int a = 0;
        for (int i = 0; i < amount; i++) {
            if (player.getInventory().firstEmpty() != -1) {
                player.getInventory().addItem(new ItemStack(Material.CACTUS));
                Main.storageList.get(plot).removeCactuses();
                a++;
            }
        }
        return a;
    }

    public static int addOneHopperToStorage(Player player, Plot plot) {
        int slot = 0;
        int i = 0;
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null) if (item.getType() == Material.HOPPER) slot = i;
            i++;
        }
        if (player.getInventory().getItem(slot).getAmount() > 1) {
            player.getInventory().getItem(slot).setAmount(player.getInventory().getItem(slot).getAmount() - 1);
        } else player.getInventory().setItem(slot, new ItemStack(Material.AIR));
        Main.storageList.get(plot).addHoppers();
        return 1;
    }

    public static int addAllHoppersToStorage(Player player, Plot plot) {
        ArrayList<Integer> slots = new ArrayList<>();
        int i = 0;
        int amount = 0;
        for (ItemStack item : player.getInventory().getContents()) {
            if (item != null) {
                if (item.getType() == Material.HOPPER) {
                    amount += item.getAmount();
                    slots.add(i);
                }
            }
            i++;
        }
        for (int slot : slots) {
            player.getInventory().setItem(slot, new ItemStack(Material.AIR));
        }
        Main.storageList.get(plot).setHoppers(Main.storageList.get(plot).getHoppers() + amount);
        return amount;
    }

    public static double sellCactus(Player player, Plot plot) {
        if (!Main.storageList.containsKey(plot)) return 0.0D;
        double price = Main.storageList.get(plot).getMoneyValue();
        double more = 0.0D;
        for (int i = 1; i <= 100; i++) {
            if (player.hasPermission("soarmazem.sell." + i)) {
                more = (price * i) / 100;
            }
        }
        Main.storageList.get(plot).setCactuses(0);
        Main.getEconomy().depositPlayer(player, price + more);
        return more + price;
    }

    public static OfflinePlayer getOwner(Plot plot) {
        return Bukkit.getOfflinePlayer(plot.getOwners().stream().findFirst().get());
    }


}
