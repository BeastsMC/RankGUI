package com.Zane.RankGUI;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;

public class InventoryListener implements Listener {
    private final RankGUI plugin;

    public InventoryListener(RankGUI main) {
        this.plugin = main;
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)
                || event.getInventory().getType() != InventoryType.CHEST
                || event.getSlotType() != InventoryType.SlotType.CONTAINER) {
            return;
        }
        Player player = (Player) event.getWhoClicked();
        RankDisplay display = plugin.rankDisplays.get(player.getName());
        if (display == null || !event.getInventory().getName().equals("Ranks"))
            return; // Was click event by a Player in their ranks display

        event.setCancelled(true);
        if (event.getClick() == ClickType.LEFT) {
            List<String> commands = display.getAssociatedCommands(event.getCurrentItem());
            System.out.println(commands);
            if (commands!=null) {
                for(String command : commands) {
                    Bukkit.dispatchCommand(player, command);
                }
            }
        }
        player.closeInventory();
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getInventory().getName().equals("Ranks")) {
            plugin.rankDisplays.remove(event.getPlayer().getName());
        }
    }
}
