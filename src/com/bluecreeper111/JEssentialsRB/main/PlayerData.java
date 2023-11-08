package com.bluecreeper111.JEssentialsRB.main;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.configuration.file.YamlConfiguration;

// Responsible for writing and reading data for players
public class PlayerData {
	
	/* dataFile = File object for data
	 * data = YamlConfiguration object for accessing the data file
	 */
	
	private static final File dataFile = new File("plugins/JEssentialsRB/plugindata", "playerdata.yml");
	private static final YamlConfiguration data = YamlConfiguration.loadConfiguration(dataFile);
	
	// Constructed in main file 
	public PlayerData() {
		checkFile();
	}
	
	// Creates data file if it doesn't exist
	private void checkFile() {
		if (!dataFile.exists()) {
			try {
				dataFile.createNewFile();
				saveFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	// Saves the data file 
	private static void saveFile() {
		try {
			data.save(dataFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Writes data for specificed player to data file
	public static void writeData(UUID id, String path, Object write) {
		data.set(id.toString() + "." + path, write);
		saveFile();
	}
	
	// Gets data and returns it as an object
	// Object can be anything, depends on the data written
	public static Object getData(UUID id, String p) {
		String path = id.toString() + "." + p;
		if (data.isSet(path)) return data.get(path);
		return null;
	}
	
}
