package com.bluecreeper111.JEssentialsRB.modules;

import java.io.File;
import java.io.IOException;

import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class SpawnModule {
	
	private static File spawnf = new File("plugins/JEssentialsRB/plugindata", "spawn.yml");
	private static YamlConfiguration spawn = YamlConfiguration.loadConfiguration(spawnf);
	
	// Generates file on startup, if not previously present
	public static void registerFile() {
		if (!spawnf.exists()) {
			try {
				spawnf.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	// Sets the spawn to a given location, for the specified group
	public void setSpawn(Location loc, String group) {
		Locations.setLocation(spawnf, spawn, loc, group == null ? "spawn" : group);
	}
	
	// Teleports the given player to the spawnpoint
	public void teleportToSpawn(Player p, String group, boolean force) {
		if (group == null) {
			Teleportation.teleportPlayer(p, Locations.getLocation(spawn, "spawn"), force);
		} else {
			Location loc = Locations.getLocation(spawn, group);
			Teleportation.teleportPlayer(p, loc, force);
		}
	}
	
	// Checks if a spawn is set
	public boolean spawnSet(String group) {
		return Locations.doesLocationExist(spawn, group == null ? "spawn" : group);
	}
	
	

}
