package gangs.gangs.listeners;

import gangs.gangs.Gangs;
import gangs.gangs.commands.subcommands.GangInvite;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Listeners implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        GangInvite.getInvitedPlayers().remove(event.getPlayer().getUniqueId());
    }
}
