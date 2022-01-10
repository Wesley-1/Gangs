package gangs.gangs.commands.subcommands;

import gangs.gangs.Gangs;
import gangs.gangs.utils.MessageUtil;
import me.elapsed.universal.commands.ACommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class GangHelp extends ACommand {

    private final JavaPlugin instance;

    public GangHelp() {
        addAlias("help");
        setPermission("gang.help");
        addAutoComplete("help");
        this.instance = Gangs.getPlugin(Gangs.class);
    }
    @Override
    public void perform(CommandSender commandSender, String s, String[] strings) {
        try {
            MessageUtil.sendMessagePersonal(instance.getConfig().getStringList("Messages.Gang.Other.Help"), (Player) commandSender);
        } catch (Exception ignored) {
            MessageUtil.sendMessagePersonal(instance.getConfig().getStringList("Messages.Gang.Other.Args"), (Player) commandSender);
        }
    }
}
