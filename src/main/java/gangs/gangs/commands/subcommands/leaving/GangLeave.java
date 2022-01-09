package gangs.gangs.commands.subcommands.leaving;

import gangs.gangs.Gangs;
import gangs.gangs.data.GangProfileData;
import me.elapsed.universal.commands.ACommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class GangLeave extends ACommand {

    private final JavaPlugin instance;

    public GangLeave() {
        addAlias("leave");
        setPermission("gang.leave");
        instance = Gangs.getPlugin(Gangs.class);
    }

    @Override
    public void perform(CommandSender commandSender, String s, String[] strings) {
        Player player = (Player) commandSender;

        if (player == null)
            return;

        leave(player);
    }

    public void leave(Player player) {

        if (GangProfileData.loadedUsers.get(player.getUniqueId()).getUserGang().getOwnerUUID().equals(player.getUniqueId())) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    instance.getConfig().getString("Messages.Gangs.YouAreLeader_YouCannotLeave")));
            return;
        }

        GangProfileData.loadedUsers.get(player.getUniqueId()).getUserGang().getTeamMembers().forEach((uuid, rank) ->
                Bukkit.getPlayer(uuid).sendMessage(ChatColor.translateAlternateColorCodes('&',
                        instance.getConfig().getString("Messages.Gangs.LeftTeam"))
                        .replaceAll("%whoLeft%", player.getName())));

        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                instance.getConfig().getString("Messages.Gangs.LeftPersonal"))
                .replaceAll("%gang_name%", GangProfileData.loadedUsers.get(player.getUniqueId()).getUserGang().getGangName()));

        GangProfileData.loadedUsers.remove(player.getUniqueId());

    }
}
