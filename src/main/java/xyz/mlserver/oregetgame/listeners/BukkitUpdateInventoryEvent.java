package xyz.mlserver.oregetgame.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.mlserver.oregetgame.OreGetGame;
import xyz.mlserver.oregetgame.utils.MainAPI;

public class BukkitUpdateInventoryEvent implements Listener {

    @EventHandler
    public void on(InventoryMoveItemEvent e) { MainAPI.updatePoint(); }

    @EventHandler
    public void on(InventoryPickupItemEvent e) { MainAPI.updatePoint(); }

    @EventHandler
    public void on(InventoryClickEvent e) { MainAPI.updatePoint(); }

    @EventHandler
    public void on(PlayerDropItemEvent e) { MainAPI.updatePoint(); }

    @EventHandler
    public void on(EntityPickupItemEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        MainAPI.updatePoint();
    }
}
