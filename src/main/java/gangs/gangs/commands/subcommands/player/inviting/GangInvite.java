package gangs.gangs.commands.subcommands.player.inviting;

import com.sun.tools.javap.JavapFileManager;
import gangs.gangs.Gangs;
import gangs.gangs.data.GangProfileData;
import gangs.gangs.gangs.gang.Gang;
import gangs.gangs.permissions.Permission;
import gangs.gangs.user.GangProfile;
import gangs.gangs.util.Pair;
import lombok.Getter;
import lombok.var;
import me.elapsed.universal.commands.ACommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class GangInvite extends ACommand {

   @Getter
   private static final HashMap<UUID, String> invitedPlayers = new HashMap<>();
    private final JavaPlugin instance;

    public GangInvite() {
        addAlias("invite", "inv");
        setPermission("invite");
        instance = Gangs.getPlugin(Gangs.class);
    }

    @Override
    public void perform(CommandSender commandSender, String s, String[] strings) {
        Player player = (Player) commandSender;
        Player invited = Bukkit.getPlayer(strings[0]);

        if (invited == null || player == null)
            return;

        invite(player, invited);
    }

    public void invite(Player inviter, Player invited) {

        Gang inviterGang = GangProfileData.loadedUsers.get(inviter.getUniqueId()).getUserGang();

        if (invitedPlayers.containsKey(invited.getUniqueId())) {
            inviter.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    instance.getConfig().getString("Messages.Gangs.AlreadyHasInvitePending")));
            return;
        }
        if (!inviterGang.getRankPermissions().get(inviterGang.getTeamMembers().get(inviter.getUniqueId())).contains(Permission.INVITE)) {
            inviter.sendMessage(ChatColor.translateAlternateColorCodes('&', instance.getConfig().getString("Messages.Gangs.NoPermission")));
            return;
        }

        if (GangProfileData.loadedUsers.containsKey(invited.getUniqueId())) {
            inviter.sendMessage(ChatColor.translateAlternateColorCodes('&', instance.getConfig().getString("Messages.Gangs.AlreadyInGang")));
            return;
        }

        GangProfile inviterProfile = GangProfileData.loadedUsers.get(inviter.getUniqueId());
        invitedPlayers.put(invited.getUniqueId(), inviterProfile.getUserGang().getGangName());

        inviter.sendMessage(ChatColor.translateAlternateColorCodes('&',
                instance.getConfig().getString("Messages.Gangs.Invite_Inviter"))
                .replaceAll("%invited%", invited.getName()));

        invited.sendMessage(ChatColor.translateAlternateColorCodes('&',
                instance.getConfig().getString("Messages.Gangs.Invite_Invited"))
                .replaceAll("%inviter%", inviter.getName())
                .replaceAll("%gang_name%", inviterProfile.getUserGang().getGangName()));

        Bukkit.getScheduler().runTaskLater(instance, () -> {

            if (!invitedPlayers.containsKey(invited.getUniqueId()))
                return;

            invitedPlayers.remove(invited.getUniqueId());
            invited.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    instance.getConfig().getString("Messages.Gangs.InviteExpired_Invited")
                            .replaceAll("%gang_name%", inviterGang.getGangName())
                            .replaceAll("%inviter%", inviter.getName())));

            inviter.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    instance.getConfig().getString("Messages.Gangs.InviteExpired_Inviter")
                            .replaceAll("%gang_name%", inviterGang.getGangName())
                            .replaceAll("%invited%", invited.getName())));

        }, 600);
    }
}
