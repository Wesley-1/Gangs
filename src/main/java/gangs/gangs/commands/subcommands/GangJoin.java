package gangs.gangs.commands.subcommands;

import gangs.gangs.Gangs;
import gangs.gangs.data.GangData;
import gangs.gangs.gangs.Gang;
import gangs.gangs.gangs.ranks.enums.Ranks;
import gangs.gangs.user.GangProfile;
import gangs.gangs.utils.MessageUtil;
import me.elapsed.universal.commands.ACommand;
import me.elapsed.universal.commons.objects.Placeholder;
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
        addAutoComplete("join");
        instance = Gangs.getPlugin(Gangs.class);
    }

    @Override
    public void perform(CommandSender commandSender, String s, String[] strings) {
        try {
            Player player = (Player) commandSender;
            String gangToJoin = strings[0];

            if (gangToJoin == null || !gangToJoin.equals(GangInvite.getInvitedPlayers().get(player.getUniqueId()))) {
                MessageUtil.sendMessagePersonal(instance.getConfig().getStringList("Messages.Gang.Invitation.No-Invite-With-Gang-Name"), player);
                return;
            }
            join(player, gangToJoin);
        } catch (Exception ignored) {
            MessageUtil.sendMessagePersonal(instance.getConfig().getStringList("Messages.Gang.Other.Args"), (Player) commandSender);
        }
    }

    public void join(Player joiner, String gangName) {

        if (!GangInvite.getInvitedPlayers().containsKey(joiner.getUniqueId())) {
            MessageUtil.sendMessagePersonal(instance.getConfig().getStringList("Messages.Gang.Invitation.No-Pending-Invite"), joiner);
            return;
        }

        Gang gang = GangData.loadedGangs.get(gangName);
        GangProfile gangProfile = new GangProfile(joiner.getUniqueId(), gang);

        gang.getTeamMembers().put(joiner.getUniqueId(), Ranks.MEMBER.getRank());

        MessageUtil.sendTeamMembers(instance.getConfig().getStringList("Messages.Gang.Joining.Joined-Team"), gangProfile.getUserGang(),
                new Placeholder("%joiner%", joiner.getName()));

        MessageUtil.sendMessagePersonal(instance.getConfig().getStringList("Messages.Gang.Joining.Joined-Personal"), joiner);

        GangInvite.getInvitedPlayers().remove(joiner.getUniqueId());
    }
}
