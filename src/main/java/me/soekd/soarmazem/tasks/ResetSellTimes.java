package me.soekd.soarmazem.tasks;

import com.intellectualcrafters.plot.object.Plot;
import me.soekd.soarmazem.Main;

import java.util.Date;
import java.util.HashMap;

public class ResetSellTimes {

    public static HashMap<Plot, Date> timeReset = new HashMap<>();

    public static void addResetTime(Plot plot) {
        if (!timeReset.containsKey(plot)) {
            Date dataAtual = new Date();
            dataAtual.setSeconds(dataAtual.getSeconds() + Main.getInstance().getConfig().getInt("SellConfigurations.ResetTime"));
            timeReset.put(plot, dataAtual);
        }
    }

    public static void checkPlot(Plot plot) {
        if (!Main.storageList.containsKey(plot)) return;
        if (timeReset.containsKey(plot)) {
            Date expiration = timeReset.get(plot);
            Date now = new Date();
            if (now.after(expiration)) {
                Main.storageList.get(plot).setTimesSell(Main.getInstance().getConfig().getInt("SellConfigurations.AllowedTimes"));
                timeReset.remove(plot);
            }
        } else {
            Main.storageList.get(plot).setTimesSell(Main.getInstance().getConfig().getInt("SellConfigurations.AllowedTimes"));
        }
    }

    public static int getSeconds(Plot plot) {
        if (!timeReset.containsKey(plot)) return 0;
        Date expiration = timeReset.get(plot);
        Date now = new Date();
        long variation = expiration.getTime() - now.getTime();
        return (int) (variation / 1000 % 60);
    }

    public static int getMinutes(Plot plot) {
        if (!timeReset.containsKey(plot)) return 0;
        Date expiration = timeReset.get(plot);
        Date now = new Date();
        long variation = expiration.getTime() - now.getTime();
        return (int) (variation / 60000 % 60);
    }

    public static String formatMinutes(int minutes) {
        if (minutes == 1)
            return Main.getInstance().getConfig().getString("Time.Minute").replace("{minute}", String.valueOf(minutes));
        else
            return Main.getInstance().getConfig().getString("Time.Minutes").replace("{minute}", String.valueOf(minutes));
    }

    public static String formatSeconds(int seconds) {
        if (seconds == 1)
            return Main.getInstance().getConfig().getString("Time.Second").replace("{second}", String.valueOf(seconds));
        else
            return Main.getInstance().getConfig().getString("Time.Seconds").replace("{second}", String.valueOf(seconds));
    }

}
