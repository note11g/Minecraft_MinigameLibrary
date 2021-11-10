package main;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class RoomSaveClass {
    private static final ArrayList<RoomClass> rooms = new ArrayList<>();
    private static final HashMap<Player, RoomClass> playerDates = new HashMap<>();

    public static ArrayList<RoomClass> getRooms() {
        return rooms;
    }

    public static void addRoom(RoomClass room) {
        rooms.add(room);
    }

    public static RoomClass removeRoom(RoomClass room) {
        rooms.remove(room);
        return room;
    }

    public static Player registerPlayer(Player player, RoomClass room) {
        playerDates.put(player, room);
        return player;
    }

    public static RoomClass getRoom(Player player) {
        return playerDates.get(player);
    }

    public static RoomClass removePlayer(Player player) {
        RoomClass room = playerDates.get(player);
        playerDates.remove(player);
        return room;
    }
}
