package com.bluecreeper111.JEssentialsRB.tabcompleters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class MotdTC extends JERBTab {
	
	public MotdTC() {
		super("motd", "motd");
	}

	// Run on tab complete
	public List<String> tab(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> list = new ArrayList<>();
		if (args.length == 1) {
			if (perm.hasPermission(sender, "motd.set")) {
				list.add("set");
			} 
			if (perm.hasPermission(sender, "motd.toggle")) {
				list.add("toggle");
			}
			return list;
		} else if (args.length == 2) {
			if (perm.hasPermission(sender, "motd.toggle") && args[0].equalsIgnoreCase("toggle")) {
				return Arrays.asList("true", "false");
			}
		}
		return null;
	}

}
