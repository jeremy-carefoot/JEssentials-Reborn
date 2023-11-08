package com.bluecreeper111.JEssentialsRB.utils;

import org.bukkit.Bukkit;

import com.bluecreeper111.JEssentialsRB.main.LanguageFile;

public class Console {
	
	public String prefix;
	
	public Console(String prefix) {
		this.prefix = prefix;
	}
	
	// Allows logging with a prefix
	public void info(String message) {
		Bukkit.getConsoleSender().sendMessage(prefix + "§r " + message);
	}
	
	// Allows directly sending messages to console
	public static void sendMessage(String message) {
		Bukkit.getConsoleSender().sendMessage(message);
	}
	// Allows sending messages to console from lang file
	public static void sendLang(String message) {
		Bukkit.getConsoleSender().sendMessage(LanguageFile.getMessage(message));
	}
}
