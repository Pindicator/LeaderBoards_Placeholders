package me.recar.addon.askyblock;

import com.avaje.ebean.validation.NotNull;
import com.wasteofplastic.askyblock.ASkyBlockAPI;
import com.wasteofplastic.askyblock.Island;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.recar.addon.askyblock.top.islands.TopIslands;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;
import java.util.stream.Collectors;

public class Placeholders extends PlaceholderExpansion {

    private final JavaPlugin plugin;
    private static final ASkyBlockAPI A_SKY_BLOCK_API = LeaderBoardPlaceholdersPlugin.A_SKY_BLOCK_API;

    protected Placeholders(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String onRequest(OfflinePlayer offlinePlayer, String params) {

        String placeholderText = null;
        String[] identifiers = params.split("_");

        Server server = LeaderBoardPlaceholdersPlugin.plugin.getServer();
        UUID playerUUID;

        if (identifiers.length == 2) {

            playerUUID = TopIslands.getIslands().get(getIndex(identifiers[1])).getOwner();

            switch (identifiers[0].toLowerCase()) {
                case "owner":
                    placeholderText = server.getOfflinePlayer(playerUUID).getName();
                    break;

                case "level":

                    placeholderText = String.valueOf(A_SKY_BLOCK_API.getLongIslandLevel(playerUUID));
                    break;

                case "rank":
                    placeholderText = getRank(null, identifiers);
                    break;

                case "name":
                    placeholderText = getIslandName(playerUUID);
                    break;
            }
        } else if (identifiers.length == 1) {

            playerUUID = offlinePlayer.getUniqueId();

            switch (identifiers[0].toLowerCase()) {
                case "rank":
                    placeholderText = getRank(playerUUID, null);
                    break;

                case "name":
                    placeholderText = getIslandName(playerUUID);
                    break;
            }
        }

        return placeholderText;
    }

    private String getIslandName(UUID ownerUUID) {
        return A_SKY_BLOCK_API.getIslandName(ownerUUID);
    }

    private String getRank(UUID playerUUID, String[] identifiers) {
        String person = playerUUID == null ? "They" : "You";
        if (playerUUID == null) {
            playerUUID = A_SKY_BLOCK_API.getOwnedIslands().values().stream().filter(island -> plugin.getServer().getOfflinePlayer(island.getOwner()).getName().equals(identifiers[1])).collect(Collectors.toList()).get(0).getOwner();
        }

        boolean exist = false;

        for (Island island : TopIslands.getIslands()) {
            if (!exist) {
                if (island.getOwner().equals(playerUUID))
                    exist = true;
            } else
                break;
        }

        if (!exist)
            return person + (A_SKY_BLOCK_API.hasIsland(playerUUID) ? " are not in the Leaderboards" : " don't have a Island");


        int rank = 0;
        for (Island island : TopIslands.getIslands()) {
            rank++;

            if (island.getOwner().equals(playerUUID))
                break;
        }

        return String.valueOf(rank);
    }

    private int getIndex(String identifier) {

        int index = 0;

        try {
            index = Short.parseShort(identifier);

            if (index <= 0)
                index = 0;
            else
                index--;

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return index;
    }

    @Override
    public String getIdentifier() {
        return "aslbp";
    }

    @Override
    public String getAuthor() {
        return plugin.getDescription().getAuthors().get(0);
    }

    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }
}
