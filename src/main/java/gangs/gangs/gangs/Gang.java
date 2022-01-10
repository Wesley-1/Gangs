package gangs.gangs.gangs;

import gangs.gangs.data.GangData;
import gangs.gangs.gangs.ranks.Rank;
import gangs.gangs.gangs.ranks.enums.Ranks;
import gangs.gangs.permissions.Permission;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Gang {

    private final UUID ownerUUID;
    private final String gangName;
    private final HashMap<Rank, List<Permission>> rankPermissions;
    private final HashMap<UUID, Rank> teamMembers;

    public Gang(UUID ownerUUID, String gangName) {
        this.ownerUUID = ownerUUID;

        this.gangName = gangName;

        this.teamMembers = new HashMap<>();
        this.teamMembers.put(ownerUUID, Ranks.getRankByLadder(0));

        this.rankPermissions = new HashMap<>();
        for (Ranks rank : Ranks.values()) {
            List<Permission> permissions = new LinkedList<>();
            rank.getRank().getBasePermissions().forEach(permission -> permissions.add(Permission.fromString(permission)));
            rankPermissions.put(rank.getRank(), permissions);
        }

        GangData.loadedGangs.put(gangName, this);
    }

/*
    public void promoteMember(UUID uuid) {
        Player promoted = Objects.requireNonNull(Bukkit.getPlayer(uuid));
        Player promoter = Objects.requireNonNull(Bukkit.getPlayer(ownerUUID));
        if (this.teamMembers.get(uuid).getLadder() == 1) {

            promoted.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    Objects.requireNonNull(Gangs.getInstance().getConfig().getString("Messages.Gangs.HighestPromotionPromoted"))
                    ));

            promoter.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    Objects.requireNonNull(Gangs.getInstance().getConfig().getString("Messages.Gangs.HighestPromotionPromoter"))
            ));

        } else {
            int nextPromotion = this.teamMembers.get(uuid).getLadder() - 1;
          //  this.teamMembers.replace(uuid, new Rank(ladder, name, permissions).getRank(nextPromotion));

        //    promoted.sendMessage(ChatColor.translateAlternateColorCodes('&',
          //          Objects.requireNonNull(Gangs.getInstance().getConfig().getString("Messages.Gangs.BeenPromoted"))
        //    ).replace("%newRank%", this.teamMembers.get(uuid).getRank(nextPromotion).getName()));

        }
    }

 */
}
