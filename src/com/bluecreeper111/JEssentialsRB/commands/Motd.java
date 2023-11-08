package com.bluecreeper111.JEssentialsRB.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.bluecreeper111.JEssentialsRB.main.JEssentialsRB;
import com.bluecreeper111.JEssentialsRB.main.LanguageFile;
import com.bluecreeper111.JEssentialsRB.utils.QuickMessage;

public class Motd extends JERBCommand {
	
	public Motd() {
		super("motd", "motd", true);
		perm.addPermission("motd.set");
		perm.addPermission("motd.toggle");
	}

	// Run on command execution
	public void execute(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			sendMOTD(sender);
		} else {
			if (args[0].equalsIgnoreCase("toggle")) {
				if (perm.hasPermission(sender, "motd.toggle")) {
					if (args.length == 1) {
						boolean b = plugin.getConfig().getBoolean("motdOn");
						plugin.getConfig().set("motdOn", !b);
						plugin.saveConfig();
						sender.sendMessage(LanguageFile.getMessage("motdToggled")
								.replaceAll("%toggle%", Boolean.toString(!b)));
					} else {
						if (JEssentialsRB.isBoolean(args[1])) {
							boolean toggle = Boolean.parseBoolean(args[1]);
							plugin.getConfig().set("motdOn", toggle);
							plugin.saveConfig();
							sender.sendMessage(LanguageFile.getMessage("motdToggled")
									.replaceAll("%toggle%", Boolean.toString(toggle)));
						} else {
							QuickMessage.incorrectSyntax(sender, "motd toggle [true | false]");
						}
					}
				} else {
					QuickMessage.noPermission(sender);
				}
			} else if (args[0].equalsIgnoreCase("set")) {
				if (perm.hasPermission(sender, "motd.set")) {
					if (args.length > 1) {
						String motd = "";
						for (int i = 1; i < args.length; i++) {
							motd += args[i] + " ";
						}
						plugin.getConfig().set("motd", motd);
						plugin.saveConfig();
						sender.sendMessage(LanguageFile.getMessage("motdSet"));
					} else {
						QuickMessage.incorrectSyntax(sender, "motd set <message>");
					}
				} else {
					QuickMessage.noPermission(sender);
				}
			} else {
				QuickMessage.incorrectSyntax(sender, "motd [set | toggle]");
			}
		}
	}
	
	// Method for sending the MOTD
	private void sendMOTD(CommandSender sender) {
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("motd")
				.replaceAll("%player%", sender.getName())));
	}

}
