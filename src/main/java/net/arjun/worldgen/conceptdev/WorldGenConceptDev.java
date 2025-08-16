package net.arjun.worldgen.conceptdev;

import net.arjun.worldgen.RoomType;

import java.util.concurrent.ThreadLocalRandom;

import static net.arjun.worldgen.conceptdev.PoolRoomsWorldMap.*;

public class WorldGenConceptDev {

    public static int[] getAvailableRoom() {
        for (int x = 1; x <= X_BOUND; x++) {
            for (int y = 1; y <= Y_BOUND; y++) {
                if (PoolRoomsWorldMap.getInstance().spotIsAvailable(y,x)) {
                    return new int[]{y,x};
                }
            }
        } return new int[]{-1,-1};
    }

    /*TODO: I finished the single room generator in the loop, do this next time:
    *                - Change the room TO A VALID ROOM (empty/unassigned) after single room generation is done
    *                - Test!*/
    public static void main(String[] args) {
        System.out.println("This is WorldGenConceptDev!");

        int startingX = 8;
        int startingY = 8;

        int x = 1;
        int y = 1;

        PoolRoomsWorldMap map = new PoolRoomsWorldMap(15, 15);

        map.setRoom(RoomType.R1x1, startingX, startingY, startingX, startingY);

        while (map.slotsFilled != X_BOUND*Y_BOUND) {
            int nextRoomAll = ThreadLocalRandom.current().nextInt(1, 5);
            int nextRoomThree = ThreadLocalRandom.current().nextInt(1,4);
            int nextRoomTwo = ThreadLocalRandom.current().nextInt(1,3);
            int garunteedOne = ThreadLocalRandom.current().nextInt(1,4);

            if (nextRoomAll == 4 && garunteedOne != 1) { // R2x2
                if (map.roomIsAvailable(RoomType.R2x2, x,y,x+1,y+1)) {
                    map.setRoom(RoomType.R2x2, x,y,x+1,y+1);
                } else if (map.roomIsAvailable(RoomType.R2x2, x,y,x+1,y-1)) {
                    map.setRoom(RoomType.R2x2, x,y,x+1,y-1);
                } else if (map.roomIsAvailable(RoomType.R2x2, x,y,x-1,y-1)) {
                    map.setRoom(RoomType.R2x2, x,y,x-1,y-1);
                } else if (map.roomIsAvailable(RoomType.R2x2, x,y,x-1,y+1)) {
                    map.setRoom(RoomType.R2x2, x,y,x-1,y+1);
                }
            } else if (nextRoomAll == 3 || nextRoomThree == 3 && garunteedOne != 1) {
                if (map.roomIsAvailable(RoomType.R1x3, x,y,x+2,y)) {
                    map.setRoom(RoomType.R1x3, x,y,x+2,y);
                } else if (map.roomIsAvailable(RoomType.R1x3, x,y,x-2,y)) {
                    map.setRoom(RoomType.R1x3, x,y,x-2,y);
                } else if (map.roomIsAvailable(RoomType.R1x3, x,y,x,y+2)) {
                    map.setRoom(RoomType.R1x3, x,y,x,y+2);
                } else if (map.roomIsAvailable(RoomType.R1x3, x,y,x,y-2)) {
                    map.setRoom(RoomType.R1x3, x,y,x,y-2);
                }
            } else if (nextRoomAll == 2 || nextRoomThree == 2 || nextRoomTwo == 2 && garunteedOne != 1) {
                if (map.roomIsAvailable(RoomType.R1x2, x,y,x+1,y)) {
                    map.setRoom(RoomType.R1x2, x,y,x+1,y);
                } else if (map.roomIsAvailable(RoomType.R1x2, x,y,x-1,y)) {
                    map.setRoom(RoomType.R1x2, x,y,x-1,y);
                } else if (map.roomIsAvailable(RoomType.R1x2, x,y,x,y+1)) {
                    map.setRoom(RoomType.R1x2, x,y,x,y+1);
                } else if (map.roomIsAvailable(RoomType.R1x2, x,y,x,y-1)) {
                    map.setRoom(RoomType.R1x2, x,y,x,y-1);
                }
            } else {
                if (map.roomIsAvailable(RoomType.R1x1, x,y,x,y)) {
                    map.setRoom(RoomType.R1x1, x,y,x,y);
                }
            }

            int[] xy = getAvailableRoom();
            x = xy[0];
            y = xy[1];
        }

//        map.print();
    }
}
