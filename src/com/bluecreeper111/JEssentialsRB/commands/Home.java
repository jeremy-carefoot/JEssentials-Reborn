package com.bluecreeper111.JEssentialsRB.commands;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.JEssentialsRB.main.JEssentialsRB;
import com.bluecreeper111.JEssentialsRB.main.LanguageFile;
import com.bluecreeper111.JEssentialsRB.main.Main;
import com.bluecreeper111.JEssentialsRB.modules.HomeModule;
import com.bluecreeper111.JEssentialsRB.utils.QuickMessage;

public class Home extends JERBCommand {
	
	private final HomeModule homes;
	
	public Home() {
		super ("home", "home", false);
		perm.addPermission("home.others");
		homes = new HomeModule((Main)plugin);
	}

	// Run on command execute
	public void execute(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if (label.equalsIgnoreCase("homes")
				|| (args.length == 0 && homes.howManyHomes(p) > 1)) {
			if (homes.howManyHomes(p) == 0) {
				p.sendMessage(LanguageFile.getMessage("noHome"));
				return;
			}
			p.sendMessage("§6Homes: §r" + String.join(", ", homes.getHomes(p.getUniqueId())));
		} else {
			if (args.length == 0) {
				if (homes.howManyHomes(p) != 0) {
					homes.teleportToHome(p, homes.getHomes(p.getUniqueId()).get(0));
				} else {
					p.sendMessage(LanguageFile.getMessage("noHome"));
				}
			} else if (args.length == 1) {
				if (homes.doesHomeExist(p.getUniqueId(), args[0])) {
					 homes.teleportToHome(p, args[0]);
				} else {
					p.sendMessage(LanguageFile.getMessage("homeNotFound").replaceAll("%argument%", args[0]));
				}
			} else {
				if (!perm.hasPermission(p, "home.others")) {
					QuickMessage.noPermission(p);
				} else {
					OfflinePlayer tar = JEssentialsRB.getOfflinePlayer(args[0]);
					if (tar == null) {
						QuickMessage.playerNotFound(p, args[0]);
					} else {
						if (homes.doesHomeExist(tar.getUniqueId(), args[1])) {
							homes.teleportToOtherHome(p, tar.getUniqueId(), args[1]);
						} else {
							p.sendMessage(LanguageFile.getMessage("homeNotFound").replaceAll("%argument%", args[1]));
						}
					}
				}
			}
		}
	}
}