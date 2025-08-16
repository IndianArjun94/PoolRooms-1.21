package net.arjun.worldgen.conceptdev;

import net.arjun.worldgen.RoomType;
import net.minecraft.util.math.Direction;

import java.util.ArrayList;

public class PoolRoom {
    public final RoomType type;

    public final int x;
    public final int x2;
    public final int y;
    public final int y2;

    public ArrayList<Direction> entrances = new ArrayList<>();

    public PoolRoom(RoomType type, int x, int y, int x2, int y2) {
        this.type = type;
        this.x = x;
        this.x2 = x2;
        this.y = y;
        this.y2 = y2;
    }

    public void addEntrance(Direction entrance) {
        if (!entrances.contains(entrance)) {
            entrances.add(entrance);
        } else {
            System.err.println("Entrance \"" + entrance + "\" already added!");
            System.exit(1);
        }
    }
}
