package io.github.tavstaldev.commands;

import com.griefprevention.commands.CommandHandler;
import io.github.tavstaldev.util.HoloUtil;
import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import me.ryanhamshire.GriefPrevention.Messages;
import me.ryanhamshire.GriefPrevention.TextMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ToggleHologramCommand extends CommandHandler
{
    public ToggleHologramCommand(@NotNull GriefPrevention plugin)
    {
        super(plugin, "togglehologram");
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

        Claim claim = GriefPrevention.instance.dataStore.getClaimAt(player.getLocation(), true /*ignore height*/, null);
        if (claim == null)
        {
            GriefPrevention.sendMessage(player, TextMode.Err, Messages.ClaimMissing);
            return true;
        }

        if (claim.getOwnerID() != player.getUniqueId())
        {
            GriefPrevention.sendMessage(player, TextMode.Err, Messages.NotYourClaim);
            return true;
        }

        if (HoloUtil.toggleHologram(claim))
        {
            GriefPrevention.sendMessage(player, TextMode.Success, Messages.HologramEnabled);
        }
        else
        {
            GriefPrevention.sendMessage(player, TextMode.Success, Messages.HologramDisabled);
        }
        return  true;
    }
}