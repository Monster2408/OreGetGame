package xyz.mlserver.oregetgame.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import xyz.mlserver.oregetgame.utils.MainAPI;

public class BukkitUpdateInventoryEvent implements Listener {

    @EventHandler
    public void on(InventoryMoveItemEvent e) {
        MainAPI.updatePoint();
    }

    @EventHandler
    public void on(InventoryPickupItemEvent e) {
        MainAPI.updatePoint();
    }

}
