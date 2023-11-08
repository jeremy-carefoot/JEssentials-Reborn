package com.bluecreeper111.JEssentialsRB.tabcompleters;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ExpTC extends JERBTab {
	
	public ExpTC() {
		super ("exp", "exp");
	}

	// Run on tab complete
	public List<String> tab(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> list = new ArrayList<>();
		if (args.length == 1) {
			if (perm.hasPermission(sender, "exp.give")) {
				list.add("give");
			}
			if (perm.hasPermission(sender, "exp.set")) {
				list.add("set");
			}
			if (perm.hasPermission(sender, "exp.others")) {
				list.add("show");
			}
			return list;
		}
		return null;
	}

}
