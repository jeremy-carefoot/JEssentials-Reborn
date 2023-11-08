package com.bluecreeper111.JEssentialsRB.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.bluecreeper111.JEssentialsRB.main.LanguageFile;
import com.bluecreeper111.JEssentialsRB.utils.JEItem;

public class Hat extends JERBCommand {
	
	public Hat() {
		super("hat", "hat", false);
		perm.addPermission("hat.type.");
	}

	// Run on command execution
	public void execute(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if (args.length == 1 && args[0].equalsIgnoreCase("remove")) {
			// Clears hat
			if (p.getInventory().getHelmet() != null) {
				JEItem.giveItem(p, p.getInventory().getHelmet());
				p.getInventory().setHelmet(null);
				p.sendMessage(LanguageFile.getMessage("hatRemoved"));
			} else {
				p.sendMessage(LanguageFile.getMessage("noHat"));
			}
			return;
		}
		if (plugin.getConfig().getBoolean("use-individual-item-permission-for-hat")
				 && !perm.hasPermission(p, "hat.type." + 
				p.getInventory().getItemInMainHand().getType().name().toLowerCase())) {
			p.sendMessage(LanguageFile.getMessage("hatRestricted"));
			return;
		}
		if (p.getInventory().getItemInMainHand().getType() == Material.AIR
				|| p.getInventory().getItemInMainHand() == null) {
			p.sendMessage(LanguageFile.getMessage("mustHoldItem"));
		} else {
			// Swaps item holding with helmet item (puts on hat)
			ItemStack item = p.getInventory().getItemInMainHand();
			ItemStack helmet = p.getInventory().getHelmet();
			p.getInventory().setHelmet(item);
			p.getInventory().setItemInMainHand(helmet);
			p.sendMessage(LanguageFile.getMessage("newHat"));
		}
	}

}
