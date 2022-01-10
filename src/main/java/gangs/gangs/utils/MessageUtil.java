package gangs.gangs.utils;

import gangs.gangs.gangs.Gang;
import me.elapsed.universal.commons.objects.Placeholder;
import me.elapsed.universal.commons.utils.TextUtility;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.awt.*;
import java.util.List;

public class MessageUtil {

    public static void sendMessageTeamAndPersonal(List<String> configPathPlayer1, List<String> configPathPlayer2, Player p1, Player p2, Placeholder... placeholders) {
        TextUtility.colorize(configPathPlayer1, placeholders).forEach(p1::sendMessage);
        TextUtility.colorize(configPathPlayer2, placeholders).forEach(p2::sendMessage);
    }
    public static void sendMessagePersonal(List<String> configPathPlayer, Player player, Placeholder... placeholders) {
        TextUtility.colorize(configPathPlayer, placeholders).forEach(player::sendMessage);
    }
    public static void sendAllPlayers(List<String> configPathPlayer, Placeholder... placeholders) {
        TextUtility.colorize(configPathPlayer, placeholders).forEach(s -> Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(s)));
    }
    public static void sendTeamMembers(List<String> configPathPlayer, Gang gang, Placeholder... placeholders) {
        TextUtility.colorize(configPathPlayer, placeholders).forEach(s -> gang.getTeamMembers().forEach((uuid, rank) -> Bukkit.getPlayer(uuid).sendMessage(s)));
    }
}
