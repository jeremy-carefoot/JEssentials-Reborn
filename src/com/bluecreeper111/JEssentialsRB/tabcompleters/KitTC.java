package com.bluecreeper111.JEssentialsRB.tabcompleters;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.JEssentialsRB.modules.KitModule;

public class KitTC extends JERBTab {
	
	private KitModule kits;

	public KitTC(KitModule km) {
		super("kit", "kit");
		this.kits = km;
	}

	// Run on tab complete
	public List<String> tab(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> list = new ArrayList<>();
		if (args.length == 1) {
			if (label.equalsIgnoreCase("kit") || label.equalsIgnoreCase("kits") 
					|| label.equalsIgnoreCase("kitshow") 
						|| (label.equalsIgnoreCase("kitreset") && perm.hasPermission(sender, "kitreset"))
							|| (label.equalsIgnoreCase("kitoptions") && perm.hasPermission(sender, "kitoptions"))) {
				if (sender instanceof Player) {
					list = kits.getPermissibleKits((Player)sender, perm);
				}
			} else if (label.equalsIgnoreCase("kitdel")
					&& perm.hasPermission(sender, "kitdel")) {
				list = kits.listKits();
			} 
		} else if (args.length == 2 && label.equalsIgnoreCase("kitoptions")
				&& perm.hasPermission(sender, "kitoptions")) {
			list.add("cooldown");
			list.add("firstjoin");
		}
		return list;
	}

}
