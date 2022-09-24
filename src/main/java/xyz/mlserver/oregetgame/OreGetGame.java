package xyz.mlserver.oregetgame;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import xyz.mlserver.mc.util.CustomConfiguration;
import xyz.mlserver.oregetgame.commands.game;
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
        config.saveDefaultConfig();

        msgConfig = new CustomConfiguration(this, "lang/" + config.getConfig().getString("language-file"));
        msgConfig.saveDefaultConfig();

        MainAPI.load();

        getServer().getPluginManager().registerEvents(new BukkitUpdateInventoryEvent(), this);
        getCommand("game").setExecutor(new game());
    }

    @Override
    public void onDisable() {
        MainAPI.reset();
    }

    public static JavaPlugin getPlugin() { return plugin; }
}
