package xyz.mlserver.oregetgame.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import xyz.mlserver.oregetgame.OreGetGame;

public class MainAPI {

    private static int CoalOptionPoint, IronOptionPoint, GoldOptionPoint, DiamondOptionPoint, EmeraldOptionPoint, LapisLazuliOptionPoint, RedstoneOptionPoint, CopperOptionPoint;

    private static int CoalPoint, IronPoint, GoldPoint, DiamondPoint, EmeraldPoint, LapisLazuliPoint, RedstonePoint, CopperPoint, AllPoint;
    private static int GoalPoint;

    private static Objective sidebar;
    private static BossBar bossBar;

    private static boolean GameNow = false;

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
    }

    public static boolean start() {
        if (GameNow) return false;
        GameNow = true;

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.getInventory().clear();
        }
        return true;
    }

    public static boolean end() {
        if (!GameNow) return false;
        GameNow = false;
        return true;
    }

    public static void updatePoint() {
        CoalPoint = 0;
        IronPoint = 0;
        GoldPoint = 0;
        DiamondPoint = 0;
        EmeraldPoint = 0;
        CopperPoint = 0;
        LapisLazuliPoint = 0;
        RedstonePoint = 0;
        for (Player player : Bukkit.getOnlinePlayers()) {
            for (ItemStack itemStack : player.getInventory()) {
                switch (itemStack.getType()) {
                    case COAL:
                        CoalPoint+=CoalOptionPoint*itemStack.getAmount();
                    case IRON_INGOT:
                        IronPoint+=IronOptionPoint*itemStack.getAmount();
                    case GOLD_INGOT:
                        GoldPoint+=GoldOptionPoint*itemStack.getAmount();
                    case DIAMOND:
                        DiamondPoint+=DiamondOptionPoint*itemStack.getAmount();
                    case EMERALD:
                        EmeraldPoint+=EmeraldOptionPoint*itemStack.getAmount();
                    case COPPER_INGOT:
                        CopperPoint+=CopperOptionPoint*itemStack.getAmount();
                    case LAPIS_LAZULI:
                        LapisLazuliPoint+=LapisLazuliOptionPoint*itemStack.getAmount();
                    case REDSTONE:
                        RedstonePoint+=RedstoneOptionPoint*itemStack.getAmount();
                }
            }
        }
        AllPoint = CoalPoint+IronPoint+GoldPoint+DiamondPoint+EmeraldPoint+CopperPoint+LapisLazuliPoint+RedstonePoint;
        updateSidebar();
        updateBossbar();
        if (GameNow && AllPoint >= GoalPoint) {
            GameNow = false;
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendTitle(ChatColor.YELLOW + "" + ChatColor.BOLD + "チャレンジ達成", "", 10, 70, 10);
                player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 20f, 0f);
            }
        }
    }

    private static void updateSidebar() {
        sidebar = OreGetGame.scoreboard.registerNewObjective("sidebar","dummy", "points");
        sidebar.setDisplaySlot(DisplaySlot.SIDEBAR);

        sidebar.getScore("石炭" + "(" + CoalOptionPoint + "pt)").setScore(CoalPoint);
        sidebar.getScore("鉄インゴット" + "(" + IronOptionPoint + "pt)").setScore(IronPoint);
        sidebar.getScore("金インゴット" + "(" + GoldOptionPoint + "pt)").setScore(GoldPoint);
        sidebar.getScore("ダイヤモンド" + "(" + DiamondOptionPoint + "pt)").setScore(DiamondPoint);
        sidebar.getScore("エメラルド" + "(" + EmeraldOptionPoint + "pt)").setScore(EmeraldPoint);
        sidebar.getScore("ラピスラズリ" + "(" + LapisLazuliOptionPoint + "pt)").setScore(LapisLazuliPoint);
        sidebar.getScore("レッドストーン" + "(" + RedstoneOptionPoint + "pt)").setScore(RedstonePoint);
        sidebar.getScore("銅インゴット" + "(" + CopperOptionPoint + "pt)").setScore(CopperPoint);
        sidebar.getScore("合計ポイント").setScore(AllPoint);
    }

    private static void updateBossbar() {
        if (bossBar == null) bossBar = Bukkit.createBossBar("", BarColor.WHITE, BarStyle.SOLID);
        bossBar.setTitle("ポイント " + AllPoint + "/" + GoalPoint);
        for (Player player : Bukkit.getOnlinePlayers()) if (!bossBar.getPlayers().contains(player)) bossBar.addPlayer(player);
    }

}
