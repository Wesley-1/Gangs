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

public class GangCreate extends ACommand {

    private final JavaPlugin instance;
    public GangCreate() {
        addAlias("create");
        setPermission("gang.create");
        addAutoComplete("create");
        instance = Gangs.getPlugin(Gangs.class);

    }

    @Override
    public void perform(CommandSender commandSender, String s, String[] strings) {
        try {
            Player player = (Player) commandSender;
            String gangName = strings[0];

            if (gangName == null || player == null)
                return;
            create(player, gangName);
        } catch (Exception ignored) {
            MessageUtil.sendMessagePersonal(instance.getConfig().getStringList("Messages.Gang.Other.Args"), (Player) commandSender);
        }
    }

    public void create(Player player, String gangName) {

        if (GangData.loadedGangs.containsKey(gangName)) {
            MessageUtil.sendMessagePersonal(instance.getConfig().getStringList("Messages.Gang.Creation.Already-Exists"), player);
            return;
        }

        if(GangProfile.doesntHaveProfile(player.getUniqueId()) || GangProfileData.loadedUsers.isEmpty()) {
            Gang gang = new Gang(player.getUniqueId(), gangName);
            GangProfile gangProfile = new GangProfile(player.getUniqueId(), gang);

            MessageUtil.sendAllPlayers(instance.getConfig().getStringList("Messages.Gang.Creation.Created"),
                    new Placeholder("%gang_name%",gangProfile.getUserGang().getGangName()),
                    new Placeholder("%creator%", player.getName()));
        } else {
            MessageUtil.sendMessagePersonal(instance.getConfig().getStringList("Messages.Gang.Other.Already-In-Gang"), player);
        }

    }
}
