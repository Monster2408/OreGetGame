package xyz.mlserver.oregetgame;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import xyz.mlserver.mc.util.CustomConfiguration;
import xyz.mlserver.oregetgame.listeners.BukkitUpdateInventoryEvent;
import xyz.mlserver.oregetgame.utils.MainAPI;

public final class OreGetGame extends JavaPlugin {

    private static JavaPlugin plugin;
    public static CustomConfiguration config, msgConfig;

    public static Scoreboard scoreboard;
    @Override
    public void onEnable() {
        plugin = this;

        config = new CustomConfiguration(this);
        msgConfig = new CustomConfiguration(this, "message.yml");

        MainAPI.load();

        scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

        getServer().getPluginManager().registerEvents(new BukkitUpdateInventoryEvent(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static JavaPlugin getPlugin() {
        return plugin;
    }
}
