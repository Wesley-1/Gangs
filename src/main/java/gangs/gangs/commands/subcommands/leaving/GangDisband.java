package gangs.gangs.commands.subcommands.leaving;

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

public class GangDisband extends ACommand {

    private final JavaPlugin instance;
    public GangDisband() {
        addAlias("disband");
        setPermission("gang.disband");
        instance = Gangs.getPlugin(Gangs.class);
    }
    @Override
    public void perform(CommandSender commandSender, String s, String[] strings) {
        Player player = (Player) commandSender;

        if (player == null)
            return;

        if (!GangProfile.hasProfile(player.getUniqueId())) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    instance.getConfig().getString("Messages.Gangs.YouAreNotInAGang")));
            return;
        }

        disband(player);
    }

    public void disband(Player player) {

        if (!player.getUniqueId().equals(GangProfileData.loadedUsers.get(player.getUniqueId()).getUserGang().getOwnerUUID())) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', 
                    instance.getConfig().getString("Messages.Gangs.MustBeOwnerToDisband")));
            return;
        }

        Gang gang = GangProfileData.loadedUsers.get(player.getUniqueId()).getUserGang();

        if (gang.getTeamMembers().size() == 1 && gang.getTeamMembers().containsKey(player.getUniqueId())) {
            Bukkit.getOnlinePlayers().forEach(players -> players.sendMessage(
                    ChatColor.translateAlternateColorCodes('&',
                            instance.getConfig().getString("Messages.Gangs.GangDisbanded")
                                    .replaceAll("%gang_name", gang.getGangName()))));

            GangData.loadedGangs.remove(gang.getGangName());
            GangProfileData.loadedUsers.remove(player.getUniqueId());
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    instance.getConfig().getString("Messages.Gangs.GangCantBeDisbanded")));
        }
    }
}
