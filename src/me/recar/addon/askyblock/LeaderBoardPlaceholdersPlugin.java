package me.recar.addon.askyblock;

import com.wasteofplastic.askyblock.ASkyBlockAPI;
import me.recar.addon.askyblock.commands.LeaderBoardsCommand;
import me.recar.addon.askyblock.commands.ReloadCommand;
import me.recar.addon.askyblock.top.islands.TopIslandsUpdater;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LeaderBoardPlaceholdersPlugin extends JavaPlugin {


    public static JavaPlugin plugin;
    public static FileConfiguration config;
    public static ASkyBlockAPI A_SKY_BLOCK_API;
    public static final String PREFIX = "[ASLB] ";



    @Override
    public void onEnable() {
        plugin = this;
        config = this.getConfig();
        A_SKY_BLOCK_API = ASkyBlockAPI.getInstance();

        if (!isUpToDate()) {
            this.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', PREFIX + "&4&lThe Plugin Version that you are currently using is outdated please update the plugin."));
        }

        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new TopIslandsUpdater(), this);

        this.setCommands();
        new Placeholders(this).register();

        this.saveDefaultConfig();

        super.onEnable();
    }

    private void setCommands() {
        PluginCommand islandLeaderBoard = this.getCommand("leaderboards");
        islandLeaderBoard.setPermission("aslb.top");
        islandLeaderBoard.setExecutor(new LeaderBoardsCommand());

        PluginCommand reloadCommand = this.getCommand("aslb-reload");
        reloadCommand.setPermission("aslb.reload");
        reloadCommand.setExecutor(new ReloadCommand());
    }

    private boolean isUpToDate() {

        final String url = "https://api.spigotmc.org/legacy/update.php?resource=";
        final String id = "86872";
        final String localVersion = this.getDescription().getVersion().trim();

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url + id).openConnection();
            connection.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String latestVersion = reader.readLine().trim();

            return latestVersion.equals(localVersion);

        } catch (IOException e) {
            return false;
        }

    }

}