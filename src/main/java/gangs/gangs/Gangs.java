package gangs.gangs;

import gangs.gangs.commands.GangCommand;
import gangs.gangs.data.GangData;
import gangs.gangs.data.GangProfileData;
import gangs.gangs.gangs.ranks.enums.Ranks;
import gangs.gangs.listeners.Join;
import lombok.Getter;
import me.elapsed.universal.AtlasComponent;
import me.elapsed.universal.nms.NMS;
import org.bukkit.plugin.java.JavaPlugin;

public final class Gangs extends JavaPlugin implements AtlasComponent {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        GangProfileData.load();
        GangData.load();
        this.getCommandManager().register(new GangCommand());
        this.registerListeners(this, new Join());
        NMS.init();

    }

    @Override
    public void onDisable() {
        GangProfileData.save();
        GangData.save();
    }
}
