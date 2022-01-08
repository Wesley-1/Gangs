package gangs.gangs.commands.subcommands;

import gangs.gangs.Gangs;
import gangs.gangs.data.GangData;
import gangs.gangs.data.GangProfileData;
import gangs.gangs.permissions.Permission;
import gangs.gangs.user.GangProfile;
import me.elapsed.universal.commands.ACommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class GangKick extends ACommand {

    private final JavaPlugin instance;

    public GangKick() {
        addAlias("kick");
        setPermission("gang.kick");
        instance = Gangs.getPlugin(Gangs.class);
    }

    @Override
    public void perform(CommandSender commandSender, String s, String[] strings) {
        Player player = (Player) commandSender;
        Player kicked = Bukkit.getPlayer(strings[0]);
        kick(player, kicked);
    }

    public void kick(Player kicker, Player kicked) {

        if (!GangProfileData.loadedUsers.get(kicker.getUniqueId()).getUserGang().getTeamMembers().get(kicker.getUniqueId()).getPermissions().contains("KICK"))
            return;

        GangProfile kickerProfile = GangProfileData.loadedUsers.get(kicker.getUniqueId());
        GangProfile kickedProfile = GangProfileData.loadedUsers.get(kicked.getUniqueId());

        if (kickedProfile.getUserGang().getTeamMembers().get(kicked.getUniqueId()).getLadder()
                > kickerProfile.getUserGang().getTeamMembers().get(kicker.getUniqueId()).getLadder()
                && kickerProfile.getUserGang().equals(kickedProfile.getUserGang())) {

            kickerProfile.getUserGang().getTeamMembers().remove(kickedProfile.getPlayerUUID());

            kickerProfile.getUserGang().getTeamMembers().forEach((uuid, rank) ->
                Bukkit.getPlayer(uuid).sendMessage(ChatColor.translateAlternateColorCodes('&',
                                instance.getConfig().getString("Messages.Gangs.BeenKickedTeam"))
                                .replaceAll("%kicker%", kicker.getName())
                                .replaceAll("%kicked%", kicked.getName())));

            kicked.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    instance.getConfig().getString("Messages.Gangs.BeenKickedPersonal"))
                            .replaceAll("%kicker%", kicker.getName()));

        }
    }
}
