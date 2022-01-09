package gangs.gangs.commands.subcommands.player.promotion;

import gangs.gangs.Gangs;
import gangs.gangs.data.GangProfileData;
import gangs.gangs.gangs.gang.Gang;
import gangs.gangs.gangs.ranks.Rank;
import gangs.gangs.gangs.ranks.enums.Ranks;
import gangs.gangs.permissions.Permission;
import gangs.gangs.user.GangProfile;
import gangs.gangs.util.Pair;
import lombok.var;
import me.elapsed.universal.commands.ACommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.swing.*;

public class GangPromote extends ACommand {

    private final Gangs instance;

    public GangPromote() {
        addAlias("promote");
        setPermission("gang.promote");
        instance = Gangs.getPlugin(Gangs.class);
    }

    @Override
    public void perform(CommandSender commandSender, String s, String[] strings) {
        Player promoter = (Player) commandSender;
        Player promoted = Bukkit.getPlayer(strings[0]);

        promote(promoter, promoted);
    }

    public void promote(Player promoter, Player promoted) {
        Gang promoterGang = GangProfileData.loadedUsers.get(promoter.getUniqueId()).getUserGang();

        if (!promoterGang.getRankPermissions().get(promoterGang.getTeamMembers().get(promoter.getUniqueId())).contains(Permission.PROMOTE)) {
            promoter.sendMessage(ChatColor.translateAlternateColorCodes('&', instance.getConfig().getString("Messages.Gangs.NoPermission")));
            return;
        }

        GangProfile promoterProfile = GangProfileData.loadedUsers.get(promoter.getUniqueId());
        GangProfile promotedProfile = GangProfileData.loadedUsers.get(promoted.getUniqueId());
        var promotedLadder = promotedProfile.getUserGang().getTeamMembers().get(promoted.getUniqueId()).getLadder();
        var promoterLadder = promoterProfile.getUserGang().getTeamMembers().get(promoter.getUniqueId()).getLadder();

        if (promotedLadder != 1) {
            if (promotedLadder > promoterLadder + 1 && promoterProfile.getUserGang().equals(promotedProfile.getUserGang())) {

                int nextPromotion = promotedProfile.getUserGang().getTeamMembers().get(promoted.getUniqueId()).getLadder() - 1;
                promotedProfile.getUserGang().getTeamMembers().replace(
                        promoted.getUniqueId(),
                        Ranks.getRankByLadder(nextPromotion));

                promoterProfile.getUserGang().getTeamMembers().forEach((uuid, rank) ->
                        Bukkit.getPlayer(uuid).sendMessage(ChatColor.translateAlternateColorCodes('&',
                                instance.getConfig().getString("Messages.Gangs.BeenPromotedTeam"))
                                .replaceAll("%promoter%", promoter.getName())
                                .replaceAll("%promoted%", promoted.getName())
                                .replaceAll("%newRank%", promotedProfile.getUserGang().getTeamMembers().get(promoted.getUniqueId()).getName())));

                promoted.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        instance.getConfig().getString("Messages.Gangs.BeenPromotedPersonal"))
                        .replaceAll("%promoter%", promoter.getName())
                        .replaceAll("%newRank%", promotedProfile.getUserGang().getTeamMembers().get(promoted.getUniqueId()).getName()));
            } else {
                promoter.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        instance.getConfig().getString("Messages.Gangs.CannotPromoteUser")));
            }
        } else {
            promoter.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    instance.getConfig().getString("Messages.Gangs.CannotPromoteUser")));
        }
    }
}
