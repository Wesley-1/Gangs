package gangs.gangs.commands.subcommands;

import com.sun.tools.javap.JavapFileManager;
import gangs.gangs.Gangs;
import gangs.gangs.data.GangProfileData;
import gangs.gangs.user.GangProfile;
import lombok.Getter;
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
        invite(player, invited);
    }

    public void invite(Player inviter, Player invited) {

        if (!GangProfileData.loadedUsers.get(inviter.getUniqueId()).getUserGang().getTeamMembers().get(inviter.getUniqueId()).getPermissions().contains("INVITE"))
            return;

        GangProfile inviterProfile = GangProfileData.loadedUsers.get(inviter.getUniqueId());
        invitedPlayers.put(invited.getUniqueId(), inviterProfile.getUserGang().getGangName());

        inviter.sendMessage(ChatColor.translateAlternateColorCodes('&',
                instance.getConfig().getString("Messages.Gangs.Invite_Inviter"))
                .replaceAll("%invited%", invited.getName()));

        invited.sendMessage(ChatColor.translateAlternateColorCodes('&',
                instance.getConfig().getString("Messages.Gangs.Invite_Invited"))
                .replaceAll("%inviter%", inviter.getName()));

        Bukkit.getScheduler().runTaskLater(instance, () -> {
            invitedPlayers.remove(invited.getUniqueId());
        }, 600);
    }
}
