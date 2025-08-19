package io.github.tavstaldev.hologram;

import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.CustomLogEntryTypes;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.Bukkit;

import java.util.UUID;

public class UpdateClaimHologramPreTask implements Runnable
{
    private UUID ownerID = null;

    UpdateClaimHologramPreTask(UUID uuid)
    {
        this.ownerID = uuid;
    }

    @Override
    public void run()
    {
        Claim claimToExpire = null;

        for (Claim claim : GriefPrevention.instance.dataStore.getClaims())
        {
            if (ownerID.equals(claim.ownerID))
            {
                claimToExpire = claim;
                break;
            }
        }

        if (claimToExpire == null)
        {
            GriefPrevention.AddLogEntry("Unable to find a claim for " + ownerID.toString(), CustomLogEntryTypes.Debug, false);
            return;
        }

        //pass it back to the main server thread, where it's safe to delete a claim if needed
        Bukkit.getScheduler().scheduleSyncDelayedTask(GriefPrevention.instance, new UpdateClaimHologramTask(claimToExpire), 1L);
    }
}
