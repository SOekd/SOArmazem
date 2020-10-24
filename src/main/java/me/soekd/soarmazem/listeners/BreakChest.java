package me.soekd.soarmazem.listeners;

import me.soekd.soarmazem.Main;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BreakChest implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        BlockState bs = event.getBlock().getState();
        if (bs instanceof Chest && Main.getInstance().getConfig().getStringList("EnabledWorlds").contains(event.getBlock().getLocation().getWorld().getName())) {
            Chest bau = (Chest) bs;
            Inventory inv = bau.getBlockInventory();
            int quant = 0;
            if (inv.contains(Material.CACTUS)) {
                int i = 0;
                for (ItemStack item : inv.getContents()) {
                    if (item != null) {
                        if (item.getType() == Material.CACTUS) {
                            quant += item.getAmount();
                            inv.setItem(i, new ItemStack(Material.AIR));
                        }
                    }
                    i++;
                }
            }
            while (quant > 0) {
                bs.getLocation().getWorld().dropItemNaturally(bs.getLocation(), new ItemStack(Material.CACTUS, 1));
                quant--;
            }
        }
    }

}
