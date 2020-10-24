package me.soekd.soarmazem.tasks;

import me.soekd.soarmazem.Main;
import me.soekd.soarmazem.Messages;
import me.soekd.soarmazem.database.SQLManager;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.atomic.AtomicInteger;

public class SaveInventory {

    public static void saveAll() {
        new BukkitRunnable() {
            @Override
            public void run() {
                saveAllInventory();
            }
        }.runTaskTimer(Main.getInstance(), 20 * 60 * Main.getInstance().getConfig().getInt("SaveConfiguration.Time"), 20 * 60 * Main.getInstance().getConfig().getInt("SaveConfiguration.Time"));
    }

    public static void saveAllInventory() {
        AtomicInteger i = new AtomicInteger();
        Main.storageList.keySet().forEach(plot -> {
            SQLManager.saveStorage(plot, Main.storageList.get(plot));
            i.getAndIncrement();
        });
        Bukkit.getConsoleSender().sendMessage(Messages.formatMessage(Main.getInstance().getConfig().getString("SaveConfiguration.Message")).replace("{amount}", String.valueOf(i)));
    }

}
