package com.bluecreeper111.JEssentialsRB.tabcompleters;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class RepairTC extends JERBTab {

	public RepairTC() {
		super("repair", "repair");
		
	}

	// Run on tab complete
	public List<String> tab(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> list = new ArrayList<>();
		if (args.length <= 1) {
			if (perm.hasPermission(sender, "repair.all")) {
				list.add("all");
			}
			if (perm.hasPermission(sender, "repair.hand")) {
				list.add("hand");
			}
		}
		return list;
	}

}
