package com.bluecreeper111.JEssentialsRB.main;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.bluecreeper111.JEssentialsRB.utils.Console;

public class LanguageFile {
	
	private static File languageFile;
	private static YamlConfiguration lang;
	private static final Console log = new Console("§6[JEssentialsRB]");
	
	// Generates lang.yml File
	public static void initializeLanguageFile(JavaPlugin pl) {
		languageFile = new File(pl.getDataFolder(), "lang.yml");
		if (!languageFile.exists()) {
			pl.saveResource("lang.yml", false);
		}
		lang = YamlConfiguration.loadConfiguration(languageFile);
	}
	
	// Used to access messages inside lang.yml file
	public static String getMessage(String messageTitle) {
		if (lang.isSet(messageTitle)) {
			return ChatColor.translateAlternateColorCodes('&', lang.getString(messageTitle));
		} else {
			log.info("§4Internal Error: §cCould not find message §7" + messageTitle + "§c, please make sure your lang.yml file isn't corrupt!");
			return "";
		}
	}
	
	// Used to access language file configuration directly
	public static YamlConfiguration getLangFile() {
		return lang;
	}
	
	// Used to access actual language file
	public static File getLang() {
		return languageFile;
	}


}