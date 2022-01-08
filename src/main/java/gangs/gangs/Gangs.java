package gangs.gangs;

import gangs.gangs.commands.GangCommand;
import gangs.gangs.data.GangData;
import gangs.gangs.data.GangProfileData;
import gangs.gangs.gangs.ranks.Rank;
import gangs.gangs.gangs.ranks.manager.RankManager;
import gangs.gangs.listeners.Join;
import lombok.Getter;
import me.elapsed.universal.AtlasComponent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Gangs extends JavaPlugin implements AtlasComponent {

    @Getter
    private static RankManager rankManager;
    @Override
    public void onEnable() {
        saveDefaultConfig();
        rankManager = new RankManager();
        rankManager.loadRanks();
        GangProfileData.load();
        GangData.load();
        this.getCommandManager().register(new GangCommand());
        this.registerListeners(this, new Join());
    }

    @Override
    public void onDisable() {
        GangProfileData.save();
        GangData.save();
    }
}
