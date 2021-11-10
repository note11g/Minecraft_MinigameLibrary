package main;

import org.bukkit.entity.Player;

import java.util.*;

public class RoomClass {
    private static int NowRoomId = 0;
    private final HashMap<Player, PlayerData> playerDatas = new HashMap<>();
    private final ArrayList<Player> players = new ArrayList<>();
    private final ArrayList<Player> votes = new ArrayList<>();
    private final int ROOM_ID;

    public RoomClass() {
        if (++NowRoomId == Integer.MAX_VALUE) NowRoomId = 0;
        ROOM_ID = NowRoomId;
    }

    public void regPlayer(Player player) {
        players.add(player);
        broadcast(player.getName() + "님이 방 접속 함 : " + players.size() + "명");

        PlayerData data = new PlayerData();
        data.player = player;
        playerDatas.put(player, data);
    }

    public Player removePlayer(Player player) {
        players.remove(player);
        playerDatas.remove(player);
        broadcast(player.getName() + "님이 방 나감 : " + players.size() + "명");
        return player;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void addKills(Player player) {
        PlayerData data = playerDatas.get(player);
        data.kills++;
    }

    public void addDeaths(Player player) {
        PlayerData data = playerDatas.get(player);
        data.deaths++;
    }

    public int getROOM_ID() {
        return ROOM_ID;
    }

    public boolean vote(Player player) {
        if (votes.contains(player)) return false;
        votes.add(player);
        if (votes.size() >= players.size() / 2) {
            broadcast("투표로 인해 게임이 종료되었습니다.");

            ArrayList<PlayerData> list = new ArrayList<>(playerDatas.values());
            list.sort((o1, o2) -> o2.kills - o1.kills);
            list.forEach(e -> broadcast(e.player.getName() + ": kills - " + e.kills + "  deaths - " + e.deaths));
        }
        return true;
    }

    private void broadcast(String str) {
        players.forEach(p -> p.sendMessage(str));
    }

    private static class PlayerData {
        private Player player;
        private int kills = 0;
        private int deaths = 0;
    }
}
