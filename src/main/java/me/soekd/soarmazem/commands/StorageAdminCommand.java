package me.soekd.soarmazem.commands;

import com.intellectualcrafters.plot.api.PlotAPI;
import com.intellectualcrafters.plot.object.Plot;
import me.soekd.soarmazem.Main;
import me.soekd.soarmazem.Messages;
import me.soekd.soarmazem.database.SQLManager;
import me.soekd.soarmazem.inventory.OptionsInventory;
import me.soekd.soarmazem.inventory.StorageInventory;
import me.soekd.soarmazem.tasks.ResetSellTimes;
import me.soekd.soarmazem.tasks.WarnSystem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StorageAdminCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {
        if (cmd.getName().equalsIgnoreCase("storageadmin")) {
            if (!sender.hasPermission("soarmazem.admin")) {
                sender.sendMessage(Messages.getSimpleMessage("NoPermission"));
                return false;
            }
            if (!(sender instanceof Player)) {
                sender.sendMessage(Messages.getSimpleMessage("NoConsole"));
                return false;
            }
            Player player = (Player) sender;
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("setcacto") || args[0].equalsIgnoreCase("setcactos") || args[0].equalsIgnoreCase("setcactus") || args[0].equalsIgnoreCase("setcactuses")) {
                    if (args.length == 2) {
                        PlotAPI plotAPI = new PlotAPI();
                        Plot plot = plotAPI.getPlot(player.getLocation());
                        if (plot == null) {
                            player.sendMessage(Messages.getSimpleMessage("NoPlot"));
                            return false;
                        }
                        if (!plot.hasOwner()) {
                            player.sendMessage(Messages.getSimpleMessage("NoOwner"));
                            return false;
                        }
                        if (!player.getWorld().getName().equals(Main.getInstance().getConfig().getString("PlotWorld"))) {
                            player.sendMessage(Messages.getSimpleMessage("NoInPlotWorld"));
                            return false;
                        }
                        try {
                            int amount = Integer.parseInt(args[1]);
                            if (!Main.storageList.containsKey(plot))
                                Main.storageList.put(plot, SQLManager.getStorage(plot));
                            Main.storageList.get(plot).setCactuses(amount);
                            player.sendMessage(Messages.getSimpleMessage("SetCactus", plot).replace("{amount}", String.valueOf(amount)));
                            return true;
                        } catch (NumberFormatException exception) {
                            player.sendMessage(Messages.getSimpleMessage("ErrorNumberFormat"));
                            return false;
                        }
                    } else {
                        player.sendMessage(Messages.getSimpleMessage("IncorrectSetCactus"));
                        return false;
                    }
                }
                if (args[0].equalsIgnoreCase("setfunil") || args[0].equalsIgnoreCase("setfunis") || args[0].equalsIgnoreCase("sethopper") || args[0].equalsIgnoreCase("sethoppers")) {
                    if (args.length == 2) {
                        PlotAPI plotAPI = new PlotAPI();
                        Plot plot = plotAPI.getPlot(player.getLocation());
                        if (plot == null) {
                            player.sendMessage(Messages.getSimpleMessage("NoPlot"));
                            return false;
                        }
                        if (!plot.hasOwner()) {
                            player.sendMessage(Messages.getSimpleMessage("NoOwner"));
                            return false;
                        }
                        if (!player.getWorld().getName().equals(Main.getInstance().getConfig().getString("PlotWorld"))) {
                            player.sendMessage(Messages.getSimpleMessage("NoInPlotWorld"));
                            return false;
                        }
                        try {
                            int amount = Integer.parseInt(args[1]);
                            if (!Main.storageList.containsKey(plot)) Main.storageList.put(plot, SQLManager.getStorage(plot));
                            Main.storageList.get(plot).setHoppers(amount);
                            player.sendMessage(Messages.getSimpleMessage("SetHopper", plot).replace("{amount}", String.valueOf(amount)));
                            return true;
                        } catch (NumberFormatException exception) {
                            player.sendMessage(Messages.getSimpleMessage("ErrorNumberFormat"));
                            return false;
                        }
                    } else {
                        player.sendMessage(Messages.getSimpleMessage("IncorrectSetHopper"));
                        return false;
                    }
                }
                if (args[0].equalsIgnoreCase("reset") || args[0].equalsIgnoreCase("resetar")) {
                    PlotAPI plotAPI = new PlotAPI();
                    Plot plot = plotAPI.getPlot(player.getLocation());
                    if (plot == null) {
                        player.sendMessage(Messages.getSimpleMessage("NoPlot"));
                        return false;
                    }
                    if (!plot.hasOwner()) {
                        player.sendMessage(Messages.getSimpleMessage("NoOwner"));
                        return false;
                    }
                    if (!player.getWorld().getName().equals(Main.getInstance().getConfig().getString("PlotWorld"))) {
                        player.sendMessage(Messages.getSimpleMessage("NoInPlotWorld"));
                        return false;
                    }
                    SQLManager.deletePlot(plot);
                    ResetSellTimes.timeReset.remove(plot);
                    WarnSystem.plotToWarn.remove(plot);
                    Main.storageList.remove(plot);
                    if(!Main.storageList.containsKey(plot)) Main.storageList.put(plot, SQLManager.getStorage(plot));
                    player.sendMessage(Messages.getSimpleMessage("ResetPlot", plot));
                    return true;
                }
                if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("recarregar")) {
                    Main.getInstance().saveDefaultConfig();
                    Main.getInstance().reloadConfig();
                    StorageInventory.storageConfig.saveDefaultConfig();
                    StorageInventory.storageConfig.reloadConfig();
                    OptionsInventory.optionsConfig.saveDefaultConfig();
                    OptionsInventory.optionsConfig.reloadConfig();
                    Messages.messagesConfig.saveDefaultConfig();
                    Messages.messagesConfig.reloadConfig();
                    Messages.loadSimpleMessages();
                    player.sendMessage(Messages.getSimpleMessage("Reload"));
                    return true;
                }
            }
            Messages.getMultiMessage("Help").forEach(player::sendMessage);
        }
        return false;
    }

}
