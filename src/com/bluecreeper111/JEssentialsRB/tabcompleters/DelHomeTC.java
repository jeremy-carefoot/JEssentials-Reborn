package com.bluecreeper111.JEssentialsRB.tabcompleters;

import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.JEssentialsRB.main.JEssentialsRB;
import com.bluecreeper111.JEssentialsRB.modules.HomeModule;

public class DelHomeTC extends JERBTab {
	
	private HomeModule homes;
	
	public DelHomeTC() {
		super("delhome", "delhome");
		homes = new HomeModule(plugin);
	}

	// Run on tab complete
	public List<String> tab(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 1
				&& sender instanceof Player) {
			return homes.getHomes(((Player)sender).getUniqueId());
		} else if (args.length == 2 && perm.hasPermission(sender, "delhome.others")) {
			OfflinePlayer tar = JEssentialsRB.getOfflinePlayer(args[0]);
			if (tar != null) {
				return homes.getHomes(tar.getUniqueId());
			}
		}
		return null;
	}

}
