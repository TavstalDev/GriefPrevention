package io.github.tavstaldev;

import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.menu.SGMenu;
import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import me.ryanhamshire.GriefPrevention.Messages;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainGUI {
    private static final GriefPrevention _plugin = GriefPrevention.instance;
    private static final Logger _logger = _plugin.getLogger();

    private static final Integer[] SlotPlaceholders = {
            0,  1,      3,      5,       7, 8,
            9,                              17,
            18,                             26,
            27,                             35,
            36,                             44,
                46, 47,             51, 52, 53
    };
    // 2 - Claim Information
    // 4 - Time Information
    // 6 - Fuel Information
    // 45 - Close Button
    // 48 - Previous Page Button
    // 49 - Page Indicator
    // 50 - Next Page Button
    private static final Integer PlayerPerPage = 28; // Number of players per page

    public static SGMenu create(@NotNull Player player) {
        try {
            var playerId = player.getUniqueId();
            String translation = GriefPrevention.instance.dataStore.getMessage(Messages.GuiTitle, "...");
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
                    GuiUtils.createItem(Material.BARRIER, translation)
            ).withListener(event -> close(player));
            menu.setButton(0, 45, closeButton);

            // Previous Page Button
            translation = GriefPrevention.instance.dataStore.getMessage(Messages.GuiPreviousPage);
            SGButton prevPageButton = new SGButton(
                    GuiUtils.createItem(Material.ARROW, translation)
            ).withListener(event -> {
                PlayerCache playerData = PlayerManager.getPlayerData(playerId);
                if (playerData.getMainPage() > 1) {
                    playerData.setMainPage(playerData.getMainPage() - 1);
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
                PlayerCache playerData = PlayerManager.getPlayerData(playerId);
                if (playerData.getClaim() == null)
                    return;

                ArrayList<String> builders = new ArrayList<>();
                ArrayList<String> containers = new ArrayList<>();
                ArrayList<String> accessors = new ArrayList<>();
                ArrayList<String> managers = new ArrayList<>();
                playerData.getClaim().getPermissions(builders, containers, accessors, managers);

                int maxPage = 1 + builders.size() / PlayerPerPage;
                if (playerData.getMainPage() < maxPage) {
                    playerData.setMainPage(playerData.getMainPage() + 1);
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

    public static void open(@NotNull Player player, Claim claim) {
        PlayerCache playerData = PlayerManager.getPlayerData(player.getUniqueId());
        // Show the GUI
        playerData.setGUIOpened(true);
        playerData.setMainPage(1);
        playerData.setClaim(claim);
        player.openInventory(playerData.getMainMenu().getInventory());

        // Update GUI title
        String translation = GriefPrevention.instance.dataStore.getMessage(Messages.GuiTitle, claim.getShortRemainingTime());
        playerData.getMainMenu().setName(translation);

        refresh(player);
    }

    public static void close(@NotNull Player player) {
        PlayerCache playerData = PlayerManager.getPlayerData(player.getUniqueId());
        player.closeInventory();
        playerData.setGUIOpened(false);
    }

    public static void refresh(@NotNull Player player) {
        try {
            var playerId = player.getUniqueId();
            PlayerCache playerData = PlayerManager.getPlayerData(playerId);
            var menu = playerData.getMainMenu();
            var claim = playerData.getClaim();
            if (claim == null)
            {
                // Should not happen, but just in case
                close(player);
                return;
            }

            ArrayList<String> builders = new ArrayList<>();
            ArrayList<String> containers = new ArrayList<>();
            ArrayList<String> accessors = new ArrayList<>();
            ArrayList<String> managers = new ArrayList<>();
            claim.getPermissions(builders, containers, accessors, managers);

            final boolean isCallerOwner = playerId.equals(claim.getOwnerID());
            @Nullable OfflinePlayer owner = null;
            if (claim.getOwnerID() != null) {
                owner = Bukkit.getOfflinePlayer(claim.getOwnerID());
            }

            String translation;
            List<String> loreList = new ArrayList<>();

            // Claim Information
            //#region Make Lore
            // Owner
            if (owner == null) {
                translation = GriefPrevention.instance.dataStore.getMessage(Messages.GuiClaimInformationNoOwner);
            } else {
                translation = GriefPrevention.instance.dataStore.getMessage(Messages.GuiClaimInformationOwner, owner.getName());
            }
            loreList.add(translation);

            // Size
            translation = GriefPrevention.instance.dataStore.getMessage(Messages.GuiClaimInformationSize, String.valueOf(claim.getWidth()), String.valueOf(claim.getHeight()));
            loreList.add(translation);

            // Members
            translation = GriefPrevention.instance.dataStore.getMessage(Messages.GuiClaimInformationMembers, String.valueOf(builders.size()));
            loreList.add(translation);
            //#endregion

            translation = GriefPrevention.instance.dataStore.getMessage(Messages.GuiClaimInformation);
            SGButton claimButton = new SGButton(
                    GuiUtils.createItem(Material.BOOK, translation, loreList)
            );
            menu.setButton(0, 2, claimButton);

            // Time Information
            loreList.clear(); // Reset lore list for next section
            //#region Make Lore
            // Remaining Time
            translation = GriefPrevention.instance.dataStore.getMessage(Messages.GuiTimeInformationRemaining, claim.getShortRemainingTime());
            loreList.add(translation);

            // Action
            if (isCallerOwner)
            {
                translation = GriefPrevention.instance.dataStore.getMessage(Messages.GuiTimeInformationAction);
                loreList.add(translation);
            }
            //#endregion

            translation = GriefPrevention.instance.dataStore.getMessage(Messages.GuiTimeInformation);
            SGButton timeInfoButton = new SGButton(
                    GuiUtils.createItem(Material.CLOCK, translation, loreList)
            ).withListener((InventoryClickEvent event) -> {
                if (!isCallerOwner)
                    return;

                RefuelGUI.open(player);
            });
            menu.setButton(0, 4, timeInfoButton);

            // Fuel Information
            loreList.clear(); // Reset lore list for next section
            //#region Make Lore
            // Remaining Time
            translation = GriefPrevention.instance.dataStore.getMessage(Messages.GuiFuelInformationRemaining, claim.getShortRemainingTime());
            loreList.add(translation);

            // Supported Materials
            loreList.add(""); // Empty line for better readability
            translation = GriefPrevention.instance.dataStore.getMessage(Messages.GuiFuelInformationMaterials);
            loreList.add(translation);

            // Materials List
            translation = GriefPrevention.instance.dataStore.getMessage(Messages.GuiFuelInformationMaterialsList);
            for (var set : Constants.FUEL.entrySet())
            {
                var material = set.getKey();
                var duration = set.getValue();
                String hourTranslation = GriefPrevention.instance.dataStore.getMessage(Messages.Hour, String.valueOf(duration.toHours()));
                loreList.add(MessageFormat.format(translation, material.name(), hourTranslation));
            }
            // Action
            if (isCallerOwner)
            {
                loreList.add(""); // Empty line for better readability
                translation = GriefPrevention.instance.dataStore.getMessage(Messages.GuiFuelInformationAction);
                loreList.add(translation);
            }
            //#endregion

            translation = GriefPrevention.instance.dataStore.getMessage(Messages.GuiFuelInformation);
            SGButton fuelInfoButton = new SGButton(
                    GuiUtils.createItem(Material.FURNACE, translation, loreList)
            ).withListener((InventoryClickEvent event) -> {
                if (!isCallerOwner)
                    return;

                RefuelGUI.open(player);
            });
            menu.setButton(0, 6, fuelInfoButton);

            // Page Indicator
            int maxPage = 1 + builders.size() / PlayerPerPage;
            translation = GriefPrevention.instance.dataStore.getMessage(Messages.GuiPage, String.valueOf(playerData.getMainPage()), String.valueOf(maxPage));
            SGButton pageButton = new SGButton(
                    GuiUtils.createItem(Material.PAPER, translation
                    )
            );
            menu.setButton(0, 49, pageButton);

            // Player List
            int page = playerData.getMainPage();
            loreList.clear();
            translation = GriefPrevention.instance.dataStore.getMessage(Messages.GuiClickToUntrust);
            loreList.add(translation);

            for (int i = 0; i < PlayerPerPage; i++) {
                int index = i + (page - 1) * PlayerPerPage;
                int slot = i + 10 + (2 * (i / 7));
                if (index >= builders.size()) {
                    menu.removeButton(0, slot);
                    continue;
                }

                var memberUuid = UUID.fromString(builders.get(index));
                OfflinePlayer member = Bukkit.getOfflinePlayer(memberUuid);

                var iconResult = GuiUtils.createItem(Material.PLAYER_HEAD, member.getName(), loreList);
                var meta = iconResult.getItemMeta();
                if (meta instanceof SkullMeta skullMeta) {
                    skullMeta.setOwnerProfile(member.getPlayerProfile());
                    iconResult.setItemMeta(meta);
                }

               menu.setButton(0, slot, new SGButton(iconResult).withListener((InventoryClickEvent event) -> {
                    Bukkit.dispatchCommand(player, "untrust " + member.getName());
                    refresh(player);
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