package gangs.gangs.commands.subcommands;

import gangs.gangs.Gangs;
import gangs.gangs.data.GangProfileData;
import gangs.gangs.gangs.Gang;
import gangs.gangs.gangs.ranks.enums.Ranks;
import gangs.gangs.permissions.Permission;
import gangs.gangs.user.GangProfile;
import gangs.gangs.utils.MessageUtil;
import lombok.var;
import me.elapsed.universal.commands.ACommand;
import me.elapsed.universal.commons.objects.Placeholder;
import me.elapsed.universal.commons.utils.TextUtility;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class GangDemote extends ACommand {

    private static JavaPlugin instance;

    public GangDemote() {
        addAlias("demote");
        setPermission("gang.demote");
        addAutoComplete("demote");
        instance = Gangs.getPlugin(Gangs.class);
    }

    @Override
    public void perform(CommandSender commandSender, String s, String[] strings) {
        try {
            Player player = (Player) commandSender;
            Player player1 = Bukkit.getPlayer(strings[0]);

            if (player == null || player1 == null)
                return;

            demote(player, player1);
        } catch (Exception ignored) {
            MessageUtil.sendMessagePersonal(instance.getConfig().getStringList("Messages.Gang.Other.Args"), (Player) commandSender);
        }
    }

    public static void demote(Player demoter, Player demoted) {
        Gang demoterGang = GangProfileData.loadedUsers.get(demoter.getUniqueId()).getUserGang();

        if (!demoterGang.getRankPermissions().get(demoterGang.getTeamMembers().get(demoter.getUniqueId())).contains(Permission.DEMOTE)) {
            MessageUtil.sendMessagePersonal(instance.getConfig().getStringList("Messages.Gangs.Other.No-Permission"), demoter);
            return;
        }

        GangProfile demoterProfile = GangProfileData.loadedUsers.get(demoter.getUniqueId());
        GangProfile demotedProfile = GangProfileData.loadedUsers.get(demoted.getUniqueId());
        var demotedLadder = demotedProfile.getUserGang().getTeamMembers().get(demoted.getUniqueId()).getLadder();
        var demoterLadder = demoterProfile.getUserGang().getTeamMembers().get(demoter.getUniqueId()).getLadder();

        if (demotedLadder != Ranks.MEMBER.getRank().getLadder() + 1) {
            if (demotedLadder > demoterLadder + 1 && demoterProfile.getUserGang().equals(demotedProfile.getUserGang())) {

                int nextDemotion = demotedProfile.getUserGang().getTeamMembers().get(demoted.getUniqueId()).getLadder() + 1;
                demotedProfile.getUserGang().getTeamMembers().replace(
                        demoted.getUniqueId(),
                        Ranks.getRankByLadder(nextDemotion));

                MessageUtil.sendTeamMembers(instance.getConfig().getStringList("Messages.Gang.Demotion.User-Demoted-Team"), demoterProfile.getUserGang(),
                        new Placeholder("%demoted%", demoted.getName()),
                        new Placeholder("%demoter%", demoter.getName()),
                        new Placeholder("%newRank%", demotedProfile.getUserGang().getTeamMembers().get(demoted.getUniqueId()).getDisplayName()));

                MessageUtil.sendMessagePersonal(instance.getConfig().getStringList("Messages.Gang.Demotion.User-Demoted-Personal"), demoted,
                        new Placeholder("%demoter%", demoter.getName()),
                        new Placeholder("%newRank%", demotedProfile.getUserGang().getTeamMembers().get(demoted.getUniqueId()).getDisplayName()));
            } else {
                MessageUtil.sendMessagePersonal(instance.getConfig().getStringList("Messages.Gang.Demotion.Cannot-Promote-User"), demoter,
                        new Placeholder("%name%", demoted.getName()));
            }
        } else {
            MessageUtil.sendMessagePersonal(instance.getConfig().getStringList("Messages.Gang.Demotion.Cannot-Demote-User"), demoter,
                    new Placeholder("%name%", demoted.getName()));
        }
    }
}
