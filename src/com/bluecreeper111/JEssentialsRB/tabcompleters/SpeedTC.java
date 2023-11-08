package com.bluecreeper111.JEssentialsRB.tabcompleters;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpeedTC extends JERBTab {
	
	public SpeedTC() {
		super ("speed", "speed");
	}

	// Run on tab complete
	public List<String> tab(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> list = new ArrayList<>();
		if (!(sender instanceof Player) && args.length == 1) {
			if (perm.hasPermission(sender, "speed.fly")) {
				list.add("fly");
			} 
			if (perm.hasPermission(sender, "speed.walk")) {
				list.add("walk");
			}
			return list;
		}
		return null;
	}

}
