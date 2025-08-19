package io.github.tavstaldev.commands;

import com.griefprevention.commands.CommandHandler;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import me.ryanhamshire.GriefPrevention.Messages;
import me.ryanhamshire.GriefPrevention.TextMode;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ClaimsCommand extends CommandHandler
{
    public ClaimsCommand(@NotNull GriefPrevention plugin)
    {
        super(plugin, "claims");
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

        if (args.length == 0) {

            GriefPrevention.sendMessage(player, TextMode.Info, Messages.Commands);
            GriefPrevention.sendMessageNoPrefix(player, TextMode.Info, Messages.CommandList);
            GriefPrevention.sendMessageNoPrefix(player, TextMode.Info, Messages.CommandCreate);
            GriefPrevention.sendMessageNoPrefix(player, TextMode.Info, Messages.CommandAbandon);
            GriefPrevention.sendMessageNoPrefix(player, TextMode.Info, Messages.CommandTrustList);
            GriefPrevention.sendMessageNoPrefix(player, TextMode.Info, Messages.CommandTrust);
            GriefPrevention.sendMessageNoPrefix(player, TextMode.Info, Messages.CommandTrustAccess);
            GriefPrevention.sendMessageNoPrefix(player, TextMode.Info, Messages.CommandTrustContainer);
            GriefPrevention.sendMessageNoPrefix(player, TextMode.Info, Messages.CommandUntrust);
            GriefPrevention.sendMessageNoPrefix(player, TextMode.Info, Messages.CommandUntrustAll);
            GriefPrevention.sendMessageNoPrefix(player, TextMode.Info, Messages.CommandToggleExplosions);
            return true;
        }

        String subcommand = args[0].toLowerCase();
        switch (subcommand)
        {
            case "create":
                return Bukkit.dispatchCommand(player, "claim");
            case "abandon":
                return Bukkit.dispatchCommand(player, "abandonclaim");
            case "list":
                return Bukkit.dispatchCommand(player, "claimlist");
            case "trust":
            {
                if (args.length < 2)
                {
                    GriefPrevention.sendMessage(player, TextMode.Info, Messages.CommandTrust);
                    return true;
                }

                String targetName = args[1];
                return Bukkit.dispatchCommand(player, "trust " + targetName);
            }
            case "access":
            {
                if (args.length < 2)
                {
                    GriefPrevention.sendMessage(player, TextMode.Info, Messages.CommandTrustAccess);
                    return true;
                }

                String targetName = args[1];
                return Bukkit.dispatchCommand(player, "accesstrust " + targetName);
            }
            case "container":
            {

                if (args.length < 2)
                {
                    GriefPrevention.sendMessage(player, TextMode.Info, Messages.CommandTrustContainer);
                    return true;
                }

                String targetName = args[1];
                return Bukkit.dispatchCommand(player, "containertrust " + targetName);
            }
            case "trusts": {

                return Bukkit.dispatchCommand(player, "trustlist");
            }
            case "untrust":
            {
                if (args.length < 2)
                {
                    GriefPrevention.sendMessage(player, TextMode.Info, Messages.CommandUntrust);
                    return true;
                }

                String targetName = args[1];
                return Bukkit.dispatchCommand(player, "untrust " + targetName);
            }
            case "untrustall":
            {
                return Bukkit.dispatchCommand(player, "untrust all");
            }
            case "explosions":
                return Bukkit.dispatchCommand(player, "claimexplosions");
        }
        return true;
    }
}
