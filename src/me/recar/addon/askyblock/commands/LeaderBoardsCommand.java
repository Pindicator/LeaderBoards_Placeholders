package me.recar.addon.askyblock.commands;

import com.wasteofplastic.askyblock.Island;
import me.recar.addon.askyblock.LeaderBoardPlaceholdersPlugin;
import me.recar.addon.askyblock.top.islands.TopIslands;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class LeaderBoardsCommand implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        Plugin plugin  = LeaderBoardPlaceholdersPlugin.plugin;

        ArrayList<Island> islands = TopIslands.getIslands();

        islands.forEach(island -> sender.sendMessage(plugin.getServer().getOfflinePlayer(island.getOwner()).getName()));

        return true;
    }


}
