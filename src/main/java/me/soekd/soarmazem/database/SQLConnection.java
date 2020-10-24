package me.soekd.soarmazem.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.soekd.soarmazem.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.PluginManager;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLConnection {

    public static Connection con = null;
    protected static ConsoleCommandSender cs = Bukkit.getConsoleSender();
    protected static PluginManager pm = Bukkit.getPluginManager();

    public static void openConnecionMySQL() {
        if (Main.getInstance().getConfig().getBoolean("MySQL.Enable")) {
            String host = Main.getInstance().getConfig().getString("MySQL.Host");
            int port = Main.getInstance().getConfig().getInt("MySQL.Port");
            String database = Main.getInstance().getConfig().getString("MySQL.Database");
            String username = Main.getInstance().getConfig().getString("MySQL.Username");
            String password = Main.getInstance().getConfig().getString("MySQL.Password");
            String url = "jdbc:mysql://" + host + ":" + port + "/" + database;
            HikariConfig config = new HikariConfig();
            config.setPoolName(Main.getInstance().getConfig().getString("MySQL.AdvancedConfiguration.PoolName"));
            config.setJdbcUrl(url);
            config.setUsername(username);
            config.setPassword(password);
            config.setDriverClassName("com.mysql.jdbc.Driver");
            config.addDataSourceProperty("cachePrepStmts", Main.getInstance().getConfig().getBoolean("MySQL.AdvancedConfiguration.CachePrepStmts"));
            config.addDataSourceProperty("prepStmtCacheSize", Main.getInstance().getConfig().getInt("MySQL.AdvancedConfiguration.PrepStmtCacheSize"));
            config.addDataSourceProperty("prepStmtCacheSqlLimit", Main.getInstance().getConfig().getInt("MySQL.AdvancedConfiguration.PrepStmtCacheSqlLimit"));
            config.addDataSourceProperty("useServerPrepStmts", Main.getInstance().getConfig().getBoolean("MySQL.AdvancedConfiguration.UseServerPrepStmts"));
            config.addDataSourceProperty("useLocalSessionState", Main.getInstance().getConfig().getBoolean("MySQL.AdvancedConfiguration.UseLocalSessionState"));
            config.addDataSourceProperty("rewriteBatchedStatements", Main.getInstance().getConfig().getBoolean("MySQL.AdvancedConfiguration.RewriteBatchedStatements"));
            config.addDataSourceProperty("cacheResultSetMetadata", Main.getInstance().getConfig().getBoolean("MySQL.AdvancedConfiguration.CacheResultSetMetadata"));
            config.addDataSourceProperty("cacheServerConfiguration", Main.getInstance().getConfig().getBoolean("MySQL.AdvancedConfiguration.CacheServerConfiguration"));
            config.addDataSourceProperty("elideSetAutoCommits", Main.getInstance().getConfig().getBoolean("MySQL.AdvancedConfiguration.ElideSetAutoCommits"));
            config.addDataSourceProperty("maintainTimeStats", Main.getInstance().getConfig().getBoolean("MySQL.AdvancedConfiguration.MaintainTimeStats"));
            HikariDataSource ds = new HikariDataSource(config);
            try {
                con = ds.getConnection();
                openTable();
                cs.sendMessage("§6§lSOArmazem  §aA conexao com o banco de dados MySQL foi estabelecida com sucesso.");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                cs.sendMessage("§6§lSOArmazem  §cNao foi possivel abrir uma conexao com o banco de dados MySQL. Tentando abrir conexao SQLite.");
                openConnectionSQLite();
            }

        } else {
            openConnectionSQLite();
        }
    }

    public static void openConnectionSQLite() {
        File file = new File(Main.getInstance().getDataFolder(), "database.db");
        String url = "jdbc:sqlite:" + file;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection(url);
            openTable();
            cs.sendMessage("§6§lSOArmazem  §aA conexao com o banco de dados SQLite foi estabelecida com sucesso.");
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void openTable() {
        PreparedStatement stm;
        try {
            stm = con.prepareStatement("CREATE TABLE IF NOT EXISTS `soarmazem` (plotID VARCHAR(15), storage TEXT, PRIMARY KEY (plotID))");
            stm.executeUpdate();
            cs.sendMessage("§6§lSOArmazem  §aA tabela do banco de dados foi carregada/criada com sucesso.");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            cs.sendMessage("§6§lSOArmazem  §cNao foi possivel criar/carregar a tabela do banco de dados. Desligando o plugin para evitar problemas.");
            pm.disablePlugin(Main.getInstance());
        }
    }

    public static void closeTable() {
        if(con != null) {
            try {
                con.close();
                con = null;
                cs.sendMessage("§6§lSOArmazem  §cA tabela do banco de dados foi fechada corretamente.");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
                cs.sendMessage("§6§lSOArmazem  §cNao foi possivel fechar a tabela do banco de dados corretamente.");
            }
        }
    }


}
