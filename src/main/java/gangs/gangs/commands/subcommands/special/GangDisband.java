package gangs.gangs.commands.subcommands.special;

import gangs.gangs.data.GangData;
import gangs.gangs.data.GangProfileData;
import gangs.gangs.gangs.gang.Gang;
import gangs.gangs.user.GangProfile;
import me.elapsed.universal.commands.ACommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GangDisband extends ACommand {

    public GangDisband() {
        addAlias("disband");
        setPermission("gang.disband");

    }
    @Override
    public void perform(CommandSender commandSender, String s, String[] strings) {
        Player player = (Player) commandSender;
        disband(player);
    }

    public void disband(Player player) {

        if (!player.getUniqueId().equals(GangProfileData.loadedUsers.get(player.getUniqueId()).getUserGang().getOwnerUUID()))
            return;

        Gang gang = GangProfileData.loadedUsers.get(player.getUniqueId()).getUserGang();
        GangData.loadedGangs.remove(gang.getGangName());
        GangProfileData.loadedUsers.remove(player.getUniqueId());
    }
}
