package gangs.gangs.permissions;

import gangs.gangs.Gangs;
import gangs.gangs.data.GangData;
import lombok.Getter;
import lombok.Setter;
import me.elapsed.universal.commons.objects.Placeholder;
import me.elapsed.universal.nms.NMS;
import me.elapsed.universal.objects.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

public enum Permission {

    CHAT("CHAT", "Permissions.CHAT"),
    KICK("KICK", "Permissions.KICK"),
    INVITE("INVITE", "Permissions.INVITE"),
    PROMOTE("PROMOTE", "Permissions.PROMOTE"),
    DEMOTE("DEMOTE", "Permissions.DEMOTE");

    @Getter
    private final String name;
    @Getter
    private final String path;
    @Getter
    @Setter
    private ItemBuilder displayItem;
    @Getter
    private final String slot;

    /**
     *
     * @param name This is the name of that custom item.
     * @param path This is the path to the custom item.
     *
     */
    Permission(String name, String path) {
        this.name = name;
        this.path = path;
        JavaPlugin instance = Gangs.getPlugin(Gangs.class);
        displayItem = new ItemBuilder(instance, path);
        NMS.getInstance().setKey(displayItem, "DATA", name);
        this.slot = path + ".slot";
    }

    /**
     * @param name This param is used for finding which permission has that name.
     * @return This will return the obj
     */
    public static Permission fromString(String name) {
        for (Permission permissions : values()) {
            if (permissions.getName().equals(name)) {
                return permissions;
            }
        }
        return null;
    }

    public static ItemBuilder getDisplayItemFromName(String name) {
        for (Permission permissions : values()) {
            if(permissions.getName().equals(name)) {
                return permissions.getDisplayItem();
            }
        }
        return null;
    }
}
