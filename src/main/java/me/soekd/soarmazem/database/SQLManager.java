package me.soekd.soarmazem.database;

import com.google.gson.Gson;
import com.intellectualcrafters.plot.object.Plot;
import me.soekd.soarmazem.Main;
import me.soekd.soarmazem.objects.Storage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLManager extends SQLConnection {

    public static boolean hasStorage(Plot plot) {
        PreparedStatement stm;
        try {
            stm = con.prepareStatement("SELECT * FROM `soarmazem` WHERE `plotID` = ?");
            stm.setString(1, plot.getId().toString());
            ResultSet rs = stm.executeQuery();
            return rs.next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            cs.sendMessage("§6§lSOArmazem  §cNao foi possível verificar corretamente se existe o terreno §7" + plot.getId().toString() + " §cno banco de dados.");
        }
        return false;
    }

    public static void insertStorage(Plot plot) {
        PreparedStatement stm;
        try {
            Gson json = new Gson();
            String jsonPadrao = json.toJson(new Storage(0, 0, true, false, false, Main.getInstance().getConfig().getInt("SellConfigurations.AllowedTimes")));
            stm = con.prepareStatement("INSERT INTO `soarmazem` (plotID,storage) VALUES (?,?)");
            stm.setString(1, plot.getId().toString());
            stm.setString(2, jsonPadrao);
            stm.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            cs.sendMessage("§6§lSOArmazem  §cNao foi possivel inserir corretamente o terreno §7" + plot.getId().toString() + " §cno banco de dados.");
        }
    }

    public static void saveStorage(Plot plot, Storage storage) {
        PreparedStatement stm;
        if (!hasStorage(plot)) insertStorage(plot);
        if (hasStorage(plot)) {
            try {
                stm = con.prepareStatement("UPDATE `soarmazem` SET storage = ? WHERE plotID = ?");
                Gson json = new Gson();
                String storageString = json.toJson(storage);
                stm.setString(1, storageString);
                stm.setString(2, plot.getId().toString());
                stm.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                cs.sendMessage("§6§lSOArmazem  §cNao foi possivel salvar corretamente o terreno §7" + plot.getId().toString() + " §cno banco de dados.");
            }
        }
    }

    public static Storage getStorage(Plot plot) {
        PreparedStatement stm;
        if (!hasStorage(plot)) insertStorage(plot);
        if (hasStorage(plot)) {
            try {
                stm = con.prepareStatement("SELECT * FROM `soarmazem` WHERE plotID = ?");
                stm.setString(1, plot.getId().toString());
                ResultSet rs = stm.executeQuery();
                Gson json = new Gson();
                if (rs.next()) {
                    return json.fromJson(rs.getString("storage"), Storage.class);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                cs.sendMessage("§6§lSOArmazem  §cNao foi possivel carregar o terreno §7" + plot.getId().toString() + " §cdo banco de dados.");
            }
        }
        return new Storage(0, 0, false, false, false, Main.getInstance().getConfig().getInt("SellConfigurations.AllowedTimes"));
    }

    public static void deletePlot(Plot plot) {
        PreparedStatement stm;
        if (!hasStorage(plot)) return;
        if (hasStorage(plot)) {
            try {
                stm = con.prepareStatement("DELETE FROM `soarmazem` WHERE plotID = ?");
                stm.setString(1, plot.getId().toString());
                stm.executeUpdate();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                cs.sendMessage("§6§lSOArmazem  §cNao foi possivel deletar o terreno §7" + plot.getId().toString() + " §cdo banco de dados.");
            }
        }

    }


}
