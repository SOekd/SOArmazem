package me.soekd.soarmazem;

import com.intellectualcrafters.plot.object.Plot;
import me.soekd.soarmazem.commands.StorageAdminCommand;
import me.soekd.soarmazem.commands.StorageCommand;
import me.soekd.soarmazem.database.SQLConnection;
import me.soekd.soarmazem.inventory.OptionsInventory;
import me.soekd.soarmazem.inventory.StorageInventory;
import me.soekd.soarmazem.listeners.*;
import me.soekd.soarmazem.objects.Storage;
import me.soekd.soarmazem.tasks.SaveInventory;
import me.soekd.soarmazem.utils.Update;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class Main extends JavaPlugin {

    private static Main instance;
    public static Main getInstance() {
        return instance;
    }

    private static Economy econ = null;
    private final ConsoleCommandSender cs = Bukkit.getConsoleSender();
    private final PluginManager pm = Bukkit.getPluginManager();

    public static HashMap<Plot, Storage> storageList = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        reloadConfig();
        StorageInventory.storageConfig.saveDefaultConfig();
        StorageInventory.storageConfig.reloadConfig();
        OptionsInventory.optionsConfig.saveDefaultConfig();
        OptionsInventory.optionsConfig.reloadConfig();
        Messages.messagesConfig.saveDefaultConfig();
        Messages.messagesConfig.reloadConfig();
        Messages.loadSimpleMessages();
        Messages.loadMultiMessages();
        SQLConnection.openConnecionMySQL();
        if(pm.getPlugin("PlotSquared") == null) {
            cs.sendMessage("§6§lSOArmazem  §cNao foi possivel hookar o PlotSquared. Desligando o plugin");
            pm.disablePlugin(this);
            return;
        }
        if(!setupEconomy()) {
            cs.sendMessage("§6§lSOArmazem  §cNao foi possivel encontrar o Vault. Desligando o plugin.");
            pm.disablePlugin(this);
            return;
        }
        registerCommands();
        registerEvents();
        cs.sendMessage(" ");
        cs.sendMessage("§6§lSOArmazem  §eO plugin foi ligado com sucesso.");
        cs.sendMessage("§6§lSOArmazem  §eAutor: SOekd");
        cs.sendMessage("§6§lSOArmazem  §eDiscord: SOekd#3055");
        cs.sendMessage("§6§lSOArmazem  §eVersion: " + this.getDescription().getVersion());
        cs.sendMessage(" ");
        Update.checar();
        SaveInventory.saveAll();
    }

    @Override
    public void onDisable() {
        SaveInventory.saveAllInventory();
        SQLConnection.closeTable();
        cs.sendMessage("§6§lSOArmazem  §cO plugin foi desligado com sucesso.");
    }

    public void registerCommands() {
        getCommand("storage").setExecutor(new StorageCommand());
        getCommand("storageadmin").setExecutor(new StorageAdminCommand());
        cs.sendMessage("§6§lSOArmazem  §aOs comandos foram registrados com sucesso.");
    }

    public void registerEvents() {
        pm.registerEvents(new BreakChest(), this);
        pm.registerEvents(new InventoryClickStorage(), this);
        pm.registerEvents(new InventoryClickOptions(), this);
        pm.registerEvents(new InventoryClose(), this);
        pm.registerEvents(new ItemSpawn(), this);
        pm.registerEvents(new PlayerDropCactus(), this);
        pm.registerEvents(new PlotClear(), this);
        pm.registerEvents(new PlotLeave(), this);
        cs.sendMessage("§6§lSOArmazem  §aOs eventos foram registrados com sucesso.");
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static Economy getEconomy() {
        return econ;
    }

}
