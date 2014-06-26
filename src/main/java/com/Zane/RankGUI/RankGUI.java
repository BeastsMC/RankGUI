package com.Zane.RankGUI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class RankGUI extends JavaPlugin {
    public List<RankIcon> rankIcons;
    public Map<String, RankDisplay> rankDisplays;

    public void onEnable() {
        rankIcons = new ArrayList<RankIcon>();
        rankDisplays = new HashMap<String, RankDisplay>();
        loadConfig();
    }

    private void loadConfig() {
        saveDefaultConfig();
        ConfigurationSection rankSection = getConfig().getConfigurationSection("ranks");
        for (String rankName : rankSection.getKeys(false)) {
            String displayName = formatDisplayStrings(rankSection.getString(rankName + ".display-name"));
            List<String> info = rankSection.getStringList(rankName + ".rank-info");
            for (int i = 0; i < info.size(); i++) {
                info.set(i, formatDisplayStrings(info.get(i)));
            }
            List<String> unlockedCommands = rankSection.getStringList(rankName + ".unlocked-commands");
            List<String> lockedCommands = rankSection.getStringList(rankName + ".locked-commands");
            RankIcon rank = new RankIcon(rankName, displayName, info, unlockedCommands, lockedCommands);
            rankIcons.add(rank);
        }
        getServer().getPluginManager().registerEvents(new InventoryListener(this), this);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String strCmd,
            String[] args) {
        if (args.length == 0) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                RankDisplay rankDisplay = new RankDisplay(p, this);
                rankDisplay.open();
                rankDisplays.put(p.getName(), rankDisplay);
            } else {
                sender.sendMessage("Only players can use this command!");
            }
            return true;
        } else if (args[0].equalsIgnoreCase("reload")) {
            if (sender.hasPermission("rankgui.reload")) {
                reloadConfig();
                rankDisplays.clear();
                rankIcons.clear();
                loadConfig();
                sender.sendMessage("RankGUI reloaded!");
            } else {
                sender.sendMessage("You do not have permission to use this command!");
            }
            return true;
        }
        return false;
    }

    public static String formatDisplayStrings(String message) {
        return message.replaceAll("&", String.valueOf(ChatColor.COLOR_CHAR));
    }

}
