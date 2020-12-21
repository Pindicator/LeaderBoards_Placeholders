package me.recar.addon.askyblock.top.islands;

import com.wasteofplastic.askyblock.ASkyBlockAPI;
import com.wasteofplastic.askyblock.Island;
import com.wasteofplastic.askyblock.events.IslandPostLevelEvent;
import com.wasteofplastic.askyblock.events.ReadyEvent;
import me.recar.addon.askyblock.LeaderBoardPlaceholdersPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.stream.Stream;

public class TopIslandsUpdater implements Listener {

    @EventHandler
    private void updateEvent(IslandPostLevelEvent e) {
        update();
    }

    @EventHandler
    private void updateEvent(ReadyEvent e) {
        update();
    }

    public static void manualUpdate() {
        new TopIslandsUpdater().update();
    }

    private void update() {
        ASkyBlockAPI aSkyBlockAPI = LeaderBoardPlaceholdersPlugin.A_SKY_BLOCK_API;

        Stream<Island> topIslands = aSkyBlockAPI.getOwnedIslands().values().stream().sorted((x, y)-> {
            Integer islandLevel1 = Math.toIntExact(aSkyBlockAPI.getLongIslandLevel(x.getOwner()));
            Integer islandLevel2 = Math.toIntExact(aSkyBlockAPI.getLongIslandLevel(y.getOwner()));

            return islandLevel2.compareTo(islandLevel1);
        } ).limit(LeaderBoardPlaceholdersPlugin.config.getInt("limit")).filter(island -> aSkyBlockAPI.getLongIslandLevel(island.getOwner()) != 0);

        ArrayList<Island> sortedList = new ArrayList<>();

        topIslands.forEach(sortedList::add);
        TopIslands.setIslands(sortedList);
    }

}
