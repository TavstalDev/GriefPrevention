package io.github.tavstaldev.commands;

import com.griefprevention.commands.CommandHandler;
import com.griefprevention.visualization.BoundaryVisualization;
import com.griefprevention.visualization.VisualizationType;
import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import me.ryanhamshire.GriefPrevention.Messages;
import me.ryanhamshire.GriefPrevention.PlayerData;
import me.ryanhamshire.GriefPrevention.TextMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ToggleBorderCommand extends CommandHandler
{
    public ToggleBorderCommand(@NotNull GriefPrevention plugin)
    {
        super(plugin, "toggleborder");
    }

    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            @NotNull String[] args)
    {
        if (!(sender instanceof Player player))
            return false;

        PlayerData playerData = GriefPrevention.instance.dataStore.getPlayerData(player.getUniqueId());
        if (playerData == null) {
            GriefPrevention.sendMessage(player, TextMode.Err, Messages.NoPlayerData);
            return true;
        }

        if (playerData.getVisibleBoundaries() != null) {
            playerData.setVisibleBoundaries(null);
            GriefPrevention.sendMessage(player, TextMode.Success, Messages.BorderVisualizationOff);
            return true;
        }

        Claim claim = GriefPrevention.instance.dataStore.getClaimAt(player.getLocation(), true /*ignore height*/, null);
        if (claim == null)
        {
            GriefPrevention.sendMessage(player, TextMode.Err, Messages.ClaimMissing);
            return true;
        }

        BoundaryVisualization.visualizeClaim(player, claim, VisualizationType.CLAIM);
        GriefPrevention.sendMessage(player, TextMode.Success, Messages.BorderVisualizationOn);
        return  true;
    }
}
