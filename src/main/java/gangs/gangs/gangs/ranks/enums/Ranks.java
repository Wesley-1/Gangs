package gangs.gangs.gangs.ranks.enums;

import gangs.gangs.Gangs;
import gangs.gangs.gangs.ranks.Rank;
import lombok.Getter;
import me.elapsed.universal.nms.NMS;
import me.elapsed.universal.objects.ItemBuilder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public enum Ranks {

    OWNER("OWNER"),
    CO_OWNER("CO_OWNER"),
    MOD("MOD"),
    MEMBER("MEMBER");

    @Getter private final Rank rank;
    @Getter private final ItemBuilder itemBuilder;

    Ranks(String name) {
        this.rank = new Rank(name, JavaPlugin.getPlugin(Gangs.class));
        this.itemBuilder = rank.getDisplayItem();
        NMS.getInstance().setKey(itemBuilder, "DATA", name);
    }


    public static Rank fromString(String n) {
        for (Ranks rank : values()) {
            Rank ranks = rank.getRank();
            if(ranks.getName().equals(n)) {
                return ranks;
            }
        }
        return null;
    }

    public static Rank getRankByLadder(int ladder) {
        for (Ranks rank : values()) {
            Rank ranks = rank.getRank();
            if (ranks.getLadder() == ladder) {
                return ranks;
            }
        }
        return null;
    }

    public static Rank getRankByData(ItemStack itemStack) {
        return fromString(NMS.getInstance().getStringValue(itemStack, "DATA"));
    }
}
