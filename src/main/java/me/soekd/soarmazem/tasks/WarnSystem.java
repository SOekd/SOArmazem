package me.soekd.soarmazem.tasks;

import com.intellectualcrafters.plot.object.Plot;
import me.soekd.soarmazem.Main;
import me.soekd.soarmazem.Messages;
import me.soekd.soarmazem.system.CactusSystem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class WarnSystem {

    public static ArrayList<Plot> plotToWarn = new ArrayList<>();

    public static void startWarn(Plot plot) {
        if (plotToWarn.contains(plot)) return;
        plotToWarn.add(plot);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (Main.storageList.get(plot).isFull() && plotToWarn.contains(plot) && Main.storageList.get(plot).isWarnWhenFull()) {
                    Player player = Bukkit.getPlayer(CactusSystem.getOwner(plot).getUniqueId());
                    if (player == null) {
                        cancel();
                        plotToWarn.remove(plot);
                    } else player.sendMessage(Messages.getSimpleMessage("StorageFull", plot));
                } else {
                    cancel();
                    plotToWarn.remove(plot);
                }
            }
        }.runTaskTimer(Main.getInstance(), 0L, 20 * Main.getInstance().getConfig().getInt("WarnTime"));
    }

}
