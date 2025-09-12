package io.github.tavstaldev.util;

import com.maximde.hologramlib.hologram.HologramManager;
import com.maximde.hologramlib.hologram.RenderMode;
import com.maximde.hologramlib.hologram.TextHologram;
import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import me.ryanhamshire.GriefPrevention.Messages;
import org.bukkit.entity.Display;

import java.util.ArrayList;

public class HoloUtil
{
    public static TextHologram createHologram(Claim claim) {
        HologramManager manager = GriefPrevention.instance.getHologramManager();
        var hologramOpt = manager.getHologram("claim_" + claim.getID());
        if (hologramOpt.isPresent())
            return (TextHologram) hologramOpt.get();

        var hologramLocation = claim.coreBlockLocation.clone();
        hologramLocation.setX(hologramLocation.getBlockX() + 0.5); // center the hologram
        hologramLocation.setY(hologramLocation.getBlockY() + 2);
        hologramLocation.setZ(hologramLocation.getBlockZ() + 0.5); // center the hologram
        TextHologram hologramData = new TextHologram("claim_" + claim.getID(), RenderMode.ALL);

        ArrayList<String> builders = new ArrayList<>();
        ArrayList<String> containers = new ArrayList<>();
        ArrayList<String> accessors = new ArrayList<>();
        ArrayList<String> managers = new ArrayList<>();
        claim.getPermissions(builders, containers, accessors, managers);

        // Adjust the Hologram Data
        String stringBuilder = GriefPrevention.instance.dataStore.getMessage(Messages.HologramTitle) + "\n" +
                GriefPrevention.instance.dataStore.getMessage(Messages.HologramOwner, claim.getOwnerName()) + "\n" +
                GriefPrevention.instance.dataStore.getMessage(Messages.HologramMembers, String.valueOf(builders.size())) + "\n" +
                GriefPrevention.instance.dataStore.getMessage(Messages.HologramBlocks, String.valueOf(claim.getArea())) + "\n" +
                GriefPrevention.instance.dataStore.getMessage(Messages.HologramExpiry, claim.getRemainingTime());
        hologramData.setText(stringBuilder);

        // Make the hologram see-through
        hologramData.setViewRange(0.2);
        hologramData.setBackgroundColor(HoloUtil.toARGB(64, 0, 0, 0));
        hologramData.setBillboard(Display.Billboard.VERTICAL);
        return manager.spawn(hologramData, hologramLocation);
    }

    public static void refreshHologram(Claim claim) {
        HologramManager manager = GriefPrevention.instance.getHologramManager();
        var hologramOpt = manager.getHologram("claim_" + claim.getID());
        if (hologramOpt.isEmpty())
            return;

        var hologram = (TextHologram)hologramOpt.get();

        ArrayList<String> builders = new ArrayList<>();
        ArrayList<String> containers = new ArrayList<>();
        ArrayList<String> accessors = new ArrayList<>();
        ArrayList<String> managers = new ArrayList<>();
        claim.getPermissions(builders, containers, accessors, managers);

        String stringBuilder = GriefPrevention.instance.dataStore.getMessage(Messages.HologramTitle) + "\n" +
                GriefPrevention.instance.dataStore.getMessage(Messages.HologramOwner, claim.getOwnerName()) + "\n" +
                GriefPrevention.instance.dataStore.getMessage(Messages.HologramMembers, String.valueOf(builders.size())) + "\n" +
                GriefPrevention.instance.dataStore.getMessage(Messages.HologramBlocks, String.valueOf(claim.getArea())) + "\n" +
                GriefPrevention.instance.dataStore.getMessage(Messages.HologramExpiry, claim.getRemainingTime());
        hologram.setText(stringBuilder);
        hologram.update();
    }

    public static boolean toggleHologram(Claim claim) {
        HologramManager manager = GriefPrevention.instance.getHologramManager();
        var hologramOpt = manager.getHologram("claim_" + claim.getID());
        if (hologramOpt.isEmpty())
        {
            createHologram(claim);
            return true;
        }
        manager.remove(hologramOpt.get().getId());
        return false;
    }

    public static int toARGB(int alpha, int red, int green, int blue) {
        return (alpha << 24) | (red << 16) | (green << 8) | blue;
    }
}
