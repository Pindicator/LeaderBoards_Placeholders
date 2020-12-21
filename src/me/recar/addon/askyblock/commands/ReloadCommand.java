package me.recar.addon.askyblock.commands;

import me.recar.addon.askyblock.LeaderBoardPlaceholdersPlugin;
import me.recar.addon.askyblock.top.islands.TopIslandsUpdater;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class ReloadCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        Plugin plugin = LeaderBoardPlaceholdersPlugin.plugin;

        plugin.reloadConfig();
        LeaderBoardPlaceholdersPlugin.config = plugin.getConfig();

        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', LeaderBoardPlaceholdersPlugin.PREFIX + "&aPlugin Config Successfully Reloaded"));

        TopIslandsUpdater.manualUpdate();

        return true;
    }
}
