package com.bluecreeper111.JEssentialsRB.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.JEssentialsRB.main.JEssentialsRB;
import com.bluecreeper111.JEssentialsRB.main.LanguageFile;
import com.bluecreeper111.JEssentialsRB.utils.QuickMessage;

public class Fly extends JERBCommand {
	
	public Fly() {
		super("fly", "fly", true);
		perm.addPermission("fly.others");
	}

	// Run on command execute
	public void execute(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			if (sender instanceof Player) {
				toggleFlight((Player)sender);
			} else {
				QuickMessage.onlyPlayersCanExecuteCommand();
			}
		} else {
			if (!perm.hasPermission(sender, "fly.others")) {
				QuickMessage.noPermission(sender);
			} else {
				Player target = JEssentialsRB.getPlayer(args[0]);
				if (target == null) {
					QuickMessage.playerNotFound(sender, args[0]);
				} else {
					toggleFlight(target);
					sender.sendMessage(LanguageFile.getMessage("targetFlightToggled")
							.replaceAll("%target%", args[0])
								.replaceAll("%toggle%", target.getAllowFlight() ? "enabled" : "disabled"));
				}
			}
		}
	}
	
	// Method for toggling players flight
	private void toggleFlight(Player p) {
		p.setAllowFlight(!(p.getAllowFlight()));
		p.sendMessage(LanguageFile.getMessage("flightToggled")
				.replaceAll("%toggle%", p.getAllowFlight() ? "enabled" : "disabled"));
	}
}
