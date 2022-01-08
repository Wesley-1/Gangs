package gangs.gangs.user;

import gangs.gangs.data.GangProfileData;
import gangs.gangs.gangs.gang.Gang;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class GangProfile {

    private final UUID playerUUID;
    private final Gang userGang;

    public GangProfile(UUID playerUUID, Gang userGang) {
        this.playerUUID = playerUUID;
        this.userGang = userGang;
    }

    public static boolean hasProfile(UUID uuid) {
        return GangProfileData.loadedUsers.containsKey(uuid);
    }
}
