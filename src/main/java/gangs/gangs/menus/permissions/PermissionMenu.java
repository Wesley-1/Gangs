package gangs.gangs.menus.permissions;

import gangs.gangs.Gangs;
import gangs.gangs.data.GangData;
import gangs.gangs.data.GangProfileData;
import gangs.gangs.gangs.Gang;
import gangs.gangs.gangs.ranks.Rank;
import gangs.gangs.menus.permissions.ranks.RankPermissionMenu;
import gangs.gangs.permissions.Permission;
import gangs.gangs.user.GangProfile;
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
import org.bukkit.inventory.meta.ItemMeta;
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

        updateTitle(this.getTitle().replace("%displayRank%", rank.getDisplayName()));

        for (Permission permission : Permission.values()) {
            final ItemStack item = permission.getDisplayItem().clone();
            {
                final ItemMeta meta = item.getItemMeta();
                meta.setLore(TextUtility.colorize(meta.getLore(),
                        new Placeholder("%hasPermission%", checkStatus(gang.getRankPermissions().get(rank), permission))));
                item.setItemMeta(meta);
            }
            this.setItem(plugin.getConfig().getInt(permission.getSlot()), item, event -> {
                if (profile.getUserGang().getOwnerUUID().equals(player.getUniqueId())) {
                    update(rank, gang, gang.getRankPermissions().get(rank), permission);
                }
            });
        }
        //this.fillEmptySlots(new ItemBuilder(plugin, "menus.PERMISSION_MENU.fills"), true);
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
