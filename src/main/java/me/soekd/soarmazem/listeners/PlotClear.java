package me.soekd.soarmazem.listeners;


import com.intellectualcrafters.plot.object.Plot;
import com.plotsquared.bukkit.events.PlotClearEvent;
import me.soekd.soarmazem.Main;
import me.soekd.soarmazem.database.SQLManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlotClear implements Listener {

    @EventHandler
    public void onPlotClear(PlotClearEvent event) {
        if (Main.getInstance().getConfig().getBoolean("ClearWhenDispose")) {
            Plot plot = event.getPlot();
            Main.storageList.remove(plot);
            SQLManager.deletePlot(plot);
            Bukkit.getConsoleSender().sendMessage("§6§lSOArmazem  §cO armazem do terreno §7" + plot.getId().toString() + " §cfoi deletado do banco de dados.");
        }
    }

}
