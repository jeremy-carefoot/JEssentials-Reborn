package com.bluecreeper111.JEssentialsRB.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.JEssentialsRB.main.JEssentialsRB;
import com.bluecreeper111.JEssentialsRB.main.LanguageFile;
import com.bluecreeper111.JEssentialsRB.utils.QuickMessage;

public class Exp extends JERBCommand {
	
	public Exp() {
		super("exp", "exp", true);
		perm.addPermission("exp.others");
		perm.addPermission("exp.give");
		perm.addPermission("exp.give.others");
		perm.addPermission("exp.set");
		perm.addPermission("exp.set.others");
	}

	// Run on command execute
	public void execute(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length >= 2) {
			if (args[0].equalsIgnoreCase("show")) {
				Player target = JEssentialsRB.getPlayer(args[1]);
				if (!perm.hasPermission(sender, "exp.others")) {
					QuickMessage.noPermission(sender);
					return;
				}
				if (target == null) {
					QuickMessage.playerNotFound(sender, args[1]);
				} else {
					float xp = target.getExp();
					sender.sendMessage(LanguageFile.getMessage("viewExp").replaceAll("%target%", args[1])
							.replaceAll("%exp%", Float.toString(xp + target.getLevel())));
				}
			} else if (args[0].equalsIgnoreCase("give")) {
				if (args.length != 3) {
					QuickMessage.incorrectSyntax(sender, "exp give <player> [amount]");
				} else {
					Player target = JEssentialsRB.getPlayer(args[1]);
					if (target == null) {
						QuickMessage.playerNotFound(sender, args[1]); 
					} else {
						if (JEssentialsRB.isInt(args[2])) {
							int xp = Integer.parseInt(args[2]);
							if (!perm.hasPermission(sender, "exp.give")) {
								QuickMessage.noPermission(sender);
							} else {
								if (!sender.getName().equals(target.getName())
										&& !perm.hasPermission(sender, "exp.give.others")) {
									QuickMessage.noPermission(sender);
								} else {
									giveExp(target, xp);
									sender.sendMessage(LanguageFile.getMessage("xpGivenSender").replaceAll("%target%", args[1])
											.replaceAll("%exp%", Integer.toString(xp)));
								}
							}
						} else {
							sender.sendMessage(LanguageFile.getMessage("invalidNumber")
									.replaceAll("%argument%", args[2]));
						}
					}
				}
			} else if (args[0].equalsIgnoreCase("set")) {
				if (args.length != 3) {
					QuickMessage.incorrectSyntax(sender, "exp set <player> [amount]");
				} else {
					if (!perm.hasPermission(sender, "exp.set")) {
						QuickMessage.noPermission(sender);
					} else {
						Player target = JEssentialsRB.getPlayer(args[1]);
						if (target == null) {
							QuickMessage.playerNotFound(sender, args[1]);
						} else {
							if (!perm.hasPermission(sender, "exp.set.others")
									&& !target.getName().equals(sender.getName())) {
								QuickMessage.noPermission(sender);
							} else {
								if (!JEssentialsRB.isInt(args[2])) {
									sender.sendMessage(LanguageFile.getMessage("invalidNumber")
											.replaceAll("%argument%", args[2]));
								} else {
									setExp(target, Integer.parseInt(args[2]));
									sender.sendMessage(LanguageFile.getMessage("xpSetSender").replaceAll("%target%", args[1])
											.replaceAll("%exp%", args[2]));
								}
							}
						}
					}
				}
			} else {
				QuickMessage.incorrectSyntax(sender, "exp [show | set | give] <player> [amount]");
			}
		} else {
			QuickMessage.incorrectSyntax(sender, "exp [show | set | give] <player> [amount]");
		}
	}
	
	// Method for giving EXP
	private void giveExp(Player p, int amount) {
		p.giveExp(amount);
		p.sendMessage(LanguageFile.getMessage("xpGiven")
				.replaceAll("%exp%", Integer.toString(amount)));
	}
	// Method for setting a players EXP
	private void setExp(Player p, int amount) {
		p.setLevel(amount);
		p.sendMessage(LanguageFile.getMessage("xpSet")
				.replaceAll("%exp%", Integer.toString(amount)));
	}

}
