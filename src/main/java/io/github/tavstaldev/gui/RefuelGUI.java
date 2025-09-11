package io.github.tavstaldev.gui;

import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.menu.SGMenu;
import io.github.tavstaldev.Constants;
import io.github.tavstaldev.util.GuiUtils;
import io.github.tavstaldev.cache.PlayerCache;
import io.github.tavstaldev.cache.PlayerCacheManager;
import io.github.tavstaldev.util.HoloUtil;
import io.github.tavstaldev.util.TimeUtil;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import me.ryanhamshire.GriefPrevention.Messages;
import me.ryanhamshire.GriefPrevention.TextMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RefuelGUI
{
    private static final GriefPrevention _plugin = GriefPrevention.instance;
    private static final Logger _logger = _plugin.getLogger();

    private static final Integer[] SlotPlaceholders = {
            0,  1,  2,   3,   5,   6,   7,  8,
            9,                              17,
            18,                             26,
            27,                             35,
            36,                             44,
                46, 47,             51, 52, 53
    };
    // 4 - Note about the limitations
    // 45 - Close Button
    // 48 - Previous Page Button
    // 49 - Page Indicator
    // 50 - Next Page Button
    private static final Integer FuelsPerPage = 28; // Number of fuels per page

    public static SGMenu create(@NotNull Player player) {
        try {
            var playerId = player.getUniqueId();
            String translation = GriefPrevention.instance.dataStore.getMessage(Messages.GuiRefuelTitle);
            SGMenu menu = _plugin.GetGUI().create(translation, 6);

            // Create Placeholders
            SGButton placeholderButton = new SGButton(GuiUtils.createItem(Material.BLACK_STAINED_GLASS_PANE, " "));
            for (Integer slot : SlotPlaceholders) {
                menu.setButton(0, slot, placeholderButton);
            }

            // Dynamic buttons should be handled only in the refresh method

            // Close Button
            translation = GriefPrevention.instance.dataStore.getMessage(Messages.GuiClose);
            SGButton closeButton = new SGButton(
                    GuiUtils.createItem(Material.SPRUCE_DOOR, translation)
            ).withListener(event -> {
                close(player);
                PlayerCache playerData = PlayerCacheManager.getPlayerData(playerId);
                ClaimGUI.open(player, playerData.getClaim());
            });
            menu.setButton(0, 45, closeButton);

            // Previous Page Button
            translation = GriefPrevention.instance.dataStore.getMessage(Messages.GuiPreviousPage);
            SGButton prevPageButton = new SGButton(
                    GuiUtils.createItem(Material.ARROW, translation)
            ).withListener(event -> {
                PlayerCache playerData = PlayerCacheManager.getPlayerData(playerId);
                if (playerData.getRefuelPage() > 1) {
                    playerData.setRefuelPage(playerData.getRefuelPage() - 1);
                    refresh(player);
                }
            });
            menu.setButton(0, 48, prevPageButton);

            // Page Indicator
            translation = GriefPrevention.instance.dataStore.getMessage(Messages.GuiPage, "1", "1");
            SGButton pageButton = new SGButton(
                    GuiUtils.createItem(
                            Material.PAPER,
                            translation
                    )
            );
            menu.setButton(0, 49, pageButton);

            // Next Page Button
            translation = GriefPrevention.instance.dataStore.getMessage(Messages.GuiNextPage);
            SGButton nextPageButton = new SGButton(
                    GuiUtils.createItem(Material.ARROW, translation)
            ).withListener(event -> {
                PlayerCache playerData = PlayerCacheManager.getPlayerData(playerId);
                if (playerData.getClaim() == null)
                    return;

                int maxPage = 1 + Constants.FUEL.size() / FuelsPerPage;
                if (playerData.getRefuelPage() < maxPage) {
                    playerData.setRefuelPage(playerData.getRefuelPage() + 1);
                    refresh(player);
                }
            });
            menu.setButton(0, 50, nextPageButton);
            return menu;
        }
        catch (Exception ex) {
            _logger.log(Level.SEVERE, "An error occurred while creating the main GUI.");
            _logger.log(Level.SEVERE, ex.toString());
            return null;
        }
    }

    public static void open(@NotNull Player player) {
        PlayerCache playerData = PlayerCacheManager.getPlayerData(player.getUniqueId());
        // Show the GUI
        playerData.setGUIOpened(true);
        playerData.setRefuelPage(1);
        player.openInventory(playerData.getRefuelMenu().getInventory());
        refresh(player);
    }

    public static void close(@NotNull Player player) {
        PlayerCache playerData = PlayerCacheManager.getPlayerData(player.getUniqueId());
        player.closeInventory();
        playerData.setGUIOpened(false);
    }

    public static void refresh(@NotNull Player player) {
        try {
            var playerId = player.getUniqueId();
            PlayerCache playerData = PlayerCacheManager.getPlayerData(playerId);
            var menu = playerData.getRefuelMenu();
            final var claim = playerData.getClaim();
            if (claim == null)
            {
                // Should not happen, but just in case
                close(player);
                return;
            }

            String translation;
            List<String> loreList = new ArrayList<>();

            // Information
            translation = GriefPrevention.instance.dataStore.getMessage(Messages.Day, String.valueOf(Duration.ofHours(GriefPrevention.instance.config_advanced_claim_maximum_fuel_duration).toDays()));
            translation = GriefPrevention.instance.dataStore.getMessage(Messages.GuiRefuelWarningMessage, translation);
            loreList.add(translation);
            translation = GriefPrevention.instance.dataStore.getMessage(Messages.GuiRefuelWarning);
            SGButton timeInfoButton = new SGButton(
                    GuiUtils.createItem(Material.GLOWSTONE_DUST, translation, loreList)
            );
            menu.setButton(0, 4, timeInfoButton);

            // Page Indicator
            int maxPage = 1 + Constants.FUEL.size() / FuelsPerPage;
            translation = GriefPrevention.instance.dataStore.getMessage(Messages.GuiPage, String.valueOf(playerData.getRefuelPage()), String.valueOf(maxPage));
            SGButton pageButton = new SGButton(
                    GuiUtils.createItem(Material.PAPER, translation
                    )
            );
            menu.setButton(0, 49, pageButton);

            // Fuel List
            int page = playerData.getRefuelPage();
            var fuelList = Constants.FUEL.keySet().stream().toList();
            for (int i = 0; i < FuelsPerPage; i++) {
                int index = i + (page - 1) * FuelsPerPage;
                int slot = i + 10 + (2 * (i / 7));
                if (index >= fuelList.size()) {
                    menu.removeButton(0, slot);
                    continue;
                }

                final var fuelMaterial = fuelList.get(index);
                final var fuelDuration = Constants.FUEL.get(fuelMaterial);

                loreList.clear();
                translation = TimeUtil.formatDuration(fuelDuration.toSeconds());
                translation = GriefPrevention.instance.dataStore.getMessage(Messages.GuiRefuelDuration, translation);
                loreList.add(translation);
                loreList.add(" ");
                translation = GriefPrevention.instance.dataStore.getMessage(Messages.GuiRefuelLeftClick);
                loreList.add(translation);
                translation = GriefPrevention.instance.dataStore.getMessage(Messages.GuiRefuelRightClick);
                if (!translation.isEmpty())
                    loreList.add(translation);
                translation = GriefPrevention.instance.dataStore.getMessage(Messages.GuiRefuelShiftClick);
                if (!translation.isEmpty())
                    loreList.add(translation);
                var iconResult = GuiUtils.createItem(fuelMaterial, fuelMaterial.name(), loreList);
                menu.setButton(0, slot, new SGButton(iconResult).withListener((InventoryClickEvent event) -> {
                    int amountToConsume = 1;
                    if (event.isRightClick()) {
                        amountToConsume = 4;
                    }
                    else if (event.isShiftClick()) {
                        amountToConsume = 64;
                    }
                    final Duration fuelToAdd = fuelDuration.multipliedBy(amountToConsume);

                    var inventory = player.getInventory();
                    if (!inventory.contains(fuelMaterial))
                    {
                        GriefPrevention.sendMessage(player, TextMode.Err, Messages.GuiRefuelNoFuel);
                        return;
                    }

                    var remainingFuel = Duration.between(LocalDateTime.now(), claim.expirationDate);
                    var totalFuel = remainingFuel.plus(fuelToAdd);

                    // Check if the total fuel duration exceeds the maximum limit
                    if (totalFuel.toSeconds() > Duration.ofHours(GriefPrevention.instance.config_advanced_claim_maximum_fuel_duration).toSeconds()) {
                        // If it does, cap the total fuel at the maximum limit
                        var limitedExpiration = LocalDateTime.now().plus(Duration.ofHours(GriefPrevention.instance.config_advanced_claim_maximum_fuel_duration));

                        // Check if the current expiration date is already beyond the max
                        if (claim.expirationDate.isAfter(limitedExpiration)) {
                            GriefPrevention.sendMessage(player, TextMode.Err, Messages.GuiRefuelFull);
                            return;
                        }

                        // Prevent adding more fuel if the current expiration date is already close to the limit
                        if (Duration.between(claim.expirationDate, limitedExpiration).toHours() < fuelToAdd.toHours()) {
                            GriefPrevention.sendMessage(player, TextMode.Err, Messages.GuiRefuelNearLimit);
                            return;
                        }

                        // Set the new expiration date to the capped value
                        claim.expirationDate = limitedExpiration;

                    } else {
                        claim.expirationDate = claim.expirationDate.plus(fuelToAdd);
                    }


                    inventory.removeItem(new ItemStack(fuelMaterial, amountToConsume));

                    // Update the claim in the data store
                    GriefPrevention.instance.dataStore.saveClaim(claim);
                    HoloUtil.refreshHologram(claim);
                    GriefPrevention.sendMessage(player, TextMode.Success, Messages.GuiRefuelSuccess);
                }));
            }
            player.openInventory(menu.getInventory());
        }
        catch (Exception ex) {
            _logger.log(Level.SEVERE, "An error occurred while refreshing the main GUI.");
            _logger.log(Level.SEVERE, ex.toString());
        }
    }
}