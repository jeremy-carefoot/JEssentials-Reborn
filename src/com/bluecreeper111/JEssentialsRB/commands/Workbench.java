package com.bluecreeper111.JEssentialsRB.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Workbench extends JERBCommand {
	
	public Workbench() {
		super("workbench", "workbench", false);
	}
	
    // Run on command execution
	public void execute(CommandSender sender, Command cmd, String label, String[] args) {
		((Player)sender).openWorkbench(null, true);
	}

}
