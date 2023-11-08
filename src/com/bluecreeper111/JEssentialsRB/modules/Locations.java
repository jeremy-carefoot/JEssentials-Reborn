package com.bluecreeper111.JEssentialsRB.modules;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;

import com.bluecreeper111.JEssentialsRB.main.JEssentialsRB;

public class Locations {
	
	// Sets a location to a specified file
	public static void setLocation(File file, YamlConfiguration config, Location loc, String path) {
		double x = loc.getX();
		double y = loc.getY();
		double z = loc.getZ();
		double yaw = loc.getYaw();
		double pitch = loc.getPitch();
		String world = loc.getWorld().getName();
		config.set(path + ".x", Double.valueOf(x));
		config.set(path + ".y", Double.valueOf(y));
		config.set(path + ".z", Double.valueOf(z));
		config.set(path + ".yaw", Double.valueOf(yaw));
		config.set(path + ".pitch", Double.valueOf(pitch));
		config.set(path + ".world", world);
		JEssentialsRB.saveFile(file, config);
	}
	
	// Gets a location that was previously set
	public static Location getLocation(YamlConfiguration config, String path) {
		double x = config.getDouble(path + ".x");
		double y = config.getDouble(path + ".y");
		double z = config.getDouble(path + ".z");
		double yaw = config.getDouble(path + ".yaw");
		double pitch = config.getDouble(path + ".pitch");
		String world = config.getString(path + ".world");
		Location loc = new Location(Bukkit.getWorld(world), x, y, z, (float)yaw, (float)pitch);
		return loc;
	}
	
	// Checks if a location exists
	public static boolean doesLocationExist(YamlConfiguration config, String path) {
		if (config.isSet(path + ".x")) {
			return true;
		}
		return false;
	}
	
	// Deletes a location
	public static void deleteLocation(File file, YamlConfiguration config, String path) {
		if (config.isSet(path + ".x")) {
			config.set(path, null);
			JEssentialsRB.saveFile(file, config);
		}
	}

}
