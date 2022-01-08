package gangs.gangs.data;

import gangs.gangs.user.GangProfile;
import me.elapsed.universal.AtlasComponent;
import me.elapsed.universal.database.json.serializer.Persist;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class GangProfileData implements AtlasComponent {

    private static final transient GangProfileData INSTANCE = new GangProfileData();

    public static ConcurrentHashMap<UUID, GangProfile> loadedUsers = new ConcurrentHashMap<>();

    private GangProfileData() {}

    public static void load() {
        Persist.getInstance().loadOrSaveDefault(INSTANCE, GangProfileData.class, "GangProfiles");
    }

    public static void save() {
        Persist.getInstance().save(INSTANCE, "GangProfiles");
    }

}
