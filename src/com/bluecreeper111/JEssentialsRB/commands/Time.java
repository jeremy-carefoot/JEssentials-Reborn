package com.bluecreeper111.JEssentialsRB.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.JEssentialsRB.main.JEssentialsRB;
import com.bluecreeper111.JEssentialsRB.main.LanguageFile;
import com.bluecreeper111.JEssentialsRB.utils.QuickMessage;

public class Time extends JERBCommand {
	
	public Time() {
		super("time", "time", false);
		perm.addPermission("time.set");
	}

	// Run on command execute
	public void execute(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if (label.equalsIgnoreCase("day") || 
				(args.length >= 1 && args[0].equalsIgnoreCase("day"))) {
			if (!perm.hasPermission(p, "time.set")) {
				QuickMessage.noPermission(p);
				return;
			}
			if (args.length == 1 || args.length == 0) {
				p.getWorld().setTime(6000L);
				p.sendMessage(LanguageFile.getMessage("time")
						.replaceAll("%world%", p.getWorld().getName()).replaceAll("%time%", "day"));
			} else {
				if (JEssentialsRB.doesWorldExist(args[1])) {
					Bukkit.getWorld(args[1]).setTime(6000L);
					p.sendMessage(LanguageFile.getMessage("time")
							.replaceAll("%world%", args[1]).replaceAll("%time%", "day"));
				} else {
					p.sendMessage(LanguageFile.getMessage("worldNotFound")
							.replaceAll("%query%", args[1]));
				}
			}
		} else if (label.equalsIgnoreCase("night") ||
				(args.length >= 1 && args[0].equalsIgnoreCase("night"))) {
			if (!perm.hasPermission(p, "time.set")) {
				QuickMessage.noPermission(p);
				return;
			}
			if (args.length == 1 || args.length == 0) { 
				p.getWorld().setTime(14000L);
				p.sendMessage(LanguageFile.getMessage("time")
						.replaceAll("%world%", p.getWorld().getName()).replaceAll("%time%", "night"));
			} else {
				if (JEssentialsRB.doesWorldExist(args[1])) {
					Bukkit.getWorld(args[1]).setTime(14000L);
					p.sendMessage(LanguageFile.getMessage("time")
							.replaceAll("%world%", args[1]).replaceAll("%time%", "night"));
				} else {
					p.sendMessage(LanguageFile.getMessage("worldNotFound")
							.replaceAll("%query%", args[1]));
				}
			}
		} else if (args.length >= 1 && args[0].equalsIgnoreCase("dawn")) {
			if (!perm.hasPermission(p, "time.set")) {
				QuickMessage.noPermission(p);
				return;
			}
			if (args.length == 1) {
				p.getWorld().setTime(0L);
				p.sendMessage(LanguageFile.getMessage("time")
						.replaceAll("%world%", p.getWorld().getName()).replaceAll("%time%", "dawn"));
			} else {
				if (JEssentialsRB.doesWorldExist(args[1])) {
					Bukkit.getWorld(args[1]).setTime(0L);
					p.sendMessage(LanguageFile.getMessage("time")
							.replaceAll("%world%", args[1]).replaceAll("%time%", "dawn"));
				} else {
					p.sendMessage(LanguageFile.getMessage("worldNotFound")
							.replaceAll("%query%", args[1]));
				}
			}
		} else if (args.length >= 1 && JEssentialsRB.isInt(args[0])) {
			if (!perm.hasPermission(p, "time.set")) {
				QuickMessage.noPermission(p);
				return;
			}
			long ticks = Long.parseLong(args[0]);
			if (args.length == 1) {
				p.getWorld().setTime(ticks);
				p.sendMessage(LanguageFile.getMessage("time")
						.replaceAll("%world%", p.getWorld().getName()).replaceAll("%time%", args[0]));
			} else {
				if (JEssentialsRB.doesWorldExist(args[1])) {
					Bukkit.getWorld(args[1]).setTime(ticks);
					p.sendMessage(LanguageFile.getMessage("time")
							.replaceAll("%world%", args[1]).replaceAll("%time%", args[0]));
				} else {
					p.sendMessage(LanguageFile.getMessage("worldNotFound")
							.replaceAll("%query%", args[1]));
				}
			}
		} else if (args.length == 0) {
			p.sendMessage(LanguageFile.getMessage("currentTime")
					.replaceAll("%time%", Long.toString(p.getWorld().getTime())));
		} else {
			QuickMessage.incorrectSyntax(p, "time <day | night | dawn> [world]");
		}
	}

}
