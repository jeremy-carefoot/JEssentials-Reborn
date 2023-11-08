package com.bluecreeper111.JEssentialsRB.modules;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath implements Listener {
	
	// Adds location to /back command
	@EventHandler(priority = EventPriority.HIGH)
	public void death(PlayerDeathEvent e) {
		Player p = (Player) e.getEntity();
		Teleportation.getPrevLocations().put(p.getUniqueId(), p.getLocation());
	}

}
