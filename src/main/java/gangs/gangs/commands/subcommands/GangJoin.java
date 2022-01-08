package gangs.gangs.commands.subcommands;

import com.sun.tools.javap.JavapFileManager;
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

import java.util.Objects;

public class GangJoin extends ACommand {

    private final JavaPlugin instance;

    public GangJoin() {
        addAlias("join");
        setPermission("gang.join");
        instance = Gangs.getPlugin(Gangs.class);
    }

    @Override
    public void perform(CommandSender commandSender, String s, String[] strings) {
        Player player = (Player) commandSender;
        String gangToJoin = strings[0];
        join(player, gangToJoin);
    }

    public void join(Player joiner, String gangName) {

        if (!GangInvite.getInvitedPlayers().containsKey(joiner.getUniqueId()))
            return;

        Gang gang = GangData.loadedGangs.get(gangName);
        GangProfile gangProfile = new GangProfile(joiner.getUniqueId(), gang);
        GangProfileData.loadedUsers.put(joiner.getUniqueId(), gangProfile);
        gang.getTeamMembers().put(joiner.getUniqueId(), Gangs.getRankManager().getLoadedRanks().get(instance.getConfig().getInt("Ladder.Member")));

        gangProfile.getUserGang().getTeamMembers().forEach((uuid, rank) ->
                Bukkit.getPlayer(uuid).sendMessage(ChatColor.translateAlternateColorCodes('&',
                        instance.getConfig().getString("Messages.Gangs.JoinedTeam"))
                                .replaceAll("%joiner%", joiner.getName())));

        joiner.sendMessage(ChatColor.translateAlternateColorCodes('&',
                instance.getConfig().getString("Messages.Gangs.JoinedPersonal"))
                        .replaceAll("%gang_name%", gang.getGangName()));
    }
}
