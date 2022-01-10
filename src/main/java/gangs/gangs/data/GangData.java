package gangs.gangs.data;

import gangs.gangs.gangs.Gang;
import me.elapsed.universal.AtlasComponent;
import me.elapsed.universal.database.json.serializer.Persist;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class GangData implements AtlasComponent {

    private static final transient GangData INSTANCE = new GangData();

    public static ConcurrentHashMap<String, Gang> loadedGangs = new ConcurrentHashMap<>();

    private GangData() {}

    public static void load() {
        Persist.getInstance().loadOrSaveDefault(INSTANCE, GangData.class, "GangData");
    }
    public static void save() {
        Persist.getInstance().save(INSTANCE, "GangData");
    }
}
