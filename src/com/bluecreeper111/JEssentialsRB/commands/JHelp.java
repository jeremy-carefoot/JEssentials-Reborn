package com.bluecreeper111.JEssentialsRB.commands;

import java.util.HashMap;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.bluecreeper111.JEssentialsRB.main.JEssentialsRB;
import com.bluecreeper111.JEssentialsRB.main.LanguageFile;

public class JHelp extends JERBCommand {
	
	public JHelp() {
		super("jhelp", "jhelp", true);
	}

	// Run when command is executed
	public void execute(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			openHelp(sender, 1);
			return;
		}
		if (!JEssentialsRB.isInt(args[0])) {
			sender.sendMessage(LanguageFile.getMessage("invalidNumber")
					.replaceAll("%argument%", args[0]));
		} else {
			openHelp(sender, Integer.parseInt(args[0]));
		}
	}
	
	// Organizes a list of all commands and sends a help menu to player
	public void openHelp(CommandSender sender, int page) {
		HashMap<String, String> commands = JEssentialsRB.getCommandMap();
		// Organizes commands into pages of 5
		int i = 0;
		int pagenumber = 1;
		HashMap<String, Integer> commandpages = new HashMap<>();
		for (String command : commands.keySet()) {
			if (i / 5 == 1) {
				pagenumber++;
				commandpages.put(command, pagenumber);
				i = 1;
				continue;
			}
			commandpages.put(command, pagenumber);
			i++;
		}
		// Detects if specified page exists
		int realpage = 0;
		for (int a : commandpages.values()) {
			if (a == page) {
				realpage = a;
				break;
			}
		}
		// Gets largest page
		int largestpage = 1;
		int lastpage = 1;
		for (int a : commandpages.values()) {
			if (a > lastpage) {
				lastpage = a;
				largestpage = a;
			}
		}
		// Sends help menu
		if (realpage == 0) {
			sender.sendMessage(LanguageFile.getMessage("pageNotFound")
					.replaceAll("%number%", Integer.toString(page)));
		} else {
			sender.sendMessage("§8§l------------+ §6JEssentials§eRB §6Help §8§l+------------");
			for (String command : commandpages.keySet()) {
				if (commandpages.get(command) == realpage) {
					sender.sendMessage("§e/" + command + "§7 - " + commands.get(command));
				}
			}
			sender.sendMessage("§8§l--------------+§r§6 Page " + page + "§6/" 
			+ largestpage + " §8§l+---------------");
		}
	}
}