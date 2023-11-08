package com.bluecreeper111.JEssentialsRB.modules;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class JEGuiListener implements Listener {
	
	private final HashMap<UUID, JEGui> openGuis = new HashMap<>();
	
	// Adds player/gui to active gui hashset
	public void addPlayer(JEGui gui, UUID id) {
		openGuis.put(id, gui);
	}
	
	// Removes player from active gui hashset
	public void removePlayer(UUID id) {
		openGuis.remove(id);
	}
	
	// Event handles when an inventory is clicked. It checks if the player who clicked is in a GUI and then executes an action if the GUI has one
	@EventHandler(priority = EventPriority.HIGH)
	public void guiEvent(InventoryClickEvent e) { 
		if (openGuis.containsKey(e.getWhoClicked().getUniqueId())) {
			JEGui gui = openGuis.get(e.getWhoClicked().getUniqueId());
			e.setCancelled(true);
			int slot = e.getSlot();
			if (gui.getActions().containsKey(slot)) {
				gui.getActions().get(slot).run();
				e.getWhoClicked().closeInventory();
			}
		}
	}
	
	// Event detects when a player closes an inventory and if it is a GUI it removes it from the hashmap of open GUIs
	@EventHandler(priority = EventPriority.HIGHEST)
	public void iClose(InventoryCloseEvent e) {
		UUID id = e.getPlayer().getUniqueId();
		if (openGuis.containsKey(id)
				&& openGuis.get(id).getInventory().equals(e.getInventory())) {
			openGuis.remove(id);
		}
	}
	
}
