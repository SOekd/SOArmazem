package me.soekd.soarmazem.commands;

import com.intellectualcrafters.plot.api.PlotAPI;
import com.intellectualcrafters.plot.object.Plot;
import me.soekd.soarmazem.Main;
import me.soekd.soarmazem.Messages;
import me.soekd.soarmazem.database.SQLManager;
import me.soekd.soarmazem.inventory.StorageInventory;
import me.soekd.soarmazem.system.Check;
import me.soekd.soarmazem.tasks.ResetSellTimes;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StorageCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {
        if (cmd.getName().equalsIgnoreCase("storage")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Messages.getSimpleMessage("NoConsole"));
                return false;
            }
            Player player = (Player) sender;
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
            if(!player.getWorld().getName().equals(Main.getInstance().getConfig().getString("PlotWorld"))) {
                player.sendMessage(Messages.getSimpleMessage("NoInPlotWorld"));
                return false;
            }
            if(!Main.storageList.containsKey(plot)) Main.storageList.put(plot, SQLManager.getStorage(plot));
            if (!Check.hasPermission(player, plot)) {
                player.sendMessage(Messages.getSimpleMessage("NoPermissionPlot"));
                return false;
            }
            ResetSellTimes.checkPlot(plot);
            StorageInventory.openInventory(player, plot);
            player.sendMessage(Messages.getSimpleMessage("OpenStorage"));
            return true;
        }
        return false;
    }

}
