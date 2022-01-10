package gangs.gangs.menus.permissions.ranks;

import gangs.gangs.Gangs;
import gangs.gangs.data.GangProfileData;
import gangs.gangs.gangs.ranks.Rank;
import gangs.gangs.gangs.ranks.enums.Ranks;
import gangs.gangs.menus.permissions.PermissionMenu;
import gangs.gangs.user.GangProfile;
import jdk.javadoc.internal.doclets.toolkit.builders.ConstructorBuilder;
import lombok.Getter;
import me.elapsed.universal.menu.Menu;
import me.elapsed.universal.nms.NMS;
import me.elapsed.universal.objects.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

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
                GangProfile gangProfile = GangProfileData.loadedUsers.get(player.getUniqueId());

                if (gangProfile.getUserGang().getTeamMembers().get(player.getUniqueId()).getLadder() < Objects.requireNonNull(Ranks.fromString(NMS.getInstance().getStringValue(event.getView().getItem(event.getSlot()), "DATA"))).getLadder() || gangProfile.getUserGang().getTeamMembers().get(player.getUniqueId()).getLadder() == 0) {
                    player.sendMessage(Ranks.fromString(NMS.getInstance().getStringValue(event.getView().getItem(event.getSlot()), "DATA")).getDisplayName());
                    player.sendMessage("This is ur rank: " + gangProfile.getUserGang().getTeamMembers().get(player.getUniqueId()).getDisplayName());

                    player.closeInventory();
                    Bukkit.getScheduler().runTaskLater(plugin, () -> {
                        new PermissionMenu(Ranks.getRankByData(event.getCurrentItem()), player, plugin).displayMenu();
                    }, 2);
                } else {
                    player.sendMessage(Ranks.fromString(NMS.getInstance().getStringValue(event.getView().getItem(event.getSlot()), "DATA")).getDisplayName());
                }
            });
        }
       // this.fillEmptySlots(new ItemBuilder(plugin, "menus.RANK_MENU.fills"), true);
        setCloseExecutor(e -> destroy());
    }
}
