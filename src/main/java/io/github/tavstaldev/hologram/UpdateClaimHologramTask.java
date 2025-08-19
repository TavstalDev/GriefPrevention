package io.github.tavstaldev.hologram;

import me.ryanhamshire.GriefPrevention.Claim;

public class UpdateClaimHologramTask implements Runnable
{
    Claim claim;

    public UpdateClaimHologramTask(Claim claim)
    {
        this.claim = claim;
    }

    @Override
    public void run()
    {
        claim.refreshHologram();
    }
}
