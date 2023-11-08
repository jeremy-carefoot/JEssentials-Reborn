package com.bluecreeper111.JEssentialsRB.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.bluecreeper111.JEssentialsRB.main.LanguageFile;

public class Broadcast extends JERBCommand {
	
	public Broadcast() {
		super ("broadcast", "broadcast", true);
	}
	
	// Run on command execution
	public void execute(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			sender.sendMessage(LanguageFile.getMessage("noBroadcastNothing"));
		} else {
			broadcast(args);
		}
	}
	
	// Method for broadcasting a message
	public static void broadcast(String[] message) {
		String full = plugin.getConfig().getString("customBroadcastPrefix") + " §r"; 
		for (String s : message) {
			full += s + " ";
		}
		Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', full));
	}
}