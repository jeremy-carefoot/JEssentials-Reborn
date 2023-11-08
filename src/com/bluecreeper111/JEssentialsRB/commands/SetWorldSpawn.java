package com.bluecreeper111.JEssentialsRB.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.JEssentialsRB.main.LanguageFile;

public class SetWorldSpawn extends JERBCommand {
	
	public SetWorldSpawn() {
		super("setworldspawn", "setworldspawn", false);
	}

	// Run on command execution	
	public void execute(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		p.getWorld().setSpawnLocation(p.getLocation());
		p.sendMessage(LanguageFile.getMessage("worldSpawnSet"));
	}

}
