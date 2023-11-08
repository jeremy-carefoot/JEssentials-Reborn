package com.bluecreeper111.JEssentialsRB.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.JEssentialsRB.main.JEssentialsRB;
import com.bluecreeper111.JEssentialsRB.main.LanguageFile;
import com.bluecreeper111.JEssentialsRB.utils.QuickMessage;

public class Speed extends JERBCommand {
	
	public Speed() {
		super("speed", "speed", true);
		perm.addPermission("speed.fly");
		perm.addPermission("speed.walk");
		perm.addPermission("speed.others");
	}

	// Run on command execute
	public void execute(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 1 || args.length == 2) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (args.length == 1) {
					if (JEssentialsRB.isInt(args[0])) {
						if (!perm.hasPermission(p, !p.isFlying() ? "speed.walk" : "speed.fly")) {
							QuickMessage.noPermission(sender);
						} else {
							setSpeed(p, p, Integer.parseInt(args[0]), !p.isFlying());
						}
					} else {
						p.sendMessage(LanguageFile.getMessage("invalidNumber").replaceAll("%argument%", args[0]));
					}
				} else {
					if (args[0].equalsIgnoreCase("walk")) {
						if (!JEssentialsRB.isInt(args[1])) {
							p.sendMessage(LanguageFile.getMessage("invalidNumber").replaceAll("%argument%", args[1]));
							return;
						}
						if (perm.hasPermission(p, "speed.walk")) {
							setSpeed(p, p, Integer.parseInt(args[1]), true);
						} else {
							QuickMessage.noPermission(p);
						}
					} else if (args[0].equalsIgnoreCase("fly")) {
						if (!JEssentialsRB.isInt(args[1])) {
							p.sendMessage(LanguageFile.getMessage("invalidNumber").replaceAll("%argument%", args[1]));
							return;
						}
						if (perm.hasPermission(p, "speed.fly")) {
							setSpeed(p, p, Integer.parseInt(args[1]), false);
						} else {
							QuickMessage.noPermission(p);
						}
					} else {
						QuickMessage.incorrectSyntax(sender, "speed [fly | walk] <speed>");
					}
				}
			} else {
				QuickMessage.onlyPlayersCanExecuteCommand();
			}
		} else if (args.length == 3
				&& (args[0].equalsIgnoreCase("walk") || args[0].equalsIgnoreCase("fly"))) {
			if (!perm.hasPermission(sender, "speed.others")) {
				QuickMessage.noPermission(sender);
			} else {
				Player target = JEssentialsRB.getPlayer(args[2]);
				if (target == null) {
					QuickMessage.playerNotFound(sender, args[2]);
				} else {
					if (!JEssentialsRB.isInt(args[1])) {
						sender.sendMessage(LanguageFile.getMessage("invalidNumber").replaceAll("%argument%", args[1])); 
					} else {
						setSpeed(sender, target, Integer.parseInt(args[1]), args[0].equalsIgnoreCase("walk") ? true : false);
						sender.sendMessage(LanguageFile.getMessage("playerSpeedSwitchedSender").replaceAll("%target%", args[2])
								.replaceAll("%speed%", args[1])
									.replaceAll("%movement%", args[0].equalsIgnoreCase("walk") ? "walking" : "flying"));
					}
				}
			}
		} else {
			QuickMessage.incorrectSyntax(sender, "speed [fly | walk] <player>");
		}
	}
	
	// Sets a player's speed and sends a message
	private void setSpeed(CommandSender sender, Player p, int value, boolean walking) {
		if (value < 0 || value > 10) {
			sender.sendMessage(LanguageFile.getMessage("speedOutOfBounds"));
		} else {
			if (walking) {
				p.setWalkSpeed((float)value / 10);
			} else {
				p.setFlySpeed((float)value / 10);
			}
			p.sendMessage(LanguageFile.getMessage("playerSpeedSwitched")
					.replaceAll("%speed%", Integer.toString(value))
						.replaceAll("%movement%", walking ? "walking" : "flying"));
		}
	}

}
