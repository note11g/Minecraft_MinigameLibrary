package main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandClass implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length == 0) return false;
        if (args[0].equalsIgnoreCase("create")) {
            if (RoomSaveClass.getRoom((Player) commandSender) != null) {
                commandSender.sendMessage("방 이미 있음");
                return false;
            }
            RoomClass room = new RoomClass();
            RoomSaveClass.addRoom(room);
            room.regPlayer(RoomSaveClass.registerPlayer((Player) commandSender, room));
            commandSender.sendMessage("방 생성 됨 : " + room.getROOM_ID());
        } else if (args[0].equalsIgnoreCase("goto")) {
            if (RoomSaveClass.getRoom((Player) commandSender) != null) {
                commandSender.sendMessage("방 이미 있음");
                return false;
            }
            try {
                int code = Integer.parseInt(args[1]);
                for (RoomClass room : RoomSaveClass.getRooms()) {
                    if (room.getROOM_ID() == code) {
                        RoomSaveClass.registerPlayer((Player) commandSender, room);
                        room.regPlayer((Player) commandSender);
                        commandSender.sendMessage("방 접속 됨 : " + room.getROOM_ID());
                        return true;
                    }
                }
            } catch (Exception e) {
                commandSender.sendMessage("방이 존재하지 않거나 오류가 발생함.");
            }
        } else if (args[0].equalsIgnoreCase("quit")) {

            if (RoomSaveClass.getRoom((Player) commandSender) == null) {
                commandSender.sendMessage("방 없음");
                return false;
            }

            RoomClass room = RoomSaveClass.removePlayer((Player) commandSender);
            room.removePlayer((Player) commandSender);
            if (room.getPlayers().size() == 0)
                RoomSaveClass.removeRoom(room);

            commandSender.sendMessage("방 나감 : " + room.getROOM_ID());
        } else if (args[0].equalsIgnoreCase("vote")) {
            if (RoomSaveClass.getRoom((Player) commandSender) == null) {
                commandSender.sendMessage("방 없음");
                return false;
            }

            RoomClass room = RoomSaveClass.getRoom((Player) commandSender);
            room.vote((Player) commandSender);
            commandSender.sendMessage("투표 완료");
        }
        return true;
    }
}
