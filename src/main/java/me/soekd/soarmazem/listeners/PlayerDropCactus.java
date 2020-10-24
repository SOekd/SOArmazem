package me.soekd.soarmazem.listeners;

import me.soekd.soarmazem.Main;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class PlayerDropCactus implements Listener {

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        if(event.getItemDrop().getItemStack().getType() == Material.CACTUS && Main.getInstance().getConfig().getBoolean("BlockPlayerDrop")) {
            event.getItemDrop().setMetadata("SOArmazem", new FixedMetadataValue(Main.getInstance(), "SOArmazem"));
        }
    }

}
