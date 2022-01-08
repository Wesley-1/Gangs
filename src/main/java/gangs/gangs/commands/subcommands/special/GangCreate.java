package gangs.gangs.commands.subcommands.special;

import gangs.gangs.data.GangData;
import gangs.gangs.data.GangProfileData;
import gangs.gangs.gangs.gang.Gang;
import gangs.gangs.user.GangProfile;
import me.elapsed.universal.commands.ACommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.UUID;

public class GangCreate extends ACommand {

    public GangCreate() {
        addAlias("create");
        setPermission("gang.create");
    }

    @Override
    public void perform(CommandSender commandSender, String s, String[] strings) {
        Player player = (Player) commandSender;
        String gangName = strings[0];
        create(player, gangName);

    }

    public void create(Player player, String gangName) {

        if (GangData.loadedGangs.containsKey(gangName)) {
            player.sendMessage("You cannot make a gang if there is already an existing one with this name.");
            return;
        }

        if(!GangProfile.hasProfile(player.getUniqueId()) || GangProfileData.loadedUsers.isEmpty()) {
            Gang gang = new Gang(UUID.randomUUID(), player.getUniqueId(), gangName);

            GangProfile gangProfile = new GangProfile(player.getUniqueId(), gang);
            GangProfileData.loadedUsers.put(player.getUniqueId(), gangProfile);
            GangData.loadedGangs.put(gangName, gang);

            player.sendMessage("GangProfile has been created. " +
                    "(" + gangProfile.getUserGang().getGangName() + ") " + "(" + gang.getTeamMembers().get(player.getUniqueId()).getName() + ") " + player.getName());

        } else {
            player.sendMessage("You already have a gang profile!");
        }

    }
}
