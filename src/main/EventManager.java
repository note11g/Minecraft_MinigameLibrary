package main;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventManager implements Listener {
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        RoomClass room = RoomSaveClass.removePlayer(event.getPlayer());
        room.removePlayer(event.getPlayer());
        if (room.getPlayers().size() == 0)
            RoomSaveClass.removeRoom(room);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player && event.getEntity() instanceof Player)) return;
        RoomClass room = RoomSaveClass.getRoom((Player) event.getEntity());
        if (room == null || room != RoomSaveClass.getRoom((Player) event.getDamager())) {
            event.getDamager().sendMessage(ChatColor.RED + "같은 방 내의 플레이어만 공격이 가능합니다.");
            event.getEntity().sendMessage(ChatColor.RED + "같은 방 내의 플레이어만 공격이 가능합니다.");
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        if (!(event.getEntity() instanceof Player && event.getEntity().getKiller() != null)) return;
        Player deathPlayer = (Player) event.getEntity();
        Player attacker = event.getEntity().getKiller();
        RoomClass room = RoomSaveClass.getRoom(deathPlayer);
        if (room != null)
            room.addDeaths(deathPlayer);

        if (room != null && RoomSaveClass.getRoom(attacker) == room)
            room.addKills(attacker);
    }
}
