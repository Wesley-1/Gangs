package gangs.gangs.gangs.ranks;

import lombok.Getter;

import java.util.List;

@Getter
public class Rank {

    private final int ladder;
    private final String name;
    private final List<String> permissions;

    public Rank(int ladder, String name, List<String> permissions) {
        this.ladder = ladder;
        this.name = name;
        this.permissions = permissions;
    }
}
