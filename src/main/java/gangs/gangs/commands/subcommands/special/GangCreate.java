package gangs.gangs.commands.subcommands.special;

import gangs.gangs.Gangs;
import gangs.gangs.data.GangData;
import gangs.gangs.data.GangProfileData;
import gangs.gangs.gangs.gang.Gang;
import gangs.gangs.user.GangProfile;
import me.elapsed.universal.commands.ACommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class GangCreate extends ACommand {

    private final JavaPlugin instance;
    public GangCreate() {
        addAlias("create");
        setPermission("gang.create");
        instance = Gangs.getPlugin(Gangs.class);

    }

    @Override
    public void perform(CommandSender commandSender, String s, String[] strings) {
        Player player = (Player) commandSender;
        String gangName = strings[0];

        if (gangName == null || player == null)
            return;

        create(player, gangName);

    }

    public void create(Player player, String gangName) {

        if (GangData.loadedGangs.containsKey(gangName)) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    instance.getConfig().getString("Messages.Gangs.GangAlreadyExistsWithThatName")));
            return;
        }

        if(!GangProfile.hasProfile(player.getUniqueId()) || GangProfileData.loadedUsers.isEmpty()) {
            Gang gang = new Gang(player.getUniqueId(), gangName);
            GangProfile gangProfile = new GangProfile(player.getUniqueId(), gang);

            Bukkit.getOnlinePlayers().forEach(players -> players.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    instance.getConfig().getString("Messages.Gangs.GangCreated")
                            .replaceAll("%gang_name%", gangProfile.getUserGang().getGangName())
                            .replaceAll("%creator%", player.getName()))));
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    instance.getConfig().getString("Messages.Gangs.AlreadyInGang")));
        }

    }
}
