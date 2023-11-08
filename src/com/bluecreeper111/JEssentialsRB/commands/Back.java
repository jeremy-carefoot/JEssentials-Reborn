package com.bluecreeper111.JEssentialsRB.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.JEssentialsRB.main.LanguageFile;
import com.bluecreeper111.JEssentialsRB.modules.Teleportation;

public class Back extends JERBCommand {
	
	public Back() {
		super("back", "back", false);
	}
	
    // Run on command execution
	public void execute(CommandSender sender, Command cmd, String label, String[] args) {
		if (!Teleportation.getPrevLocations().containsKey(((Player)sender).getUniqueId())) {
			sender.sendMessage(LanguageFile.getMessage("noRecentTeleports"));
		} else {
			Teleportation.teleportPlayer((Player)sender, 
					Teleportation.getPrevLocations().get(((Player)sender).getUniqueId()), false);
		}
	}
}
