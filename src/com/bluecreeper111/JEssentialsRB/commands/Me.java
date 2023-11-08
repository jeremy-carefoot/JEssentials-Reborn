package com.bluecreeper111.JEssentialsRB.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.bluecreeper111.JEssentialsRB.utils.QuickMessage;

public class Me extends JERBCommand {
	
	public Me() {
		super("me", "me", false);
		perm.addPermission("me.color");
	}

	// Run on command execute
	public void execute(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			QuickMessage.incorrectSyntax(sender, "me <action>");
		} else {
			String message = "";
			for (int i = 0; i < args.length; i++) {
				message += args[i] + " ";
			}
			if (perm.hasPermission(sender, "me.color")) {
				message = ChatColor.translateAlternateColorCodes('&', message);
			}
			Bukkit.broadcastMessage("§5 * " + sender.getName() + ": " + message);
		}
	}

	
}
