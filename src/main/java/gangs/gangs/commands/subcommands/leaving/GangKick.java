package gangs.gangs.commands.subcommands.leaving;

import gangs.gangs.Gangs;
import gangs.gangs.data.GangData;
import gangs.gangs.data.GangProfileData;
import gangs.gangs.gangs.gang.Gang;
import gangs.gangs.permissions.Permission;
import gangs.gangs.user.GangProfile;
import gangs.gangs.util.Pair;
import me.elapsed.universal.commands.ACommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import javax.swing.*;
import java.util.List;
import java.util.Map;
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

        if (kicked == null || player == null)
            return;

        kick(player, kicked);
    }

    public void kick(Player kicker, Player kicked) {

        Gang kickerGang  = GangProfileData.loadedUsers.get(kicker.getUniqueId()).getUserGang();

        if (!kickerGang.getRankPermissions().get(kickerGang.getTeamMembers().get(kicker.getUniqueId())).contains(Permission.KICK)) {
            kicker.sendMessage(ChatColor.translateAlternateColorCodes('&', instance.getConfig().getString("Messages.Gangs.NoPermission")));
            return;
        }

        if (!GangProfile.hasProfile(kicker.getUniqueId()) || !GangProfile.hasProfile(kicked.getUniqueId()))
            return;

        GangProfile kickerProfile = GangProfileData.loadedUsers.get(kicker.getUniqueId());
        GangProfile kickedProfile = GangProfileData.loadedUsers.get(kicked.getUniqueId());

        if (kickedProfile.getUserGang().getTeamMembers().get(kicked.getUniqueId()).getLadder()
                > kickerProfile.getUserGang().getTeamMembers().get(kicker.getUniqueId()).getLadder()
                && kickerProfile.getUserGang().equals(kickedProfile.getUserGang())) {

            kickerProfile.getUserGang().getTeamMembers().forEach((uuid, rank) ->
                Bukkit.getPlayer(uuid).sendMessage(ChatColor.translateAlternateColorCodes('&',
                                instance.getConfig().getString("Messages.Gangs.BeenKickedTeam"))
                                .replaceAll("%kicker%", kicker.getName())
                                .replaceAll("%kicked%", kicked.getName())));

            kicked.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    instance.getConfig().getString("Messages.Gangs.BeenKickedPersonal"))
                            .replaceAll("%kicker%", kicker.getName()));

            GangProfileData.loadedUsers.remove(kicked.getUniqueId());

        }
    }
}
