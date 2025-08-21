package io.github.tavstaldev.util;

import me.ryanhamshire.GriefPrevention.GriefPrevention;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utility class for handling economy-related operations using Vault.
 */
public class EconomyUtils {
    private static final Logger _logger = GriefPrevention.instance.getLogger();
    private static Economy economy = null;
    private static boolean economyEnabled;

    /**
     * Sets up the economy by registering the Vault economy provider.
     *
     * @return true if the economy provider was successfully registered, false otherwise.
     */
    public static boolean setupEconomy() {
        _logger.log(Level.FINE, "Setting up economy...");
        RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        _logger.log(Level.FINE,"Economy provider: " + economyProvider);
        if (economyProvider == null)
            return false;
        _logger.log(Level.FINE,"Economy provider found.");
        economy = economyProvider.getProvider();
        economyEnabled = true;
        return true;
    }

    /**
     * Gets the registered economy provider.
     *
     * @return the registered economy provider, or null if none is registered.
     */
    public static Economy getEconomy() {
        return economy;
    }

    /**
     * Checks if the economy is enabled.
     *
     * @return true if the economy is enabled, false otherwise.
     */
    public static boolean isEnabled() {
        return economyEnabled;
    }

    /**
     * Gets the balance of a player.
     *
     * @param player the player whose balance is to be retrieved.
     * @return the balance of the player, or 0 if the economy is not enabled.
     */
    public static double getBalance(@NotNull Player player) {
        if (getEconomy() == null)
            return 0;
        return getEconomy().getBalance(player);
    }

    /**
     * Checks if a player has a certain amount of money.
     *
     * @param player the player whose balance is to be checked.
     * @param amount the amount to check for.
     * @return true if the player has at least the specified amount, false otherwise.
     */
    public static boolean has(@NotNull Player player, double amount) {
        if (getEconomy() == null)
            return false;
        return getEconomy().has(player, amount);
    }

    /**
     * Withdraws a certain amount of money from a player's balance.
     *
     * @param player the player whose balance is to be withdrawn from.
     * @param amount the amount to withdraw.
     * @return true if the transaction was successful, false otherwise.
     */
    public static boolean withdraw(@NotNull Player player, double amount) {
        if (getEconomy() == null)
            return false;
        return getEconomy().withdrawPlayer(player, amount).transactionSuccess();
    }

    /**
     * Deposits a certain amount of money into a player's balance.
     *
     * @param player the player whose balance is to be deposited into.
     * @param amount the amount to deposit.
     * @return true if the transaction was successful, false otherwise.
     */
    public static boolean deposit(@NotNull Player player, double amount) {
        if (getEconomy() == null)
            return false;
        return getEconomy().depositPlayer(player, amount).transactionSuccess();
    }

    /**
     * Transfers a certain amount of money from one player to another.
     *
     * @param from the player to transfer money from.
     * @param to the player to transfer money to.
     * @param amount the amount to transfer.
     * @return true if the transaction was successful, false otherwise.
     */
    public static boolean transfer(@NotNull Player from, @NotNull Player to, double amount) {
        if (getEconomy() == null)
            return false;
        return getEconomy().withdrawPlayer(from, amount).transactionSuccess() && getEconomy().depositPlayer(to, amount).transactionSuccess();
    }

    /**
     * Sets a player's balance to a certain amount.
     *
     * @param player the player whose balance is to be set.
     * @param amount the amount to set the balance to.
     * @return true if the transaction was successful, false otherwise.
     */
    public static boolean set(@NotNull Player player, double amount) {
        if (getEconomy() == null)
            return false;
        return getEconomy().depositPlayer(player, amount).transactionSuccess();
    }

    /**
     * Formats a given amount of money into a string.
     *
     * @param amount the amount to format.
     * @return the formatted string, or null if the economy is not enabled.
     */
    public static String format(double amount) {
        if (getEconomy() == null)
            return null;
        return getEconomy().format(amount);
    }

    /**
     * Gets the plural name of the currency.
     *
     * @return the plural name of the currency, or null if the economy is not enabled.
     */
    public static String currencyNamePlural() {
        if (getEconomy() == null)
            return null;
        return getEconomy().currencyNamePlural();
    }

    /**
     * Gets the singular name of the currency.
     *
     * @return the singular name of the currency, or null if the economy is not enabled.
     */
    public static String currencyNameSingular() {
        if (getEconomy() == null)
            return null;
        return getEconomy().currencyNameSingular();
    }
}
