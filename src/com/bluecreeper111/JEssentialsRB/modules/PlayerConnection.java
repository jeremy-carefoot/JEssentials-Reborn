package com.bluecreeper111.JEssentialsRB.modules;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.bluecreeper111.JEssentialsRB.main.Main;

public class PlayerConnection implements Listener {
	
	// Main class instance
	private final Main plugin;
	
	public PlayerConnection(Main pl) {
		plugin = pl;
	}
	
	// Sends MOTD to joining players
	@EventHandler(priority = EventPriority.MONITOR)
	public void motdJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (plugin.getConfig().getBoolean("motdOn")) {
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("motd")
					.replaceAll("%player%", p.getName())));
		}
	}
}
