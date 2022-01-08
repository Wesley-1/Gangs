package gangs.gangs.gangs.gang;

import gangs.gangs.Gangs;
import gangs.gangs.gangs.ranks.Rank;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
public class Gang {

    private final UUID identifier;
    private final UUID ownerUUID;
    private final String gangName;
    private final HashMap<UUID, Rank> teamMembers;

    public Gang(UUID identifier, UUID ownerUUID, String gangName) {
        this.identifier = identifier;
        this.ownerUUID = ownerUUID;
        this.gangName = gangName;
        this.teamMembers = new HashMap<>();
        this.teamMembers.put(ownerUUID, Gangs.getRankManager().getLoadedRanks().get(0));
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

    public void kickMember(UUID whoKicked, UUID toKick) {
        this.teamMembers.remove(toKick);
        Player kickedPlayer = Objects.requireNonNull(Bukkit.getPlayer(toKick));

        kickedPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&',
                Objects.requireNonNull(Gangs.getInstance().getConfig().getString("Messages.Gangs.BeenKicked"))
                        .replaceAll("%whoKicked%", Objects.requireNonNull(Bukkit.getPlayer(whoKicked)).getName())));
    }

 */
}
