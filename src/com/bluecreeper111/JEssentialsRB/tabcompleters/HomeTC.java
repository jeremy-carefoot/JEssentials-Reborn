package com.bluecreeper111.JEssentialsRB.tabcompleters;

import java.util.List;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.JEssentialsRB.main.JEssentialsRB;
import com.bluecreeper111.JEssentialsRB.modules.HomeModule;

public class HomeTC extends JERBTab {
	
	private final HomeModule homes;
	
	public HomeTC() {
		super("home", "home");
		homes = new HomeModule(plugin);
	}

	// Run on tab complete
	public List<String> tab(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if (args.length == 1) {
			return homes.getHomes(p.getUniqueId());
		} else if (args.length == 2
				&& perm.hasPermission(p, "home.others")) {
			OfflinePlayer tar = JEssentialsRB.getOfflinePlayer(args[0]);
			if (tar != null) {
				return homes.getHomes(tar.getUniqueId());
			}
		}
		return null;
	}

}
