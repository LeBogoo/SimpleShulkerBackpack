package me.lebogo.simpleshulkerbackpack;

import org.bukkit.plugin.java.JavaPlugin;

import me.lebogo.simpleshulkerbackpack.listeners.InventoryCloseListener;
import me.lebogo.simpleshulkerbackpack.listeners.PlayerInteractListener;

import java.util.logging.Logger;

public final class SimpleShulkerBackpack extends JavaPlugin {
    public static Logger LOGGER = Logger.getLogger("SimpleShulkerBackpack");

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryCloseListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
