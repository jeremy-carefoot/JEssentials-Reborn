package com.bluecreeper111.JEssentialsRB.tabcompleters;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import com.bluecreeper111.JEssentialsRB.main.JEssentialsRB;

public class JHelpTC extends JERBTab implements TabCompleter {
	
	public JHelpTC() {
		super("jhelp", "jhelp");
	}

	// Run on tab complete
	public List<String> tab(CommandSender sender, Command arg1, String arg2, String[] args) {
		List<String> list = new ArrayList<>();
		if (args.length <= 1 && perm.hasPermission(sender, "jhelp")) {
			final int largestpage = (int) Math.ceil(JEssentialsRB.getCommandMap().keySet().size() / 5.0);
			for (int i = 1; i <= largestpage; i++) {
				list.add(Integer.toString(i));
			}
			return list;
		}
		return null;
	}

}
