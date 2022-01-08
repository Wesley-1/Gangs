package gangs.gangs.gangs.ranks.manager;

import gangs.gangs.Gangs;
import gangs.gangs.gangs.ranks.Rank;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import sun.awt.image.ImageWatched;

import java.util.LinkedHashMap;
import java.util.List;

public class RankManager {

    @Getter
    private final LinkedHashMap<Integer, Rank> loadedRanks;
    private final FileConfiguration config;
    private final JavaPlugin instance;

    public RankManager() {
        loadedRanks = new LinkedHashMap<>();
        instance = Gangs.getPlugin(Gangs.class);
        config = instance.getConfig();

    }

    public void loadRanks() {
        for (String key : config.getConfigurationSection("Ranks").getKeys(false)) {
            List<String> permissions = config.getStringList("Ranks." + key + ".permissions");
            String name = config.getString("Ranks." + key + ".name");
            int ladder = config.getInt("Ranks." + key + ".ladder");
            Rank rank = new Rank(ladder, name, permissions);
            loadedRanks.put(ladder, rank);
        }
    }

}
