package gangs.gangs.commands.subcommands;

import gangs.gangs.Gangs;
import gangs.gangs.data.GangData;
import gangs.gangs.data.GangProfileData;
import gangs.gangs.gangs.Gang;
import gangs.gangs.user.GangProfile;
import gangs.gangs.utils.MessageUtil;
import me.elapsed.universal.commands.ACommand;
import me.elapsed.universal.commons.objects.Placeholder;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class GangDisband extends ACommand {

    private final JavaPlugin instance;
    public GangDisband() {
        addAlias("disband");
        setPermission("gang.disband");
        addAutoComplete("disband");
        instance = Gangs.getPlugin(Gangs.class);
    }
    @Override
    public void perform(CommandSender commandSender, String s, String[] strings) {
        try {
            Player player = (Player) commandSender;

            if (player == null)
                return;

            if (GangProfile.doesntHaveProfile(player.getUniqueId())) {
                MessageUtil.sendMessagePersonal(instance.getConfig().getStringList("Messages.Gang.Other.Not-In-Gang"), player);
                return;
            }

            disband(player);
        } catch (Exception ignored) {
            MessageUtil.sendMessagePersonal(instance.getConfig().getStringList("Messages.Gang.Other.Args"), (Player) commandSender);
        }
    }

    public void disband(Player player) {

        if (!player.getUniqueId().equals(GangProfileData.loadedUsers.get(player.getUniqueId()).getUserGang().getOwnerUUID())) {
            MessageUtil.sendMessagePersonal(instance.getConfig().getStringList("Messages.Gang.Disbanding.Must-Be-Owner"), player);
            return;
        }

        Gang gang = GangProfileData.loadedUsers.get(player.getUniqueId()).getUserGang();

        if (gang.getTeamMembers().size() == 1 && gang.getTeamMembers().containsKey(player.getUniqueId())) {

            MessageUtil.sendAllPlayers(instance.getConfig().getStringList("Messages.Gang.Disbanding.Disbanded"),
                    new Placeholder("%gang_name%", gang.getGangName()));

            GangData.loadedGangs.remove(gang.getGangName());
            GangProfileData.loadedUsers.remove(player.getUniqueId());
        } else {
            MessageUtil.sendMessagePersonal(instance.getConfig().getStringList("Messages.Gang.Disbanding.Gang-Cant-Be-Disbanded"), player);
        }
    }
}
