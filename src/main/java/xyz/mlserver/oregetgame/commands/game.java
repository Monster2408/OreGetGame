package xyz.mlserver.oregetgame.commands;

import org.apache.commons.lang.StringUtils;
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
                if (!MainAPI.start()) sender.sendMessage(MainAPI.IsGameNow);
            } else if (args[0].equalsIgnoreCase("end")) {
                if (!MainAPI.end()) sender.sendMessage(MainAPI.NotGameNow);
            } else if (args[0].equalsIgnoreCase("reset")) {
                MainAPI.reset();
            } else if (args[0].equalsIgnoreCase("set")) {
                if (MainAPI.GameNow) sender.sendMessage(MainAPI.IsGameNow);
                else if (args.length == 1) {
                    sender.sendMessage(MainAPI.WrongArgument + "(goal/coal/iron/gold/copper/diamond/emerald/lapis/redstone)");
                } else {
                    String text = MainAPI.SetPoint;;
                    if (args.length == 2) sender.sendMessage(MainAPI.PleaseSetPoint);
                    else {
                        if (!StringUtils.isNumeric(args[2])) sender.sendMessage(MainAPI.PleaseSetPoint);
                        else {
                            text = text.replace("%%POINT%%", args[2]);
                            switch (args[1].toLowerCase()) {
                                case "goal":
                                    MainAPI.GoalPoint = Integer.parseInt(args[2]);
                                    text = text.replace("%%NAME%%", MainAPI.ClearPointName);
                                    break;
                                case "coal":
                                    MainAPI.CoalOptionPoint = Integer.parseInt(args[2]);
                                    text = text.replace("%%NAME%%", MainAPI.CoalPointName);
                                    break;
                                case "iron":
                                    MainAPI.IronOptionPoint = Integer.parseInt(args[2]);
                                    text = text.replace("%%NAME%%", MainAPI.IronPointName);
                                    break;
                                case "gold":
                                    MainAPI.GoldOptionPoint = Integer.parseInt(args[2]);
                                    text = text.replace("%%NAME%%", MainAPI.GoldPointName);
                                    break;
                                case "copper":
                                    MainAPI.CopperOptionPoint = Integer.parseInt(args[2]);
                                    text = text.replace("%%NAME%%", MainAPI.CopperPointName);
                                    break;
                                case "diamond":
                                    MainAPI.DiamondOptionPoint = Integer.parseInt(args[2]);
                                    text = text.replace("%%NAME%%", MainAPI.DiamondPointName);
                                    break;
                                case "emerald":
                                    MainAPI.EmeraldOptionPoint = Integer.parseInt(args[2]);
                                    text = text.replace("%%NAME%%", MainAPI.EmeraldPointName);
                                    break;
                                case "lapis":
                                    MainAPI.LapisLazuliOptionPoint = Integer.parseInt(args[2]);
                                    text = text.replace("%%NAME%%", MainAPI.LapisLazuliPointName);
                                    break;
                                case "redstone":
                                    MainAPI.RedstoneOptionPoint = Integer.parseInt(args[2]);
                                    text = text.replace("%%NAME%%", MainAPI.RedstonePointName);
                                    break;
                                default:
                                    sender.sendMessage(MainAPI.WrongArgument + "(goal/coal/iron/gold/copper/diamond/emerald/lapis/redstone)");
                                    return true;
                            }
                            if (!text.contains("%%NAME%%")) sender.sendMessage(text);
                        }

                    }
                }
            }
        }
        return true;
    }

    private static final CmdUtil cmdUtil = new CmdUtil()
            .addOP("/game start", MainAPI.StartDescription)
            .addOP("/game end", MainAPI.EndDescription)
            .addOP("/game reset", MainAPI.ResetDescription)
            .addOP("/game set <name> <int>", MainAPI.SetPointDescription)
            .addOP("/game help", MainAPI.HelpDescription)
            ;
}
