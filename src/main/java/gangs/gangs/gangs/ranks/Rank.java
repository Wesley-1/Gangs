package gangs.gangs.gangs.ranks;

import lombok.Getter;
import me.elapsed.universal.objects.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;

@Getter
public class Rank {

    private final int ladder;
    private final String name;
    private final List<String> basePermissions;
    private final ItemBuilder displayItem;
    private final int displaySlot;
    private final String displayName;

    public Rank(String name, JavaPlugin instance) {
        this.name = name;
        this.basePermissions = instance.getConfig().getStringList("Ranks." + name + ".permissions");
        this.displayItem = new ItemBuilder(instance, "Ranks." + name + ".displayItem");
        this.displaySlot = instance.getConfig().getInt("Ranks." + name + ".displayItem" + ".slot");
        this.ladder = instance.getConfig().getInt("Ranks." + name + ".ladder");
        this.displayName = ChatColor.translateAlternateColorCodes('&', instance.getConfig().getString("Ranks." + name + ".displayName"));
    }
}
