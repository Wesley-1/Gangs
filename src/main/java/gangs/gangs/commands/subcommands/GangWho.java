package gangs.gangs.commands.subcommands;

import gangs.gangs.Gangs;
import gangs.gangs.data.GangProfileData;
import gangs.gangs.user.GangProfile;
import gangs.gangs.utils.MessageUtil;
import me.elapsed.universal.commands.ACommand;
import me.elapsed.universal.commons.objects.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class GangWho extends ACommand {
    private final JavaPlugin instance;

    public GangWho() {
        addAlias("help");
        setPermission("gang.help");
        addAutoComplete("help");
        this.instance = Gangs.getPlugin(Gangs.class);
    }

    @Override
    public void perform(CommandSender commandSender, String s, String[] strings) {
        try {
            Player who = Bukkit.getPlayer(strings[0]);

            if (GangProfile.doesntHaveProfile(who.getUniqueId()))
                return;

            MessageUtil.sendMessagePersonal(instance.getConfig().getStringList("Messages.Gang.Gang-Who.Who"), (Player) commandSender,
                    new Placeholder("%rank%", GangProfileData.loadedUsers.get(who.getUniqueId()).getUserGang().getTeamMembers().get(who.getUniqueId()).getDisplayName()),
                    new Placeholder("%gang_name%", GangProfileData.loadedUsers.get(who.getUniqueId()).getUserGang().getGangName()),
                    new Placeholder("%gangOwner%", Bukkit.getPlayer(GangProfileData.loadedUsers.get(who.getUniqueId()).getUserGang().getOwnerUUID()).getName()),
                    new Placeholder("%who%", who.getName())
            );
        } catch (Exception ignored) {
            MessageUtil.sendMessagePersonal(instance.getConfig().getStringList("Messages.Gang.Other.Args"), (Player) commandSender);
        }
    }
}
