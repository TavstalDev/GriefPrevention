/*
    GriefPrevention Server Plugin for Minecraft
    Copyright (C) 2012 Ryan Hamshire

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package me.ryanhamshire.GriefPrevention;

import me.ryanhamshire.GriefPrevention.events.ClaimExpirationEvent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.time.LocalDateTime;
import java.util.Vector;

class CleanupUnusedClaimTask implements Runnable
{
    Claim claim;
    PlayerData ownerData;
    OfflinePlayer ownerInfo;

    CleanupUnusedClaimTask(Claim claim, PlayerData ownerData, OfflinePlayer ownerInfo)
    {
        this.claim = claim;
        this.ownerData = ownerData;
        this.ownerInfo = ownerInfo;
    }

    @Override
    public void run()
    {
        if (expireEventCanceled())
            return;
        //make a copy of this player's claim list
        Vector<Claim> claims = new Vector<>(ownerData.getClaims());
        final LocalDateTime currentTime = LocalDateTime.now();

        for (Claim claim : claims)
        {
            //if this claim is not expired, skip it
            if (!currentTime.isAfter(claim.expirationDate))
                return;

            GriefPrevention.instance.dataStore.deleteClaim(claim);
            if (ownerInfo.isOnline()) {
                GriefPrevention.sendMessage(ownerInfo.getPlayer(), TextMode.Info, Messages.ClaimAutoRemoved);
            }
            GriefPrevention.AddLogEntry("Deleted expired claim for " + ownerInfo.getName() + " at " + claim.getLesserBoundaryCorner().toString() + ".", CustomLogEntryTypes.AdminActivity);
        }
    }

    public boolean expireEventCanceled()
    {
        //see if any other plugins don't want this claim deleted
        ClaimExpirationEvent event = new ClaimExpirationEvent(this.claim);
        Bukkit.getPluginManager().callEvent(event);
        return event.isCancelled();
    }
}
