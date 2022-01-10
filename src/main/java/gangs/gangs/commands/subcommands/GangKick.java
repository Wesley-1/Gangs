package gangs.gangs.commands.subcommands;

import gangs.gangs.Gangs;
import gangs.gangs.data.GangProfileData;
import gangs.gangs.gangs.Gang;
import gangs.gangs.permissions.Permission;
import gangs.gangs.user.GangProfile;
import gangs.gangs.utils.MessageUtil;
import me.elapsed.universal.commands.ACommand;
import me.elapsed.universal.commons.objects.Placeholder;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class GangKick extends ACommand {

    private static JavaPlugin instance;

    public GangKick() {
        addAlias("kick");
        setPermission("gang.kick");
        addAutoComplete("kick");
        instance = Gangs.getPlugin(Gangs.class);
    }

    @Override
    public void perform(CommandSender commandSender, String s, String[] strings) {
        try {
            Player player = (Player) commandSender;
            Player kicked = Bukkit.getPlayer(strings[0]);

            if (kicked == null || player == null)
                return;

            kick(player, kicked);
        } catch (Exception ignored) {
            MessageUtil.sendMessagePersonal(instance.getConfig().getStringList("Messages.Gang.Other.Args"), (Player) commandSender);
        }
    }

    public static void kick(Player kicker, Player kicked) {

        Gang kickerGang  = GangProfileData.loadedUsers.get(kicker.getUniqueId()).getUserGang();

        if (!kickerGang.getRankPermissions().get(kickerGang.getTeamMembers().get(kicker.getUniqueId())).contains(Permission.KICK)) {
            MessageUtil.sendMessagePersonal(instance.getConfig().getStringList("Messages.Gangs.Other.No-Permission"), kicker);
            return;
        }

        if (kickerGang.getOwnerUUID() == kicked.getUniqueId()) {
            return;
        }

        if (GangProfile.doesntHaveProfile(kicker.getUniqueId()) || GangProfile.doesntHaveProfile(kicked.getUniqueId()))
            return;

        GangProfile kickerProfile = GangProfileData.loadedUsers.get(kicker.getUniqueId());
        GangProfile kickedProfile = GangProfileData.loadedUsers.get(kicked.getUniqueId());

        if (kickedProfile.getUserGang().getTeamMembers().get(kicked.getUniqueId()).getLadder()
                > kickerProfile.getUserGang().getTeamMembers().get(kicker.getUniqueId()).getLadder()
                && kickerProfile.getUserGang().equals(kickedProfile.getUserGang())) {

            MessageUtil.sendTeamMembers(instance.getConfig().getStringList("Messages.Gang.Kicked.Kicked-Team"), kickerProfile.getUserGang(),
                    new Placeholder("%kicker%", kicker.getName()),
                    new Placeholder("%kicked%", kicked.getName()),
                    new Placeholder("%gang_name%", kickerProfile.getUserGang().getGangName()));

            MessageUtil.sendMessagePersonal(instance.getConfig().getStringList("Messages.Gang.Kicked.Kicked-Personal"), kicked,
                    new Placeholder("%kicker%", kicker.getName()),
                    new Placeholder("%gang_name%", kickerProfile.getUserGang().getGangName()));

            GangProfileData.loadedUsers.get(kicked.getUniqueId()).getUserGang().getTeamMembers().remove(kicked.getUniqueId());
            GangProfileData.loadedUsers.remove(kicked.getUniqueId());

        } else {
            MessageUtil.sendMessagePersonal(instance.getConfig().getStringList("Messages.Gang.Kicked.User-Cannot-Be-Kicked"), kicker);
        }
    }
}
