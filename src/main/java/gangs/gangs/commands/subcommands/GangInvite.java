package gangs.gangs.commands.subcommands;

import gangs.gangs.Gangs;
import gangs.gangs.data.GangProfileData;
import gangs.gangs.gangs.Gang;
import gangs.gangs.permissions.Permission;
import gangs.gangs.user.GangProfile;
import gangs.gangs.utils.MessageUtil;
import lombok.Getter;
import me.elapsed.universal.commands.ACommand;
import me.elapsed.universal.commons.objects.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class GangInvite extends ACommand {

   @Getter
   private static final HashMap<UUID, String> invitedPlayers = new HashMap<>();
    private final JavaPlugin instance;

    public GangInvite() {
        addAlias("invite", "inv");
        setPermission("invite");
        addAutoComplete("invite");
        instance = Gangs.getPlugin(Gangs.class);
    }

    @Override
    public void perform(CommandSender commandSender, String s, String[] strings) {
        try {
            Player player = (Player) commandSender;
            Player invited = Bukkit.getPlayer(strings[0]);

            if (invited == null || player == null)
                return;

            invite(player, invited);
        } catch(Exception ignored) {
            MessageUtil.sendMessagePersonal(instance.getConfig().getStringList("Messages.Gang.Other.Args"), (Player) commandSender);
        }
    }

    public void invite(Player inviter, Player invited) {

        Gang inviterGang = GangProfileData.loadedUsers.get(inviter.getUniqueId()).getUserGang();
        if (invitedPlayers.containsKey(invited.getUniqueId())) {
            MessageUtil.sendMessagePersonal(instance.getConfig().getStringList("Messages.Gang.Invitation.Invite-Already-Pending"), inviter);

            return;
        }
        if (!inviterGang.getRankPermissions().get(inviterGang.getTeamMembers().get(inviter.getUniqueId())).contains(Permission.INVITE)) {
            MessageUtil.sendMessagePersonal(instance.getConfig().getStringList("Messages.Gang.Other.No-Permission"), inviter);
            return;
        }

        if (GangProfileData.loadedUsers.containsKey(invited.getUniqueId())) {
            MessageUtil.sendMessagePersonal(instance.getConfig().getStringList("Messages.Gang.Invitation.No-Pending-Invite"), inviter);
            return;
        }

        GangProfile inviterProfile = GangProfileData.loadedUsers.get(inviter.getUniqueId());
        invitedPlayers.put(invited.getUniqueId(), inviterProfile.getUserGang().getGangName());

        MessageUtil.sendMessagePersonal(instance.getConfig().getStringList("Messages.Gang.Invitation.Invite-Sent-Receiver"), invited,
                new Placeholder("%gang_name%", inviterGang.getGangName()),
                new Placeholder("%sender%", inviter.getName()));

        MessageUtil.sendMessagePersonal(instance.getConfig().getStringList("Messages.Gang.Invitation.Invite-Sent-Sender"), inviter,
                new Placeholder("%gang_name%", inviterGang.getGangName()),
                new Placeholder("%receiver%", invited.getName()));

        Bukkit.getScheduler().runTaskLater(instance, () -> {

            if (!invitedPlayers.containsKey(invited.getUniqueId()))
                return;

            invitedPlayers.remove(invited.getUniqueId());

            MessageUtil.sendMessagePersonal(instance.getConfig().getStringList("Messages.Gang.Invitation.Invite-Expired-Receiver"), invited,
                    new Placeholder("%gang_name%", inviterGang.getGangName()),
                    new Placeholder("%sender%", inviter.getName()));

            MessageUtil.sendMessagePersonal(instance.getConfig().getStringList("Messages.Gang.Invitation.Invite-Expired-Sender"), inviter,
                    new Placeholder("%gang_name%", inviterGang.getGangName()),
                    new Placeholder("%receiver%", invited.getName()));

        }, 600);
    }
}
