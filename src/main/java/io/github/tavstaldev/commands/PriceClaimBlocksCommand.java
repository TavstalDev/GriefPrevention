package io.github.tavstaldev.commands;

import com.griefprevention.commands.CommandHandler;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import me.ryanhamshire.GriefPrevention.Messages;
import me.ryanhamshire.GriefPrevention.TextMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PriceClaimBlocksCommand extends CommandHandler
{
    public PriceClaimBlocksCommand(@NotNull GriefPrevention plugin)
    {
        super(plugin, "priceofclaimblocks");
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

        double cost = GriefPrevention.instance.config_claims_pricePerBlock;
        GriefPrevention.sendMessage(player, TextMode.Info, Messages.PriceOfClaimBlocks, GriefPrevention.numberFormatter.format(cost));
        return  true;
    }
}