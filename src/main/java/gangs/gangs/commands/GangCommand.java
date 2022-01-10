package gangs.gangs.commands;

import gangs.gangs.Gangs;
import gangs.gangs.commands.subcommands.*;
import gangs.gangs.menus.GangMenu;
import gangs.gangs.menus.players.MembersMenu;
import gangs.gangs.utils.MessageUtil;
import me.elapsed.universal.commands.ACommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class GangCommand extends ACommand {

    private final JavaPlugin instance;

    public GangCommand() {
        addAlias("gang", "gangs");
        setConsoleSender(false);
        setPermission("gang.main");

        instance = Gangs.getPlugin(Gangs.class);

        Arrays.asList(
                new GangCreate(),
                new GangDisband(),
                new GangLeave(),
                new GangKick(),
                new GangInvite(),
                new GangJoin(),
                new GangPromote(),
                new GangDemote(),
                new GangHelp(),
                new GangWho()
        ).forEach(this::addSubcommand);
    }
    @Override
    public void perform(CommandSender commandSender, String s, String[] strings) {
        try {
            Player player = (Player) commandSender;
            new GangMenu(player, instance).displayMenu();
        } catch(Exception ignored) {
            MessageUtil.sendMessagePersonal(instance.getConfig().getStringList("Messages.Gang.Other.Args"), (Player) commandSender);
        }
    }
}
