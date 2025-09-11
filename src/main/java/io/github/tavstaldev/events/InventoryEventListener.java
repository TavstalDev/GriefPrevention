package io.github.tavstaldev.events;

import io.github.tavstaldev.cache.PlayerCache;
import io.github.tavstaldev.cache.PlayerCacheManager;
import io.github.tavstaldev.util.GuiUtils;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class InventoryEventListener implements Listener {

    public static void init() {
        Bukkit.getPluginManager().registerEvents(new InventoryEventListener(), GriefPrevention.instance);
    }

    @EventHandler
    public void onItemPickup(InventoryPickupItemEvent event) {
        var itemStack = event.getItem().getItemStack();
        if (!GuiUtils.isDuped(itemStack))
            return;

        event.setCancelled(true);
        event.getInventory().remove(itemStack);
    }

    @EventHandler
    public void onItemSpawn(ItemSpawnEvent event) {
        var itemStack = event.getEntity().getItemStack();
        if (!GuiUtils.isDuped(itemStack))
            return;

        event.setCancelled(true);
        event.getEntity().remove();
    }

    // WORKS
    @EventHandler
    public void onItemHover(InventoryClickEvent event) {
        var itemStack = event.getCurrentItem();
        if (itemStack == null)
            return;
        if (!GuiUtils.isDuped(itemStack))
            return;

        event.setCancelled(true);
        event.getWhoClicked().getInventory().remove(itemStack);
    }

    // WORKS
    @EventHandler
    public void  onDropItem(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        PlayerCache playerData = PlayerCacheManager.getPlayerData(player.getUniqueId());
        if (playerData == null)
            return;

        if (!GuiUtils.isDuped(event.getItemDrop().getItemStack()))
            return;

        event.setCancelled(true);
        event.getItemDrop().remove();
        Bukkit.getScheduler().runTaskLater(GriefPrevention.instance, () -> {
            player.getInventory().remove(event.getItemDrop().getItemStack());
        }, 2L);
    }

    // WORKS
    @EventHandler
    public void onBlockPlace(org.bukkit.event.block.BlockPlaceEvent event) {
        Player player = event.getPlayer();
        PlayerCache playerData = PlayerCacheManager.getPlayerData(player.getUniqueId());
        if (playerData == null)
            return;

        if (!GuiUtils.isDuped(event.getItemInHand()))
            return;

        event.setCancelled(true);
        player.getInventory().remove(event.getItemInHand());
    }
}
