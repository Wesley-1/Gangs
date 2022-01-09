package gangs.gangs.menus.permissions.ranks;

import gangs.gangs.Gangs;
import gangs.gangs.gangs.ranks.Rank;
import gangs.gangs.gangs.ranks.enums.Ranks;
import gangs.gangs.menus.permissions.PermissionMenu;
import jdk.javadoc.internal.doclets.toolkit.builders.ConstructorBuilder;
import lombok.Getter;
import me.elapsed.universal.menu.Menu;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class RankPermissionMenu extends Menu {

    private final Player player;
    private final JavaPlugin plugin;

    public RankPermissionMenu(Player player, JavaPlugin plugin) {
        super(player, "RANK_MENU", plugin);
        this.player = player;
        this.plugin = plugin;
    }

    @Override
    public void run() {
        for (Ranks ranks : Ranks.values()) {
            Rank rank = ranks.getRank();
            this.setItem(rank.getDisplaySlot(), rank.getDisplayItem(), (event) -> {
                    player.closeInventory();
                    new PermissionMenu(Ranks.getRankByData(event.getCurrentItem()), player, plugin).displayMenu();
            });
        }
        setCloseExecutor(event -> destroy());
    }
}
