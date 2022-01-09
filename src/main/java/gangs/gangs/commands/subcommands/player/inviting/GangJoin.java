package gangs.gangs.commands.subcommands.player.inviting;

import gangs.gangs.Gangs;
import gangs.gangs.data.GangData;
import gangs.gangs.gangs.gang.Gang;
import gangs.gangs.gangs.ranks.enums.Ranks;
import gangs.gangs.user.GangProfile;
import me.elapsed.universal.commands.ACommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

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

        if (gangToJoin == null || !gangToJoin.equals(GangInvite.getInvitedPlayers().get(player.getUniqueId()))) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', instance.getConfig().getString("Messages.Gangs.NoInviteWithName")));
            return;
        }

        join(player, gangToJoin);
    }

    public void join(Player joiner, String gangName) {

        if (!GangInvite.getInvitedPlayers().containsKey(joiner.getUniqueId())) {
            joiner.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    instance.getConfig().getString("Messages.Gangs.NoPendingInvite"))
            );
            return;
        }

        Gang gang = GangData.loadedGangs.get(gangName);
        GangProfile gangProfile = new GangProfile(joiner.getUniqueId(), gang);
        gang.getTeamMembers().put(joiner.getUniqueId(), Ranks.MEMBER.getRank());

        gangProfile.getUserGang().getTeamMembers().forEach((uuid, rank) ->
                Bukkit.getPlayer(uuid).sendMessage(ChatColor.translateAlternateColorCodes('&',
                        instance.getConfig().getString("Messages.Gangs.JoinedTeam"))
                                .replaceAll("%joiner%", joiner.getName())));

        joiner.sendMessage(ChatColor.translateAlternateColorCodes('&',
                instance.getConfig().getString("Messages.Gangs.JoinedPersonal"))
                        .replaceAll("%gang_name%", gang.getGangName()));

        GangInvite.getInvitedPlayers().remove(joiner.getUniqueId());
    }
}
