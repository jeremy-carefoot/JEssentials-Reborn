package com.bluecreeper111.JEssentialsRB.tabcompleters;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;

public class EnchantTC extends JERBTab {
	
	public EnchantTC() {
		super("enchant", "enchant");
	}

	// Run on tab complete
	public List<String> tab(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> list = new ArrayList<>();
		if (args.length == 1) {
			if (plugin.getConfig().getBoolean("use-individual-permission-for-enchant")) {
				if (perm.hasPermission(sender, "enchant.*")) {
					for (Enchantment ench : Enchantment.values()) {
						list.add(ench.getKey().getKey());
					}
					return list;
				} else {
					for (Enchantment ench : Enchantment.values()) {
						if (perm.hasPermission(sender, "enchant." + ench.getKey().getKey())) {
							list.add(ench.getKey().getKey());
						}
					}
					return list;
				}
			} else {
				for (Enchantment ench : Enchantment.values()) {
					list.add(ench.getKey().getKey());
				}
				return list;
			}
		} else if (args.length == 2) {
			if (!perm.hasPermission(sender, "enchant.allowunsafe")) {
				Enchantment enchant = null;
				for (Enchantment ench : Enchantment.values()) {
					if (ench.getKey().getKey().equalsIgnoreCase(args[0])) {
						enchant = ench;
					}
				}
				if (enchant != null) {
					for (int i = 1; i < enchant.getMaxLevel(); i++) {
						list.add(Integer.toString(i));
					}
					return list;
				}
			}
		}
		return null;
	}

}
