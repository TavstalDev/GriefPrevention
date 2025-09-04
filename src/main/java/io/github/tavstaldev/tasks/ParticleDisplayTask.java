package io.github.tavstaldev.tasks;

import com.github.fierioziy.particlenativeapi.api.ParticleNativeAPI;
import com.griefprevention.util.IntVector;
import com.griefprevention.visualization.BoundaryVisualization;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import me.ryanhamshire.GriefPrevention.PlayerData;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class ParticleDisplayTask extends BukkitRunnable
{
    private final Player player;
    private final PlayerData playerData;
    private final BoundaryVisualization boundaryVisualization;
    private final World particleWorld;
    private final List<IntVector> locations;
    private final ParticleNativeAPI particleAPI;

    public ParticleDisplayTask(Player player, PlayerData playerData, BoundaryVisualization boundaryVisualization, World particleWorld, List<IntVector> locations) {
        this.player = player;
        this.playerData = playerData;
        this.particleWorld = particleWorld;
        this.locations = locations;
        this.boundaryVisualization = boundaryVisualization;
        this.particleAPI = GriefPrevention.instance.getParticleApi();
    }

    @Override
    public void run()
    {
        if (player == null)
        {
            this.cancel();
            return;
        }

        if (player.getWorld() == null)
        {
            this.cancel();
            return;
        }

        // Player changed world, do not cancel the task
        // as they might return to the original world
        // GriefPrevention will handle if the boundaries changed or not
        if (player.getWorld() != particleWorld)
            return;

        if (playerData.getVisibleBoundaries() != boundaryVisualization)
        {
            this.cancel();
            return;
        }

        int playerY = player.getLocation().getBlockY();
        for (IntVector loc : locations)
            addParticleAt(loc.x(), playerY + loc.y(), loc.z());
    }

    private void addParticleAt(int x, int y, int z) {
        particleAPI.LIST_1_13.DUST_COLOR_TRANSITION.color(Color.YELLOW, Color.ORANGE, 1.5).packet(true, x, y, z).sendTo(player);
    }
}