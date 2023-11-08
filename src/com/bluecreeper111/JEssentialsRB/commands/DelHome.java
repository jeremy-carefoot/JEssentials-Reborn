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

public class DelHome extends JERBCommand {

	private HomeModule homes;

	public DelHome() {
		super("delhome", "delhome", true);
		perm.addPermission("delhome.others");
		homes = new HomeModule((Main) plugin);
	}

	// Run on command execution
	public void execute(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0 || args.length == 1) {
			if (!(sender instanceof Player)) {
				QuickMessage.onlyPlayersCanExecuteCommand();
			} else {
				Player p = (Player) sender;
				if (homes.howManyHomes(p) == 1 && args.length == 0) {
					homes.delHome(p.getUniqueId(), homes.getHomes(p.getUniqueId()).get(0));
					p.sendMessage(LanguageFile.getMessage("homeDeleted"));
				} else {
					if (args.length == 1 && homes.doesHomeExist(p.getUniqueId(), args[0])) {
						homes.delHome(p.getUniqueId(), args[0]);
						p.sendMessage(LanguageFile.getMessage("homeDeletedMultiple").replaceAll("%homename%", args[0]));
					} else {
						if (args.length == 1) {
							p.sendMessage(LanguageFile.getMessage("homeNotFound").replaceAll("%argument%", args[0]));
						} else {
							if (homes.howManyHomes(p) == 0) {
								p.sendMessage(LanguageFile.getMessage("noHome"));
							} else {
								QuickMessage.incorrectSyntax(sender, "delhome [home name]");
							}
						}
					}
				}
			}
		} else {
			if (!perm.hasPermission(sender, "delhome.others")) {
				QuickMessage.noPermission(sender);
			} else {
				OfflinePlayer tar = JEssentialsRB.getOfflinePlayer(args[0]);
				if (tar == null) {
					QuickMessage.playerNotFound(sender, args[0]);
				} else {
					if (homes.doesHomeExist(tar.getUniqueId(), args[1])) {
						homes.delHome(tar.getUniqueId(), args[1]);
						sender.sendMessage(LanguageFile.getMessage("homeDeletedSender")
								.replaceAll("%target%", tar.getName()).replaceAll("%homename%", args[1]));
					} else {
						sender.sendMessage(LanguageFile.getMessage("homeNotFound").replaceAll("%argument", args[1]));
					}
				}
			}
		}
	}

}
