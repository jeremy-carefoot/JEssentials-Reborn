package com.bluecreeper111.JEssentialsRB.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.JEssentialsRB.main.JEssentialsRB;
import com.bluecreeper111.JEssentialsRB.main.LanguageFile;
import com.bluecreeper111.JEssentialsRB.utils.QuickMessage;

public class Sudo extends JERBCommand {
	
	public Sudo() {
		super("sudo", "sudo", true);
		perm.addPermission("sudo.exempt");
		perm.addPermission("sudo.multiple");
	}

	// Run on command execution
	public void execute(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length > 1) {
			if (args[0].contains(",")) {
				if (!perm.hasPermission(sender, "sudo.multiple")) {
					QuickMessage.noPermission(sender);
				} else {
					String[] players = args[0].split(",");
					for (String player : players) {
						Player target = JEssentialsRB.getPlayer(player);
						if (target == null) {
							QuickMessage.playerNotFound(sender, player);
							continue;
						}
						sender.sendMessage(LanguageFile.getMessage("playerSudoed")
								.replaceAll("%target%", target.getName()));
						sudo(sender, target, args);
					}	
				}
			} else {
				Player target = JEssentialsRB.getPlayer(args[0]);
				if (target == null) {
					QuickMessage.playerNotFound(sender, args[0]);
				} else {
					sender.sendMessage(LanguageFile.getMessage("playerSudoed")
							.replaceAll("%target%", target.getName()));
					sudo(sender, target, args);
				}
			}
		} else {
			QuickMessage.incorrectSyntax(sender, "sudo <player> [command]");
		}
	}
	
	// Method for sudoing a player
	private void sudo(CommandSender sender, Player p, String[] args) {
		if (perm.hasPermission(p, "sudo.exempt")) {
			sender.sendMessage(LanguageFile.getMessage("playerCouldNotBeSudoed"));
		} else {
			String command = "";
			for (int i = 1; i < args.length; i++) {
				command += args[i] + " ";
			}
			if (command.startsWith("/")) {
				if (plugin.getConfig().getBoolean("allPermissionSudo")) {
					p.setOp(true);
					Bukkit.dispatchCommand(p, command.substring(1));
					p.setOp(false);
					return;
				}
				Bukkit.dispatchCommand(p, command.substring(1));
			} else {
				if (perm.hasPermission(p, "chat.color")
						&& !plugin.getConfig().getBoolean("allPermissionSudo")) {
					command = ChatColor.translateAlternateColorCodes('&', command);
				}
				p.chat(command);
			}
		}
	}

}
