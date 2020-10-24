package me.soekd.soarmazem.listeners;

import com.intellectualcrafters.plot.api.PlotAPI;
import com.intellectualcrafters.plot.object.Plot;
import me.soekd.soarmazem.Main;
import me.soekd.soarmazem.database.SQLManager;
import me.soekd.soarmazem.tasks.WarnSystem;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;

public class ItemSpawn implements Listener {

    @EventHandler
    public void onItemSpawn(ItemSpawnEvent event) {
        if(event.getEntity().getItemStack().getType() == Material.CACTUS && Main.getInstance().getConfig().getString("PlotWorld").contains(event.getLocation().getWorld().getName()) && !event.getEntity().hasMetadata("SOArmazem")) {
            event.setCancelled(true);
            PlotAPI plotAPI = new PlotAPI();
            Plot plot = plotAPI.getPlot(event.getLocation());
            if(plot == null) return;
            if(!plot.hasOwner()) return;
            if(!Main.storageList.containsKey(plot)) Main.storageList.put(plot, SQLManager.getStorage(plot));
            if(Main.storageList.get(plot).isFull()){
                if(Main.storageList.get(plot).isWarnWhenFull()) WarnSystem.startWarn(plot);
                return;
            }
            Main.storageList.get(plot).addCactuses();
        }
    }

}
