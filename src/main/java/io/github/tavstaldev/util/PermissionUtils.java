package io.github.tavstaldev.util;

import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.jetbrains.annotations.Nullable;

public class PermissionUtils
{
    private static Permission perms = null;

    public static boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = Bukkit.getServer().getServicesManager().getRegistration(Permission.class);
        if (rsp == null) {
            return false;
        }
        perms = rsp.getProvider();
        return true;
    }

    public static boolean hasGroupSupport() {
        if (perms != null) {
            return perms.hasGroupSupport();
        }
        return false;
    }

    public static boolean checkOfflinePermission(OfflinePlayer player, String permission) {
        if (perms != null) {
            return perms.playerHas(null, player, permission);
        }
        return false;
    }

    public static boolean checkPermission(Player player, String permission) {
        if (perms != null) {
            return perms.playerHas(player, permission);
        }
        return false;
    }

    public static @Nullable String getPrimaryGroup(OfflinePlayer player) {
        if (perms != null) {
            if (!perms.hasGroupSupport())
                return null;
            return perms.getPrimaryGroup(null, player);
        }
        return null;
    }

    public static @Nullable String getPrimaryGroup(Player player) {
        if (perms != null) {
            if (!perms.hasGroupSupport())
                return null;
            return perms.getPrimaryGroup(player);
        }
        return null;
    }
}
