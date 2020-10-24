package me.soekd.soarmazem.utils;

import me.soekd.soarmazem.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Update implements Listener {

    public static ConsoleCommandSender cs = Bukkit.getConsoleSender();
    public static String versao = "";
    public static String link = "";

    public static void checar() {
        try {
            URL url = new URL("https://raw.githubusercontent.com/SOekd/SOVersions/master/SOArmazem.txt");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                versao = inputLine.split("--")[0];
                link = inputLine.split("--")[1];
            }
            in.close();
            if(!Main.getInstance().getDescription().getVersion().equals(versao)) {
                cs.sendMessage(" ");
                cs.sendMessage("§6§lSOArmazem  §cHá uma nova versão do plugin disponível!");
                cs.sendMessage("§6§lSOArmazem  §cLink para baixar: " + link);
                cs.sendMessage(" ");
            }
        } catch (IOException e) {
            cs.sendMessage("§6§lSOArmazem  §cOcorreu um erro ao tentar verificar se há uma atualização.");
        }

    }

    @EventHandler
    public void aoEntrar(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        new BukkitRunnable() {
            @Override
            public void run() {
                if (p.isOp()) {
                    checar();
                    if (!Main.getInstance().getDescription().getVersion().equals(versao)) {
                        p.sendMessage(" ");
                        p.sendMessage("§6§lSOArmazem  §cHá uma nova versão do plugin disponível!");
                        p.sendMessage("§6§lSOArmazem  §cLink para baixar: " + link);
                        p.sendMessage(" ");
                    }
                }
            }
        }.runTaskLater(Main.getInstance(), 20L * 5);
    }

}
