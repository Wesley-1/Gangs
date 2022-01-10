package gangs.gangs.menus;

import gangs.gangs.gangs.ranks.enums.Ranks;
import gangs.gangs.menus.permissions.PermissionMenu;
import gangs.gangs.menus.permissions.ranks.RankPermissionMenu;
import gangs.gangs.menus.players.MembersMenu;
import gangs.gangs.permissions.Permission;
import me.elapsed.universal.commons.objects.Placeholder;
import me.elapsed.universal.menu.Menu;
import me.elapsed.universal.objects.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public class GangMenu extends Menu {

    private final Player player;
    private final JavaPlugin plugin;

    public GangMenu(Player player, JavaPlugin plugin) {
        super(player, "GANG_MENU", plugin);
        this.player = player;
        this.plugin = plugin;
    }

    @Override
    public void run() {
        ItemBuilder permissions = new ItemBuilder(plugin, "menus.GANG_MENU.items.permissions");
        ItemBuilder members = new ItemBuilder(plugin, "menus.GANG_MENU.items.members");

        CompletableFuture.runAsync(() -> {
            this.setItem(plugin.getConfig().getInt("menus.GANG_MENU.items.permissions.slot"), permissions, event -> {
                player.closeInventory();
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    new RankPermissionMenu(player, plugin).displayMenu();
                }, 1);
            });

            this.setItem(plugin.getConfig().getInt("menus.GANG_MENU.items.members.slot"), members, event -> {
                player.closeInventory();
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    new MembersMenu(player, plugin).displayMenu();
                }, 1);
            });
        });
       // this.fillEmptySlots(new ItemBuilder(plugin, "menus.GANG_MENU.fills"), true);
        setCloseExecutor(event -> destroy());
    }
}