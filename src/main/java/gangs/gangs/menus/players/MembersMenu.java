package gangs.gangs.menus.players;

import dev.dbassett.skullcreator.SkullCreator;
import gangs.gangs.commands.subcommands.GangDemote;
import gangs.gangs.commands.subcommands.GangKick;
import gangs.gangs.commands.subcommands.GangPromote;
import gangs.gangs.data.GangData;
import gangs.gangs.data.GangProfileData;
import gangs.gangs.gangs.Gang;
import gangs.gangs.user.GangProfile;
import me.elapsed.universal.commons.objects.Placeholder;
import me.elapsed.universal.commons.utils.SkullyUtility;
import me.elapsed.universal.commons.utils.TextUtility;
import me.elapsed.universal.menu.Menu;
import me.elapsed.universal.nms.NMS;
import me.elapsed.universal.objects.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.SkullType;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class MembersMenu extends Menu {

    private final Player player;
    private final JavaPlugin plugin;

    public MembersMenu(Player player, JavaPlugin plugin) {
        super(player, "MEMBERS_MENU", plugin);
        this.player = player;
        this.plugin = plugin;
    }

    @Override
    public void run() {
        int counter = plugin.getConfig().getInt("menus.MEMBERS_MENU.slot-offset");
        for (UUID uuid : GangProfileData.loadedUsers.get(player.getUniqueId()).getUserGang().getTeamMembers().keySet()) {
            ItemBuilder skull = new ItemBuilder(plugin, "menus.MEMBERS_MENU.items.player-display");
            NMS.getInstance().setKey(skull, "DATA", String.valueOf(uuid));
            final ItemStack item = skull.clone();
            {
                final ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(TextUtility.colorize(meta.getDisplayName(), new Placeholder("%name%", Bukkit.getPlayer(uuid).getName())));
                meta.setLore(TextUtility.colorize(meta.getLore(),
                        new Placeholder("%rank%",
                                String.valueOf(GangProfileData.loadedUsers.get(
                                        UUID.fromString(NMS.getInstance().getStringValue(item, "DATA"))).getUserGang().getTeamMembers().get(
                                                UUID.fromString(NMS.getInstance().getStringValue(item, "DATA"))).getDisplayName()))));
                item.setItemMeta(meta);
            }

            counter++;
            this.setItem(
                    counter,
                    item,
                    event -> {
                        runAction(event, item);
                    }
            );
        }
        setCloseExecutor(e -> destroy());
      //  this.fillEmptySlots(new ItemBuilder(plugin, "menus.MEMBERS_MENU.fills"), true);
    }

    public void runAction(InventoryClickEvent event, ItemStack itemStack) {
        if (event.getClick().isRightClick()) {
            GangPromote.promote(player, Bukkit.getPlayer(UUID.fromString(NMS.getInstance().getStringValue(itemStack, "DATA"))));
        } else if (event.getClick().isLeftClick()) {
            GangDemote.demote(player, Bukkit.getPlayer(UUID.fromString(NMS.getInstance().getStringValue(itemStack, "DATA"))));
        } else if (event.getClick().isShiftClick()) {
            GangKick.kick(player, Bukkit.getPlayer(UUID.fromString(NMS.getInstance().getStringValue(itemStack, "DATA"))));
        }
    }
}
