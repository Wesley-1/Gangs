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

public enum Permission {

    CHAT("CHAT", "Permissions.CHAT"),
    KICK("KICK", "Permissions.KICK"),
    INVITE("INVITE", "Permissions.INVITE"),
    PROMOTE("PROMOTE", "Permissions.PROMOTE");

    @Getter
    private final String name;
    @Getter
    private final String path;
    @Getter
    @Setter
    private ItemBuilder displayItem;
    private final JavaPlugin instance;
    @Getter
    private final String slot;

    Permission(String name, String path) {
        this.name = name;
        this.path = path;
        instance = Gangs.getPlugin(Gangs.class);
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
