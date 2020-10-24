package me.soekd.soarmazem.listeners;

import com.plotsquared.bukkit.events.PlayerLeavePlotEvent;
import me.soekd.soarmazem.inventory.OptionsInventory;
import me.soekd.soarmazem.inventory.StorageInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlotLeave implements Listener {

    @EventHandler
    public void onPlotLeave(PlayerLeavePlotEvent event) {
        Player player = event.getPlayer();
        if(OptionsInventory.openedOptions.containsKey(player) || StorageInventory.openedStorage.containsKey(player)) {
            player.closeInventory();
            player.sendMessage("{prefix} &cO seu armazem foi fechado pois você saiu do terreno.");
        }
    }

}
