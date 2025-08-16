package net.arjun.worldgen.conceptdev;

import net.arjun.worldgen.RoomType;

public class PoolRoomsWorldMap {
    public static int X_BOUND;
    public static int Y_BOUND;

    private int[][] rooms;

    private int instances = 0;

    private static PoolRoomsWorldMap instance;

    public int slotsFilled = 0;

    public int roomsFilled = 0;

    public PoolRoomsWorldMap(int xbound, int ybound) {
        if (instances > 0) {
            System.err.println("Only 1 instance allowed of PoolRoomsWorldMap");
            System.exit(1);
        }

        X_BOUND = xbound;
        Y_BOUND = ybound;

        rooms = new int[xbound][ybound];

        instance = this;

    }

    public static PoolRoomsWorldMap getInstance() {
        return instance;
    }

    private void updateRoom(int roomType, int x1, int y1) {
        if (getSpot(x1, y1) == 0) {
            rooms[(Y_BOUND)-y1][x1-1] = roomType;
            slotsFilled++;
            System.out.println("Set: (" + x1 + "," + y1 + ")");
        } else {
            System.err.println("Position: (" + x1 + "," + y1 + ") couldn't be set because it already was.");
            System.exit(1);
        }
    }

    private int getSpot(int x1, int y1) {
        if ((Y_BOUND)-y1 <= Y_BOUND-1 && (Y_BOUND)-y1 >= 0 && x1-1 <= X_BOUND-1 && x1-1 >= 0) {
            return rooms[(Y_BOUND) - y1][x1 - 1];
        }
        return 5;
    }

    public void setRoom(RoomType roomType, int x1, int y1, int x2, int y2) {
        roomsFilled++;
        if (roomType == RoomType.R1x1) {
            updateRoom(1, x1, y1);
        } else if (roomType == RoomType.R1x2) {
            updateRoom(2, x1, y1);
            updateRoom(2, x2, y2);
        } else if (roomType == RoomType.R1x3) {
            updateRoom(3, x1, y1);
            updateRoom(3, (x1+x2)/2, (y1+y2)/2);
            updateRoom(3, x2, y2);
        } else if (roomType == RoomType.R2x2) {
            updateRoom(4, x1, y1);
            updateRoom(4, x1, y2);
            updateRoom(4, x2, y1);
            updateRoom(4, x2, y2);
        }
    }

    public boolean spotIsAvailable(int x1, int y1) {
        return getSpot(x1, y1) == 0;
    }

    public boolean roomIsAvailable(RoomType roomType, int x1, int y1, int x2, int y2) {
        if (roomType == RoomType.R1x1) {
            return getSpot(x1, y1) == 0;
        } else if (roomType == RoomType.R1x2) {
            return getSpot(x1, y1) == 0 && getSpot(x2, y2) == 0;
        } else if (roomType == RoomType.R1x3) {
            return getSpot(x1, y1) == 0 && getSpot((x1+x2)/2, (y1+y2)/2) == 0 && getSpot(x2, y2) == 0;
        } else if (roomType == RoomType.R2x2) {
            return getSpot(x1, y1) == 0 && getSpot(x1, y2) == 0 && getSpot(x2, y1) == 0 && getSpot(x2, y2) == 0;
        }

        return false;
    }

    public void print() {
        for (int i = 1; i <= Y_BOUND; i++) {
            for (int j = 1; j <= X_BOUND; j++) {
                System.out.print(getSpot(i,j) + " ");
            }
            System.out.println();
        }
    }
}
