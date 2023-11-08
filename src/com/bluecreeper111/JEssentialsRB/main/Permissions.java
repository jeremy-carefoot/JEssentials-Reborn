package com.bluecreeper111.JEssentialsRB.main;

import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

public class Permissions {
	
	private static String prefix;
	
	public Permissions(Main pl) {
		prefix = pl.getConfig().getString("customPermissionPrefix");
	}
	
	// Fast way to add permissions
	public void addPermission(String permission) {
		new Permission(prefix + "." + permission);
	}
	
	// Easier way to check if a player has permission with custom prefix
	public boolean hasPermission(CommandSender p, String permission) {
		if (p.hasPermission(prefix + "." + permission)) return true;
		return false;
	}

}
