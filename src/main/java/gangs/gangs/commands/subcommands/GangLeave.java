package gangs.gangs.commands.subcommands;

import gangs.gangs.Gangs;
import gangs.gangs.data.GangData;
import gangs.gangs.data.GangProfileData;
import gangs.gangs.user.GangProfile;
import gangs.gangs.utils.MessageUtil;
import me.elapsed.universal.commands.ACommand;
import me.elapsed.universal.commons.objects.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class GangLeave extends ACommand {

    private final JavaPlugin instance;

    public GangLeave() {
        addAlias("leave");
        setPermission("gang.leave");
        addAutoComplete("leave");
        instance = Gangs.getPlugin(Gangs.class);
    }

    @Override
    public void perform(CommandSender commandSender, String s, String[] strings) {
        try {
            Player player = (Player) commandSender;

            if (player == null)
                return;

            leave(player);
        } catch (Exception ignored) {
            MessageUtil.sendMessagePersonal(instance.getConfig().getStringList("Messages.Gang.Other.Args"), (Player) commandSender);
        }
    }

    public void leave(Player player) {

        if (GangProfileData.loadedUsers.get(player.getUniqueId()).getUserGang().getOwnerUUID().equals(player.getUniqueId())) {
            MessageUtil.sendMessagePersonal(instance.getConfig().getStringList("Messages.Gang.Leaving.You-Are-Leader"), player);
            return;
        }

        MessageUtil.sendTeamMembers(instance.getConfig().getStringList("Messages.Gang.Leaving.Left-Team"), GangProfileData.loadedUsers.get(player.getUniqueId()).getUserGang(),
                new Placeholder("%name%", player.getName()));

        MessageUtil.sendMessagePersonal(instance.getConfig().getStringList("Messages.Gangs.Leaving.Left-Personal"), player,
                new Placeholder("%gang_name%", GangProfileData.loadedUsers.get(player.getUniqueId()).getUserGang().getGangName()));

        GangProfileData.loadedUsers.get(player.getUniqueId()).getUserGang().getTeamMembers().remove(player.getUniqueId());
        GangProfileData.loadedUsers.remove(player.getUniqueId());

    }
}
