package io.github.tavstaldev.tasks;

import io.github.tavstaldev.util.HoloUtil;
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
        HoloUtil.refreshHologram(claim);
    }
}
