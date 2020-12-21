package me.recar.addon.askyblock.top.islands;

import com.wasteofplastic.askyblock.Island;

import java.util.ArrayList;

public class TopIslands {

    private static ArrayList<Island> islands = new ArrayList<>();

    public static ArrayList<Island> getIslands() {
        return islands;
    }

    protected static void setIslands(ArrayList<Island> islands) {
        TopIslands.islands = islands;
    }

}
