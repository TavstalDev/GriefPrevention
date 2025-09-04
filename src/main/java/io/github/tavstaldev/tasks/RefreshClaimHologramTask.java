package io.github.tavstaldev.tasks;

import me.ryanhamshire.GriefPrevention.CustomLogEntryTypes;
import me.ryanhamshire.GriefPrevention.GriefPrevention;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

public class RefreshClaimHologramTask implements Runnable
{
    private List<UUID> claimOwnerUUIDs;
    private Iterator<UUID> claimOwnerIterator;
    
    public RefreshClaimHologramTask() { refreshUUIDs(); }

    @Override
    public void run()
    {
        //don't do anything when there are no claims
        if (claimOwnerUUIDs.isEmpty()) return;

        //wrap search around to beginning
        if (!claimOwnerIterator.hasNext())
        {
            refreshUUIDs();
            return;
        }

        GriefPrevention.instance.getServer().getScheduler().runTaskAsynchronously(GriefPrevention.instance, new UpdateClaimHologramPreTask(claimOwnerIterator.next()));
    }

    public void refreshUUIDs()
    {
        // Fetch owner UUIDs from list of claims
        claimOwnerUUIDs = GriefPrevention.instance.dataStore.getClaims().stream().map(claim -> claim.ownerID)
                .distinct().filter(Objects::nonNull).collect(Collectors.toList());

        if (!claimOwnerUUIDs.isEmpty())
        {
            // Randomize order
            Collections.shuffle(claimOwnerUUIDs);
        }

        GriefPrevention.AddLogEntry("The following UUIDs own a claim and will be checked for inactivity in the following order:", CustomLogEntryTypes.Debug, true);

        for (UUID uuid : claimOwnerUUIDs)
            GriefPrevention.AddLogEntry(uuid.toString(), CustomLogEntryTypes.Debug, true);

        claimOwnerIterator = claimOwnerUUIDs.iterator();
    }
}
