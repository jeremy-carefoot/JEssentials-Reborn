package com.bluecreeper111.JEssentialsRB.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.JEssentialsRB.main.LanguageFile;
import com.bluecreeper111.JEssentialsRB.main.Main;
import com.bluecreeper111.JEssentialsRB.modules.HomeModule;

public class SetHome extends JERBCommand {

	private HomeModule homes;

	public SetHome() {
		super("sethome", "sethome", false);
		perm.addPermission("sethome.multiple.");
		perm.addPermission("sethome.multiple.unlimited");
		homes = new HomeModule((Main) plugin);
	}

	// Run on command execution
	public void execute(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if (homes.howManyHomes(p) >= homes.maximumNumberOfHomes(p)) {
			p.sendMessage(LanguageFile.getMessage("maximumHomes"));
		} else {
			boolean multiple = args.length > 0;
			if (homes.doesHomeExist(p.getUniqueId(), multiple ? args[0] : "home")) {
				p.sendMessage(LanguageFile.getMessage("homeAlreadySet"));
			} else {
				if (multiple && args[0].contains(".")) {
					p.sendMessage(LanguageFile.getMessage("characterNotAllowed").replaceAll("%char%", "."));
				} else {
					homes.setHome(p, multiple ? args[0] : "home");
					p.sendMessage(LanguageFile.getMessage(multiple ? "homeSetMultiple" : "homeSet")
							.replaceAll("%homename%", multiple ? args[0] : "home"));
				}
			}
		}
	}

}
