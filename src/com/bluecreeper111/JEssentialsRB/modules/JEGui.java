package com.bluecreeper111.JEssentialsRB.modules;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.bluecreeper111.JEssentialsRB.main.Main;

public class JEGui {
	
	private int slots;
	private UUID owner;
	private String name;
	private Inventory inventory;
	private HashMap<Integer, Runnable> actions;
	private JEGuiListener gl;
	
	// Class initialization
	public JEGui(int slots, String name, UUID id) {
		this.slots = slots;
		this.name = name;
		this.owner = id;
		this.actions = new HashMap<>();
		inventory = Bukkit.createInventory(null, slots > 54 ? 54 : slots, name);
		gl = Main.getGuiListener();
	}
	
	// Set item in a certain slot
	public void setItem(int index, ItemStack item) {
		inventory.setItem(index, item);
	}
	
	// Stores an action for gui
	public void setItemAction(int index, Runnable action) {
		actions.put(index, action);
	}
	
	// Sets the GUI to new owner
	public void setNewOwner(UUID id) {
		this.owner = id;
	}
	
	// Returns name
	public String getName() {
		return this.name;
	}
	
	// Returns size
	public int getSize() {
		return this.slots;
	}
	
	// Returns inventory object
	public Inventory getInventory() {
		return this.inventory;
	}
	
	// Returns owner's UUID 
	public UUID getOwner() {
		return this.owner;
	}
	
	// Returns hashmap of actions
	public HashMap<Integer, Runnable> getActions() {
		return this.actions;
	}
	
	// Opens Gui for owner
	public void openGui() {
		Player p = Bukkit.getPlayer(owner);
		if (p != null
				&& p.isOnline()) {
			p.openInventory(inventory);
			gl.addPlayer(this, owner);
		} 
	}

}
