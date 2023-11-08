package com.bluecreeper111.JEssentialsRB.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.bluecreeper111.JEssentialsRB.main.JEssentialsRB;
import com.bluecreeper111.JEssentialsRB.main.LanguageFile;
import com.bluecreeper111.JEssentialsRB.utils.JEItem;
import com.bluecreeper111.JEssentialsRB.utils.QuickMessage;

public class Item extends JERBCommand {
	
	public Item() {
		super ("item", "item", false);
		perm.addPermission("item.");
		perm.addPermission("item.all");
		perm.addPermission("item.*");
	}

	// Run on command execute
	public void execute(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if (args.length >= 1) {
			if (Material.matchMaterial(args[0]) == null) {
				p.sendMessage(LanguageFile.getMessage("itemNotFound").replaceAll("%argument%", args[0]));
			} else {
				Material material = Material.matchMaterial(args[0]);
				if (plugin.getConfig().getBoolean("permissionBasedItemSpawn")
						&& !(perm.hasPermission(p, "item.all") && perm.hasPermission(p, "item.*")
								&& perm.hasPermission(p, "item." + material.name().toLowerCase()))) {
					QuickMessage.noPermission(p);
				} else {
					ItemStack item = new ItemStack(material, args.length > 1 ? (JEssentialsRB.isInt(args[1]) ? Integer.parseInt(args[1]) : 1): 1);
					JEItem.giveItem(p, item);
					p.sendMessage(LanguageFile.getMessage("itemGiven").replaceAll("%amount%", Integer.toString(item.getAmount()))
							.replaceAll("%item%", material.name().toLowerCase()));
				}
			}
		} else {
			QuickMessage.incorrectSyntax(p, "item <item> [amount]");
		}
	}
	
}
