package com.bluecreeper111.JEssentialsRB.tabcompleters;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.bluecreeper111.JEssentialsRB.main.Main;

public class SetSpawnTC extends JERBTab {
	
	public SetSpawnTC() {
		super("setspawn", "setspawn");
	}

	// Run on tab complete
	public List<String> tab(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 1) {
			return Arrays.asList(Main.getPermissions().getGroups());
		}
		return null;
	}

}
