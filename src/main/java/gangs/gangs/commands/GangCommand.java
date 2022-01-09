package gangs.gangs.commands;

import gangs.gangs.Gangs;
import gangs.gangs.commands.subcommands.player.inviting.GangInvite;
import gangs.gangs.commands.subcommands.player.inviting.GangJoin;
import gangs.gangs.commands.subcommands.leaving.GangKick;
import gangs.gangs.commands.subcommands.player.promotion.GangPromote;
import gangs.gangs.commands.subcommands.special.GangCreate;
import gangs.gangs.commands.subcommands.leaving.GangDisband;
import gangs.gangs.commands.subcommands.leaving.GangLeave;
import gangs.gangs.gangs.ranks.Rank;
import gangs.gangs.menus.GangMenu;
import gangs.gangs.menus.permissions.PermissionMenu;
import gangs.gangs.menus.permissions.ranks.RankPermissionMenu;
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
                new GangPromote()
        ).forEach(this::addSubcommand);
    }
    @Override
    public void perform(CommandSender commandSender, String s, String[] strings) {
        Player player = (Player) commandSender;
        new RankPermissionMenu(player, instance).displayMenu();
    }
}
