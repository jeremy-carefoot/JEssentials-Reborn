package com.bluecreeper111.JEssentialsRB.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.JEssentialsRB.main.JEssentialsRB;
import com.bluecreeper111.JEssentialsRB.main.LanguageFile;
import com.bluecreeper111.JEssentialsRB.utils.QuickMessage;

public class Feed extends JERBCommand {
	
	public Feed() {
		super("feed", "feed", true);
		perm.addPermission("feed.others");
	}

	// Run on command execution
	public void execute(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			if (sender instanceof Player) {
				feed((Player)sender);
			} else {
				QuickMessage.onlyPlayersCanExecuteCommand();
			}
		} else {
			if (!perm.hasPermission(sender, "feed.others")) {
				QuickMessage.noPermission(sender);
			} else {
				if (JEssentialsRB.isPlayer(args[0])) {
					Player target = JEssentialsRB.getPlayer(args[0]);
					feed(target);
					sender.sendMessage(LanguageFile.getMessage("targetPlayerFed")
							.replaceAll("%target%", target.getName()));
				} else {
					QuickMessage.playerNotFound(sender, args[0]);
				}
			}
		}
		return;
	}
	
	// Feeds a player
	private void feed(Player p) {
		p.setFoodLevel(20);
		p.sendMessage(LanguageFile.getMessage("playerFed"));
	}
}
