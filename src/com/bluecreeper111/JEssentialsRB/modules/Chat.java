package com.bluecreeper111.JEssentialsRB.modules;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.bluecreeper111.JEssentialsRB.main.Main;
import com.bluecreeper111.JEssentialsRB.main.Permissions;

public class Chat implements Listener {
	
	private final Main plugin;
	private final Permissions perm;
	
	public Chat(Main pl) {
		plugin = pl;
		perm = new Permissions(pl);
		perm.addPermission("chat.color");
	}
	
	// Converts chat color if permission is prevalent
	@EventHandler(priority = EventPriority.NORMAL)
	public void onChat1(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if (!plugin.getConfig().getBoolean("noPermissionForChatColor")) {
			if (perm.hasPermission(p, "chat.color")) {
				e.setMessage(ChatColor.translateAlternateColorCodes('&', e.getMessage()));
			}
		}
	}
}
