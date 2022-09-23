package xyz.mlserver.oregetgame.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import xyz.mlserver.mc.util.command.CmdUtil;
import xyz.mlserver.oregetgame.utils.MainAPI;

public class game implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0){
            cmdUtil.send(sender, 1, true);
        } else {
            if (args[0].equalsIgnoreCase("help")) {
                cmdUtil.send(sender, 1, true);
            } else if (args[0].equalsIgnoreCase("start")) {
                if (!MainAPI.start()) sender.sendMessage(ChatColor.RED + "ゲーム中にゲームを開始することはできません。");
            } else if (args[0].equalsIgnoreCase("end")) {
                if (!MainAPI.end()) sender.sendMessage(ChatColor.RED + "ゲームが開始されていません。");
            } else if (args[0].equalsIgnoreCase("reset")) {
                MainAPI.reset();
            }
        }
        return true;
    }

    private static CmdUtil cmdUtil = new CmdUtil()
            .addOP("/game start", "開始コマンド")
            .addOP("/game end", "終了コマンド")
            .addOP("/game reset", "初期化コマンド")
            .addOP("/game help", "ヘルプコマンド")
            ;
}
