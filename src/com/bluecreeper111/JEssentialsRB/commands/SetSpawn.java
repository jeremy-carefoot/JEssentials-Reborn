package com.bluecreeper111.JEssentialsRB.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.JEssentialsRB.main.LanguageFile;
import com.bluecreeper111.JEssentialsRB.modules.SpawnModule;

public class SetSpawn extends JERBCommand {
	
	private final SpawnModule spawn;
	
	public SetSpawn() {
		super("setspawn", "setspawn", false);
		spawn = new SpawnModule();
		
	}

	// Run on command execution
	public void execute(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		spawn.setSpawn(p.getLocation(), args.length == 0 ? null : args[0]);
		p.sendMessage(LanguageFile.getMessage("spawnSet"));
	}

}
