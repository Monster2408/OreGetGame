package xyz.mlserver.oregetgame.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import xyz.mlserver.java.Log;
import xyz.mlserver.mc.util.Color;
import xyz.mlserver.oregetgame.OreGetGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainAPI {

    public static int CoalOptionPoint, IronOptionPoint, GoldOptionPoint, DiamondOptionPoint, EmeraldOptionPoint, LapisLazuliOptionPoint, RedstoneOptionPoint, CopperOptionPoint;

    private static int CoalPoint, IronPoint, GoldPoint, DiamondPoint, EmeraldPoint, LapisLazuliPoint, RedstonePoint, CopperPoint, AllPoint;
    public static int GoalPoint;

    public static String CoalPointName, IronPointName, GoldPointName, DiamondPointName, EmeraldPointName, LapisLazuliPointName, RedstonePointName, CopperPointName, AllPointName, ClearPointName;
    private static String GameStartMsg, GameEndMsg, GameClearTitle, BossBarText;

    public static String StartDescription, EndDescription, ResetDescription, HelpDescription, IsGameNow, NotGameNow, SetPointDescription, PleaseSetPoint, WrongArgument, SetPoint;

    private static List<String> startCmds, endCmds;

    private static Objective sidebar;
    private static BossBar bossBar;

    public static boolean GameNow = false;

    public static void load() {
        CoalOptionPoint = OreGetGame.config.getConfig().getInt("points.coal", 1);
        IronOptionPoint = OreGetGame.config.getConfig().getInt("points.iron", 3);
        GoldOptionPoint = OreGetGame.config.getConfig().getInt("points.gold", 5);
        DiamondOptionPoint = OreGetGame.config.getConfig().getInt("points.diamond", 8);
        EmeraldOptionPoint = OreGetGame.config.getConfig().getInt("points.emerald", 9);
        LapisLazuliOptionPoint = OreGetGame.config.getConfig().getInt("points.lapis_lazuli", 2);
        RedstoneOptionPoint = OreGetGame.config.getConfig().getInt("points.redstone", 2);
        CopperOptionPoint = OreGetGame.config.getConfig().getInt("points.copper", 3);

        GoalPoint = OreGetGame.config.getConfig().getInt("goal", 100);

        CoalPointName =
                OreGetGame.msgConfig.getConfig().getString("Sidebar.CoalName", "??????");
        IronPointName =
                OreGetGame.msgConfig.getConfig().getString("Sidebar.IronIngotName", "??????????????????");
        GoldPointName =
                OreGetGame.msgConfig.getConfig().getString("Sidebar.GoldIngotName", "??????????????????");
        CopperPointName =
                OreGetGame.msgConfig.getConfig().getString("Sidebar.CopperName", "??????????????????");
        DiamondPointName =
                OreGetGame.msgConfig.getConfig().getString("Sidebar.DiamondName", "??????????????????");
        EmeraldPointName =
                OreGetGame.msgConfig.getConfig().getString("Sidebar.EmeraldName", "???????????????");
        LapisLazuliPointName =
                OreGetGame.msgConfig.getConfig().getString("Sidebar.LapisLazuliName", "??????????????????");
        RedstonePointName =
                OreGetGame.msgConfig.getConfig().getString("Sidebar.RedstoneName", "?????????????????????");
        AllPointName =
                OreGetGame.msgConfig.getConfig().getString("Sidebar.AllPointName", "??? ?????????????????? ???");
        GameStartMsg = Color.replaceColorCode(OreGetGame.msgConfig.getConfig().getString("GameStartMessage", "&6?????????????????????????????????"));
        GameEndMsg = Color.replaceColorCode(OreGetGame.msgConfig.getConfig().getString("GameEndMessage", "&6?????????????????????????????????"));
        GameClearTitle = Color.replaceColorCode(OreGetGame.msgConfig.getConfig().getString("GameClearTitle", "&e&l?????????????????????"));
        BossBarText = Color.replaceColorCode(OreGetGame.msgConfig.getConfig().getString("BossBarText", "????????????"));
        ClearPointName = Color.replaceColorCode(OreGetGame.msgConfig.getConfig().getString("ClearPoint", "?????????????????????"));

        StartDescription = OreGetGame.msgConfig.getConfig().getString("Command.StartDescription", "??????????????????");
        EndDescription = OreGetGame.msgConfig.getConfig().getString("Command.EndDescription", "??????????????????");
        ResetDescription = OreGetGame.msgConfig.getConfig().getString("Command.ResetDescription", "?????????????????????");
        SetPointDescription = OreGetGame.msgConfig.getConfig().getString("Command.SetPointDescription", "??????????????????????????????");
        HelpDescription = OreGetGame.msgConfig.getConfig().getString("Command.HelpDescription", "?????????????????????");
        IsGameNow = Color.replaceColorCode(OreGetGame.msgConfig.getConfig().getString("Command.IsGameNow", "&c???????????????????????????????????????"));
        NotGameNow = Color.replaceColorCode(OreGetGame.msgConfig.getConfig().getString("Command.NotGameNow", "&c??????????????????????????????????????????"));
        PleaseSetPoint = Color.replaceColorCode(OreGetGame.msgConfig.getConfig().getString("Command.PleaseSetPoint", "&c??????????????????????????????????????????"));
        WrongArgument = Color.replaceColorCode(OreGetGame.msgConfig.getConfig().getString("Command.WrongArgument", "&c?????????????????????????????????"));
        SetPoint = Color.replaceColorCode(OreGetGame.msgConfig.getConfig().getString("Command.SetPoint", "&6%%NAME%%??????????????????%%POINT%%pt????????????????????????"));

        startCmds = new ArrayList<>();
        endCmds = new ArrayList<>();
        if (OreGetGame.config.getConfig().get("commands.start") != null) {
            for (String cmd : OreGetGame.config.getConfig().getStringList("commands.start")) {
                if (cmd.startsWith("/")) cmd = cmd.substring(1);
                startCmds.add(cmd);
            }
        }
        if (OreGetGame.config.getConfig().get("commands.end") != null) {
            for (String cmd : OreGetGame.config.getConfig().getStringList("commands.end")) {
                if (cmd.startsWith("/")) cmd = cmd.substring(1);
                endCmds.add(cmd);
            }
        }
    }

    public static boolean start() {
        if (GameNow) return false;
        GameNow = true;

        Log.debug(startCmds.toString());
        if (startCmds.size() > 0) for (String cmd : startCmds) Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), cmd);

        Bukkit.broadcastMessage(ChatColor.GOLD + GameStartMsg);

        if (OreGetGame.scoreboard == null) OreGetGame.scoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).getNewScoreboard();

        if (OreGetGame.scoreboard.getObjective("sidebar") == null) {
            sidebar = OreGetGame.scoreboard.registerNewObjective("sidebar","dummy", "points");
        } else {
            sidebar = OreGetGame.scoreboard.getObjective("sidebar");
        }
        bossBar = Bukkit.createBossBar("", BarColor.WHITE, BarStyle.SOLID);

        updatePoint();
        return true;
    }

    public static boolean end() {
        if (!GameNow) return false;
        GameNow = false;

        if (endCmds.size() > 0) for (String cmd : endCmds) Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), cmd);

        Bukkit.broadcastMessage(ChatColor.GOLD + GameEndMsg);
        return true;
    }

    public static void updatePoint() {
        new BukkitRunnable() {
            @Override
            public void run() {
                CoalPoint = 0;
                IronPoint = 0;
                GoldPoint = 0;
                DiamondPoint = 0;
                EmeraldPoint = 0;
                CopperPoint = 0;
                LapisLazuliPoint = 0;
                RedstonePoint = 0;
                ItemStack itemStack;
                for (Player player : Bukkit.getOnlinePlayers()) {
                    for (int i = 0; i < 36; i++) {
                        itemStack = player.getInventory().getItem(i);
                        if (itemStack == null) continue;
                        initPoint(itemStack);
                    }
                    initPoint(player.getInventory().getItemInOffHand());
                }
                AllPoint = CoalPoint+IronPoint+GoldPoint+DiamondPoint+EmeraldPoint+CopperPoint+LapisLazuliPoint+RedstonePoint;
                updateSidebar();
                updateBossbar();
                if (GameNow && AllPoint >= GoalPoint) {
                    GameNow = false;
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.sendTitle(GameClearTitle, "", 10, 70, 10);
                        player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 20f, 0f);
                    }
                }
            }
        }.runTaskLater(OreGetGame.getPlugin(), 2L);
    }

    private static void initPoint(ItemStack itemStack) {
        switch (itemStack.getType()) {
            case COAL:
                CoalPoint+=CoalOptionPoint*itemStack.getAmount();
                break;
            case IRON_INGOT:
                IronPoint+=IronOptionPoint*itemStack.getAmount();
                break;
            case GOLD_INGOT:
                GoldPoint+=GoldOptionPoint*itemStack.getAmount();
                break;
            case DIAMOND:
                DiamondPoint+=DiamondOptionPoint*itemStack.getAmount();
                break;
            case EMERALD:
                EmeraldPoint+=EmeraldOptionPoint*itemStack.getAmount();
                break;
            case COPPER_INGOT:
                CopperPoint+=CopperOptionPoint*itemStack.getAmount();
                break;
            case LAPIS_LAZULI:
                LapisLazuliPoint+=LapisLazuliOptionPoint*itemStack.getAmount();
                break;
            case REDSTONE:
                RedstonePoint+=RedstoneOptionPoint*itemStack.getAmount();
                break;
        }
    }

    public static void reset() {
        if (sidebar != null) sidebar.unregister(); sidebar = null;
        if (bossBar != null) bossBar.removeAll(); bossBar = null;
        GameNow = false;
    }

    private static void updateSidebar() {
        if (OreGetGame.scoreboard == null) OreGetGame.scoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard();
        if (sidebar == null) return;
        sidebar.setDisplaySlot(DisplaySlot.SIDEBAR);

        sidebar.getScore(CoalPointName + "(" + CoalOptionPoint + "pt)").setScore(CoalPoint);
        sidebar.getScore(IronPointName + "(" + IronOptionPoint + "pt)").setScore(IronPoint);
        sidebar.getScore(GoldPointName + "(" + GoldOptionPoint + "pt)").setScore(GoldPoint);
        sidebar.getScore(CopperPointName + "(" + CopperOptionPoint + "pt)").setScore(CopperPoint);
        sidebar.getScore(DiamondPointName + "(" + DiamondOptionPoint + "pt)").setScore(DiamondPoint);
        sidebar.getScore(EmeraldPointName + "(" + EmeraldOptionPoint + "pt)").setScore(EmeraldPoint);
        sidebar.getScore(LapisLazuliPointName + "(" + LapisLazuliOptionPoint + "pt)").setScore(LapisLazuliPoint);
        sidebar.getScore(RedstonePointName + "(" + RedstoneOptionPoint + "pt)").setScore(RedstonePoint);
        sidebar.getScore(AllPointName).setScore(AllPoint);

        for (Player player : Bukkit.getOnlinePlayers()) player.setScoreboard(OreGetGame.scoreboard);
    }

    private static void updateBossbar() {
        if (bossBar == null) return;
        bossBar.setTitle(BossBarText + " " + AllPoint + "/" + GoalPoint);
        for (Player player : Bukkit.getOnlinePlayers()) if (!bossBar.getPlayers().contains(player)) bossBar.addPlayer(player);
    }

    public static Objective getSidebar() {
        return sidebar;
    }

}
