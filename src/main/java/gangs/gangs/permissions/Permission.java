package gangs.gangs.permissions;

import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public enum Permission {

    CHAT("GANG CHAT", new ItemStack(Material.ENDER_CHEST));

   @Getter
   private final String name;
   @Getter
   private final ItemStack displayItem;


    Permission(String name, ItemStack displayItem) {
        this.name = name;
        this.displayItem = displayItem;
    }

    /**
     *
     * @param name This param is used for finding which permission has that name.
     *
     * @return This will return the obj
     *
     */
    public static Permission fromString(String name) {
        for(Permission permissions : values()) {
            if(permissions.getName().equals(name)) {
                return permissions;
            }
        }
        return null;
    }
}
