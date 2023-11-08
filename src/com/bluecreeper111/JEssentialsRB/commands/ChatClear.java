package com.bluecreeper111.JEssentialsRB.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import com.bluecreeper111.JEssentialsRB.main.LanguageFile;
import com.bluecreeper111.JEssentialsRB.utils.QuickMessage;

public class ChatClear extends JERBCommand {
	
	public ChatClear() {
		super("chatclear", "chatclear", true);
		perm.addPermission("chatclear.server");
	}

	// Run on command execution
	public void execute(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			clearChat(sender, false);
		} else if (args[0].equalsIgnoreCase("server")) {
			if (perm.hasPermission(sender, "chatclear.server")) {
				clearChat(sender, true);
			} else {
				QuickMessage.noPermission(sender);
			}
		} else {
			QuickMessage.incorrectSyntax(sender, "chatclear [server]");
		}
	}
	
	// Method for clearing chat
	private void clearChat(CommandSender p, boolean server) {
		for (int i = 0; i < 70; i++) {
			if (server) {
				Bukkit.broadcastMessage(" ");
			} else {
				p.sendMessage(" ");
			}
		}
		if (server) {
			Bukkit.broadcastMessage(LanguageFile.getMessage("chatClearServer")
					.replaceAll("%player%", p.getName()));
		} else {
			p.sendMessage(LanguageFile.getMessage("chatClearIndividual"));
		}
	}
}
