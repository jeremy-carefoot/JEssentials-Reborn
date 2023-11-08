package com.bluecreeper111.JEssentialsRB.tabcompleters;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ChatClearTC extends JERBTab {
	
	public ChatClearTC() {
		super("chatclear", "chatclear");
	}

	// Run on tab complete
	@Override
	public List<String> tab(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> list = new ArrayList<>();
		if (args.length <= 1 && perm.hasPermission(sender, "chatclear.server")) {
			list.add("server");
		}
		return list;
	}

}
