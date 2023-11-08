package com.bluecreeper111.JEssentialsRB.tabcompleters;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class TimeTC extends JERBTab {
	
	public TimeTC() {
		super("time", "time.set");
	}

	// Run on tab complete
	public List<String> tab(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> list = new ArrayList<>();
		if (args.length == 1) {
			list.add("day");
			list.add("night");
			list.add("dawn");
			return list;
		}
		if (args.length == 2) {
			for (World world : Bukkit.getWorlds()) {
				list.add(world.getName());
			}
			return list;
		}
		return null;
	}

}
