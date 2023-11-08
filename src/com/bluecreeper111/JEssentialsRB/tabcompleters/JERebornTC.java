package com.bluecreeper111.JEssentialsRB.tabcompleters;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class JERebornTC extends JERBTab {
	
	public JERebornTC() {
		super("jereborn", "jereborn");
	}

	// Run on tab complete
	public List<String> tab(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> list = new ArrayList<>();
		if (args.length <= 1) {
			list.add("info");
			if (perm.hasPermission(sender, "jereborn.reload")) {
				list.add("reload");
			}
			return list;
		}
		return null;
	}

}
