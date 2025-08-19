package io.github.tavstaldev.cache;

import com.samjakob.spigui.menu.SGMenu;
import io.github.tavstaldev.gui.MainGUI;
import io.github.tavstaldev.gui.RefuelGUI;
import me.ryanhamshire.GriefPrevention.Claim;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class PlayerCache {
    private final Player _player;
    private boolean _isGUIOpened;
    private SGMenu _mainMenu;
    private int _mainPage;
    private SGMenu _refuelMenu;
    private int _refuelPage;
    private Claim _claim;

    public PlayerCache(Player player) {
        _player = player;
        _isGUIOpened = false;
        _mainMenu = null;
        _mainPage = 0;
        _refuelMenu = null;
        _refuelPage = 0;
        _claim = null;
    }

    public boolean isGUIOpened() {
        return _isGUIOpened;
    }

    public void setGUIOpened(boolean isGUIOpened) {
        _isGUIOpened = isGUIOpened;
    }

    public SGMenu getMainMenu() {
        if (_mainMenu == null) {
            _mainMenu = MainGUI.create(_player);
        }
        return _mainMenu;
    }

    public int getMainPage() {
        return _mainPage;
    }

    public void setMainPage(int kitsPage) {
        _mainPage = kitsPage;
    }

    public @Nullable Claim getClaim() {
        return _claim;
    }

    public void setClaim(Claim claim) {
        _claim = claim;
    }

    public SGMenu getRefuelMenu() {
        if (_refuelMenu == null) {
            _refuelMenu = RefuelGUI.create(_player);
        }
        return _refuelMenu;
    }

    public int getRefuelPage() {
        return _refuelPage;
    }

    public void setRefuelPage(int refuelPage) {
        _refuelPage = refuelPage;
    }
}
