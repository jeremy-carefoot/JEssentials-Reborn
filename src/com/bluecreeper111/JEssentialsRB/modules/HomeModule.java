package com.bluecreeper111.JEssentialsRB.modules;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.bluecreeper111.JEssentialsRB.main.LanguageFile;
import com.bluecreeper111.JEssentialsRB.main.Main;
import com.bluecreeper111.JEssentialsRB.main.Permissions;

public class HomeModule {
	
	private static File homesf = new File("plugins/JEssentialsRB/plugindata", "homes.yml");
	private static YamlConfiguration homes = YamlConfiguration.loadConfiguration(homesf);
	private final Main plugin;
	private final Permissions perm;
	
	public HomeModule(Main pl) {
		plugin = pl;
		perm = new Permissions(plugin);
	}
	
	// Registers homes.yml file
	public static void registerFile() {
		if (!homesf.exists()) {
			try {
				homesf.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	// Sets a home for a player
	public void setHome(Player p, String name) {
		if (homes.isSet(p.getUniqueId() + "." + name)) return;
		Locations.setLocation(homesf, homes, p.getLocation(), p.getUniqueId() + "." + name);
	}
	
	// Deletes a home for a player
	public void delHome(UUID id, String name) {
		if (Locations.doesLocationExist(homes, id + "." + name)) {
			Locations.deleteLocation(homesf, homes, id + "." + name);
		}
	}
	
	// Teleports a player to their home
	public void teleportToHome(Player p, String name) {
		if (Locations.doesLocationExist(homes, p.getUniqueId() + "." + name)) {
			Location loc = Locations.getLocation(homes, p.getUniqueId() + "." + name);
			Teleportation.teleportPlayer(p, loc, false);
		}
	}
	
	
	// Boolean to detect whether or not a home exists
	public boolean doesHomeExist(UUID id, String name) {
		return Locations.doesLocationExist(homes, id + "." + name);
	}
	
	// Method to find the number of homes a player has
	public int howManyHomes(Player p) {
		if (homes.isSet(p.getUniqueId().toString())) {
			return homes.getConfigurationSection(p.getUniqueId().toString()).getKeys(false).size();
		} else {
			return 0;
		}
	}
	
	// Method to find the maximum homes a player is allowed
	public int maximumNumberOfHomes(Player p) {
		if (p.isOp() || perm.hasPermission(p, "sethome.multiple.unlimited")) return 1000;
		int i = 1;
		for (String group : plugin.getConfig().getConfigurationSection("homes").getKeys(false)) {
			if (perm.hasPermission(p, "sethome.multiple." + group)) {
				int b = plugin.getConfig().getInt("homes." + group);
				if (b > i) {
					i = b;
				}
			}
		}
		return i;
	}
	
	// Gets a list of homes
	public List<String> getHomes(UUID id) {
		if (!homes.isSet(id.toString())) return null;
		List<String> list = new ArrayList<>();
		for (String home : homes.getConfigurationSection(id.toString()).getKeys(false)) {
			list.add(home);
		}
		return list;
	}
	
	// Teleports a player to another players home (OP only)
	public void teleportToOtherHome(Player p, UUID id, String name) {
		if (Locations.doesLocationExist(homes, id + "." + name)) {
			Location loc = Locations.getLocation(homes, id + "." + name);
			Teleportation.teleportPlayer(p, loc, false);
			p.sendMessage(LanguageFile.getMessage("teleportingToHomeSender").replaceAll("%homename%", name)
					.replaceAll("%target%", Bukkit.getOfflinePlayer(id).getName()));
		}
	}

}
