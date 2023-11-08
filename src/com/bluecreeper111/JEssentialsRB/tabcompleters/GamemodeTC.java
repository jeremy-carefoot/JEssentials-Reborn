package com.bluecreeper111.JEssentialsRB.tabcompleters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class GamemodeTC extends JERBTab {
	
	public GamemodeTC() {
		super("gamemode", "gamemode");
	}

	// Run on tab complete
	public List<String> tab(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> list = new ArrayList<>();
		if (label.equalsIgnoreCase("gamemode")) {
			if (args.length == 1) {
				if (perm.hasPermission(sender, "gamemode.all")) {
					list = Arrays.asList("creative", "survival", "adventure", "spectator");
					return list;
				}
				if (perm.hasPermission(sender, "gamemode.creative")) {
					list.add("creative");
				} else if (perm.hasPermission(sender, "gamemode.survival")) {
					list.add("survival");
				} else if (perm.hasPermission(sender, "gamemode.adventure")) {
					list.add("adventure");
				} else if (perm.hasPermission(sender, "gamemode.spectator")) {
					list.add("spectator");
				}
				return list;
			}
		}
		return null;
	}

}
