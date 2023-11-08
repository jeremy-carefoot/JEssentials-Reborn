package com.bluecreeper111.JEssentialsRB.main;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class JEssentialsRB {
	
	// Stores commands and their descriptions, accessed from abstract command class
	private static HashMap<String, String> commandmap = new HashMap<>();
	
	// Addes command to above map
	public static void addCommand(String command, String description) {
		commandmap.put(command, description);
	}
	
	// Returns commandmap
	public static HashMap<String, String> getCommandMap() {
		return commandmap;
	}
	
	// Gets a player from username
	public static Player getPlayer(String name) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (player.getName().equals(name)) {
				return Bukkit.getPlayer(player.getUniqueId());
			}
		}
		return null;
	}
	// Checks if a player is online with a specific name
	public static boolean isPlayer(String name) {
		Player p = getPlayer(name);
		if (p == null) return false;
		return true;
	}
	// Checks if a player is online with a specific UUID
	public static boolean isPlayer(UUID id) {
		Player p = Bukkit.getPlayer(id);
		if (p == null)return false;
		return true;
	}
	
	// Checks if a string is an int
	public static boolean isInt(String num) {
		try {
			Integer.parseInt(num);
		} catch (NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	// Checks if a string is a boolean
	public static boolean isBoolean(String string) {
		try {
			Boolean.parseBoolean(string);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	// Quick way to save a file
	public static void saveFile(File file, YamlConfiguration config) {
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Checks if a world exists by name
	public static boolean doesWorldExist(String name) {
		World world = Bukkit.getWorld(name);
		if (world == null) return false;
		return true;
	}
	
	// Gets an offline player (by name)
	public static OfflinePlayer getOfflinePlayer(String name) {
		for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
			if (p.getName().equals(name)) return p;
		}
		return null;
	}
	
	// Gets a players group
	public static String getPlayerGroup(Player p) {
		if (!Main.areVaultPermissionsEnabled()) {
			return null;
		}
		return Main.getPermissions().getPrimaryGroup(p);
	}
	
	// Quick method for coloring a string
	public static String color(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}
	
	// Formats an enum name
	public static String formatEnumName(String format) {
		String [] f = format.toLowerCase().split("_");
		String output = "";
		for (int i = 0; i < f.length; i++) {
			f[i] = f[i].substring(0, 1).toUpperCase().concat(f[i].substring(1));
			output += f[i] + " ";
		}
		return output.substring(0, output.length() - 1);
	}
	
	// Takes x amount of seconds and formats it into a string describing hours, minutes, etc.
	public static String formatSeconds(Integer s) {
		if (s < 60) {
			return s.toString() + "s";
		} else if (s < 3600) {
			int remSec = s % 60;
			return Integer.toString((s-remSec)/60) + "min " + Integer.toString(remSec) + "s";
		} else if (s < 86400) {
			int remSec = s % 3600;
			String hours = Integer.toString((s-remSec)/3600) + "h ";
			int remSec2 = remSec % 60;
			String minutes = Integer.toString((remSec-remSec2)/60) + "min ";
			return hours + minutes + Integer.toString(remSec2) + "s";
		} else if (s < 604800) {
			int remSec = s % 86400;
			String days = Integer.toString((s-remSec)/86400) + "d ";
			int remSec2 = remSec % 3600;
			String hours = Integer.toString((remSec-remSec2)/3600) + "h ";
			int remSec3 = remSec2 % 60;
			String minutes = Integer.toString((remSec2-remSec3)/60) + "min ";
			return days + hours + minutes + Integer.toString(remSec3) + "s";
		} else if (s < 2419200) {
			int remSec = s % 604800;
			String weeks = Integer.toString((s-remSec)/604800) + "w ";
			int remSec2 = remSec % 86400;
			String days = Integer.toString((remSec-remSec2)/86400) + "d ";
			int remSec3 = remSec2 % 3600;
			String hours = Integer.toString((remSec2-remSec3)/3600) + "h ";
			int remSec4 = remSec3 % 60;
			String minutes = Integer.toString((remSec3-remSec4)/60) + "min ";
			return weeks + days + hours + minutes + Integer.toString(remSec4) + "s";
		} else if (s < 29030400) {
			int remSec = s % 2419200;
			String months = Integer.toString((s-remSec)/2419200) + "mo ";
			int remSec2 = remSec % 604800;
			String weeks = Integer.toString((remSec-remSec2)/604800) + "w ";
			int remSec3 = remSec2 % 86400;
			String days = Integer.toString((remSec2-remSec3)/86400) + "d ";
			int remSec4 = remSec3 % 3600;
			String hours = Integer.toString((remSec3-remSec4)/3600) + "h ";
			int remSec5 = remSec4 % 60;
			String minutes = Integer.toString((remSec4-remSec5)/60) + "min ";
			return months + weeks + days + hours + minutes + Integer.toString(remSec5) + "s";
		} else {
			int remSec = s % 29030400;
			String years = Integer.toString((s-remSec)/29030400) + "y ";
			int remSec2 = remSec % 2419200;
			String months = Integer.toString((remSec-remSec2)/2419200) + "mo ";
			return years + months;
		}
	}

}
