package io.github.tavstaldev.commands;

import com.griefprevention.commands.CommandHandler;
import io.github.tavstaldev.util.EconomyUtils;
import io.github.tavstaldev.util.PermissionUtils;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import me.ryanhamshire.GriefPrevention.Messages;
import me.ryanhamshire.GriefPrevention.TextMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BuyClaimBlocksCommand extends CommandHandler
{
    public BuyClaimBlocksCommand(@NotNull GriefPrevention plugin)
    {
        super(plugin, "buyclaimblocks");
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

        if (!PermissionUtils.checkPermission(player, "griefprevention.buyclaimblocks")) {
            GriefPrevention.sendMessage(player, TextMode.Err, Messages.NoPermissionForCommand);
            return true;
        }

        if (args.length != 1) {
            GriefPrevention.sendMessage(player, TextMode.Err, Messages.CommandBuyClaimBlocks);
            return true;
        }

        int amount = 1;
        if (args[0].matches("\\d+")) {
            amount = Integer.parseInt(args[0]);
            if (amount < 1)
                amount = 1;
        } else {
            GriefPrevention.sendMessage(player, TextMode.Err, Messages.InvalidAmount);
            return true;
        }

        double cost = GriefPrevention.instance.config_claims_pricePerBlock * amount;
        if (!EconomyUtils.has(player, cost)) {
            GriefPrevention.sendMessage(player, TextMode.Err, Messages.NotEnoughMoney);
            return true;
        }

        var playerData = GriefPrevention.instance.dataStore.getPlayerData(player.getUniqueId());
        int playerBlocks = playerData.getAccruedClaimBlocks();
        if (playerBlocks + amount > GriefPrevention.instance.config_claims_maxAccruedBlocks_default) {
            GriefPrevention.sendMessage(player, TextMode.Err, Messages.TooManyClaimBlocks,
                    String.valueOf(GriefPrevention.instance.config_claims_maxAccruedBlocks_default));
            return true;
        }
        EconomyUtils.withdraw(player, cost);
        playerData.accrueBlocks(amount);
        GriefPrevention.instance.dataStore.savePlayerData(player.getUniqueId(), playerData);

        GriefPrevention.sendMessage(player, TextMode.Success, Messages.BoughtClaimBlocks,
                String.valueOf(amount), GriefPrevention.numberFormatter.format(cost));
        return  true;
    }
}
