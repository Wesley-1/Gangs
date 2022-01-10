package gangs.gangs;

import gangs.gangs.commands.GangCommand;
import gangs.gangs.data.GangData;
import gangs.gangs.data.GangProfileData;
import gangs.gangs.gangs.ranks.enums.Ranks;
import gangs.gangs.listeners.Listeners;
import lombok.Getter;
import me.elapsed.universal.AtlasComponent;
import me.elapsed.universal.nms.NMS;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.HashMap;

public final class Gangs extends JavaPlugin implements AtlasComponent {

    @Override
    public void onEnable() {
        doLoad();
        this.getCommandManager().register(new GangCommand());
        this.registerListeners(this, new Listeners());
    }

    @Override
    public void onDisable() {
        doSave();
    }

    public void doSave() {
        GangData.save();
        GangProfileData.save();
    }

    public void doLoad() {
        saveDefaultConfig();
        GangData.load();
        GangProfileData.load();
        NMS.init();
    }
}
