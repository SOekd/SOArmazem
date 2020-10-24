package me.soekd.soarmazem;

import com.intellectualcrafters.plot.object.Plot;
import me.soekd.soarmazem.objects.Storage;
import me.soekd.soarmazem.system.CactusSystem;
import me.soekd.soarmazem.utils.Config;
import org.bukkit.ChatColor;

import java.util.*;

public class Messages {

    public static Config messagesConfig = new Config("messages.yml");

    private static HashMap<String, String> simpleMessage = new HashMap<>();
    private static HashMap<String, List<String>> multiMessage = new HashMap<>();

    public static void loadSimpleMessages() {
        simpleMessage.clear();
        simpleMessage.put("NoConsole", messagesConfig.getString("NoConsole"));
        simpleMessage.put("NoPlot", messagesConfig.getString("NoPlot"));
        simpleMessage.put("NoOwner", messagesConfig.getString("NoOwner"));
        simpleMessage.put("NoPermissionPlot", messagesConfig.getString("NoPermissionPlot"));
        simpleMessage.put("OpenStorage", messagesConfig.getString("OpenStorage"));
        simpleMessage.put("StorageFull", messagesConfig.getString("StorageFull"));
        simpleMessage.put("OnlyOwner", messagesConfig.getString("OnlyOwner"));
        simpleMessage.put("PickupCactus", messagesConfig.getString("PickupCactus"));
        simpleMessage.put("NoPermPickup", messagesConfig.getString("NoPermPickup"));
        simpleMessage.put("NoCactus", messagesConfig.getString("NoCactus"));
        simpleMessage.put("InventoryFull", messagesConfig.getString("InventoryFull"));
        simpleMessage.put("SellCactus", messagesConfig.getString("SellCactus"));
        simpleMessage.put("SellResetTimeMinutes", messagesConfig.getString("SellResetTimeMinutes"));
        simpleMessage.put("SellResetTimeSeconds", messagesConfig.getString("SellResetTimeSeconds"));
        simpleMessage.put("NoPermSell", messagesConfig.getString("NoPermSell"));
        simpleMessage.put("AddHoppers", messagesConfig.getString("AddHoppers"));
        simpleMessage.put("NoPermAddHopper", messagesConfig.getString("NoPermAddHopper"));
        simpleMessage.put("NoHopperInv", messagesConfig.getString("NoHopperInv"));
        simpleMessage.put("ErrorNumberFormat", messagesConfig.getString("ErrorNumberFormat"));
        simpleMessage.put("NoPermission", messagesConfig.getString("NoPermission"));
        simpleMessage.put("SetCactus", messagesConfig.getString("SetCactus"));
        simpleMessage.put("ResetPlot", messagesConfig.getString("ResetPlot"));
        simpleMessage.put("Reload", messagesConfig.getString("Reload"));
        simpleMessage.put("IncorrectSetHopper", messagesConfig.getString("IncorrectSetHopper"));
        simpleMessage.put("SetHopper", messagesConfig.getString("SetHopper"));
        simpleMessage.put("IncorrectSetCactus", messagesConfig.getString("IncorrectSetCactus"));
        simpleMessage.put("NoInPlotWorld", messagesConfig.getString("NoInPlotWorld"));

    }

    public static void loadMultiMessages() {
        multiMessage.clear();
        multiMessage.put("Help", messagesConfig.getStringList("Help"));
    }

    public static String getSimpleMessage(String path) {
        if(!simpleMessage.containsKey(path)) {
            return formatMessage("§6§lSOArmazem  §cOcorreu um erro ao carregar uma mensagem. Verifique se o arquivo messages.yml contem a linha §7" + path + "§c!");
        }
        return formatMessage(simpleMessage.get(path));
    }

    public static String getSimpleMessage(String path, Plot plot) {
        if(!simpleMessage.containsKey(path)) {
            return formatMessage("§6§lSOArmazem  §cOcorreu um erro ao carregar uma mensagem. Verifique se o arquivo messages.yml contem a linha §7" + path + "§c!");
        }
        return formatMessage(simpleMessage.get(path), plot);
    }

    public static List<String> getMultiMessage(String path) {
        if(!multiMessage.containsKey(path)) {
            return Collections.singletonList(formatMessage("§6§lSOArmazem  §cOcorreu um erro ao carregar uma mensagem. Verifique se o arquivo messages.yml contem a linha §7" + path + "§c!"));
        }
        List<String> retornar = new ArrayList<>();
        for(String msg: multiMessage.get(path)) {
            retornar.add(formatMessage(msg));
        }
        return retornar;
    }

    public static String formatMessage(String msg, Plot plot) {
        Storage storage = Main.storageList.get(plot);
        return ChatColor.translateAlternateColorCodes('&', msg
        .replace("{plot}", plot.getId().toString())
        .replace("{owner}", CactusSystem.getOwner(plot).getName())
        .replace("{cactus}", String.valueOf(storage.getCactuses()))
        .replace("{hopper}", String.valueOf(storage.getHoppers()))
        .replace("{space}", String.valueOf(storage.getStorageLimit()))
        .replace("{trusted}", storage.isTrustedPermission()? Main.getInstance().getConfig().getString("Options.Trust.Allowed"):Main.getInstance().getConfig().getString("Options.Trust.Denied"))
        .replace("{member}", storage.isMemberPermission()? Main.getInstance().getConfig().getString("Options.Member.Allowed"):Main.getInstance().getConfig().getString("Options.Member.Denied"))
        .replace("{warn}", storage.isWarnWhenFull()? Main.getInstance().getConfig().getString("Options.Warn.Allowed"):Main.getInstance().getConfig().getString("Options.Warn.Denied"))
        .replace("{selltimes}", String.valueOf(storage.getTimesSell()))
        .replace("{cactusmoney}", String.valueOf(Main.getEconomy().format(storage.getMoneyValue())))
        .replace("{prefix}", Main.getInstance().getConfig().getString("Prefix")));
    }

    public static String formatMessage(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg
        .replace("{prefix}", Main.getInstance().getConfig().getString("Prefix")));
    }

}
