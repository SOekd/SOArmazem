package me.soekd.soarmazem.listeners;

import me.soekd.soarmazem.inventory.OptionsInventory;
import me.soekd.soarmazem.inventory.StorageInventory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryClose implements Listener {

    @EventHandler
    public void onCloseInventory(InventoryCloseEvent event) {
        StorageInventory.openedStorage.remove(event.getPlayer());
        OptionsInventory.openedOptions.remove(event.getPlayer());
    }

}
