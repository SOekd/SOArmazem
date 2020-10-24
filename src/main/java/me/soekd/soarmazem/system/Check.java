package me.soekd.soarmazem.system;

import com.intellectualcrafters.plot.object.Plot;
import me.soekd.soarmazem.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Check {

    public static boolean hasPermission(Player player, Plot plot) {
        if(!Main.storageList.containsKey(plot)) return false;
        if(plot.getOwners().contains(player.getUniqueId())) return true;
        if(player.hasPermission("soarmazem.admin")) return true;
        if(plot.getTrusted().contains(player.getUniqueId()) && Main.storageList.get(plot).isTrustedPermission())  return true;
        return plot.getMembers().contains(player.getUniqueId()) && Main.storageList.get(plot).isMemberPermission();
    }

    public static boolean hasHopper(Player player) {
        return player.getInventory().contains(Material.HOPPER, 1);
    }

}
