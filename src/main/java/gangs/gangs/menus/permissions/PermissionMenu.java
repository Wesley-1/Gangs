package gangs.gangs.menus.permissions;

import gangs.gangs.Gangs;
import gangs.gangs.data.GangData;
import gangs.gangs.data.GangProfileData;
import gangs.gangs.gangs.gang.Gang;
import gangs.gangs.gangs.ranks.Rank;
import gangs.gangs.menus.permissions.ranks.RankPermissionMenu;
import gangs.gangs.permissions.Permission;
import gangs.gangs.user.GangProfile;
import gangs.gangs.util.Pair;
import lombok.var;
import me.elapsed.universal.commons.objects.Placeholder;
import me.elapsed.universal.commons.utils.TextUtility;
import me.elapsed.universal.menu.Menu;
import me.elapsed.universal.nms.NMS;
import me.elapsed.universal.objects.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;
import java.util.List;

public class PermissionMenu extends Menu {

    private final Player player;
    private final JavaPlugin plugin;
    private final Rank rank;

    public PermissionMenu(Rank rank, Player player, JavaPlugin plugin) {
        super(player, "PERMISSION_MENU", plugin);
        this.player = player;
        this.plugin = plugin;
        this.rank = rank;
    }

    @Override
    public void run() {
        GangProfile profile = GangProfileData.loadedUsers.get(player.getUniqueId());
        Gang gang = GangData.loadedGangs.get(profile.getUserGang().getGangName());
        updateTitle(rank.getDisplayName());

        for (Permission permission : Permission.values()) {
            this.setItem(plugin.getConfig().getInt(permission.getSlot()), permission.getDisplayItem().addPlaceholders(new Placeholder("%hasPermission%", checkStatus(gang.getRankPermissions().get(rank), permission))), event ->  {
                update(rank, gang, gang.getRankPermissions().get(rank), permission);
                getInventory().setStorageContents(getInventory().getContents());
            });
        }
        setCloseExecutor(e -> destroy());
    }

    public void update(Rank rank, Gang gang, List<Permission> permissions, Permission permission) {
        if (permissions.contains(permission)) {
            permissions.remove(permission);
        } else {
            permissions.add(permission);
        }
        gang.getRankPermissions().replace(rank, permissions);
        System.out.println(gang.getRankPermissions().get(rank).toString() + " " + rank.getName());
    }

    public String checkStatus(List<Permission> permissions, Permission permission) {
            if (permissions.contains(permission)) {
                return "ALLOWED";
            } else {
                return "DENIED";
            }
    }
}
