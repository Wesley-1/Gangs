package gangs.gangs.commands;

import gangs.gangs.commands.subcommands.GangInvite;
import gangs.gangs.commands.subcommands.GangJoin;
import gangs.gangs.commands.subcommands.GangKick;
import gangs.gangs.commands.subcommands.special.GangCreate;
import gangs.gangs.commands.subcommands.special.GangDisband;
import gangs.gangs.commands.subcommands.special.GangLeave;
import me.elapsed.universal.commands.ACommand;
import org.bukkit.command.CommandSender;

import java.util.Arrays;

public class GangCommand extends ACommand {

    public GangCommand() {
        addAlias("gang", "gangs");
        setConsoleSender(false);
        setPermission("gang.main");

        Arrays.asList(
                new GangCreate(),
                new GangDisband(),
                new GangLeave(),
                new GangKick(),
                new GangInvite(),
                new GangJoin()
        ).forEach(this::addSubcommand);
    }
    @Override
    public void perform(CommandSender commandSender, String s, String[] strings) {
        System.out.println("Error, use alias");
    }
}
