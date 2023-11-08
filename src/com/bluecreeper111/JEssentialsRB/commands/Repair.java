package com.bluecreeper111.JEssentialsRB.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import com.bluecreeper111.JEssentialsRB.main.LanguageFile;
import com.bluecreeper111.JEssentialsRB.utils.QuickMessage;

public class Repair extends JERBCommand {

	public Repair() {
		super("repair", "repair", false);
		perm.addPermission("repair.all");
		perm.addPermission("repair.hand");
	}

    // Run on command execute
	public void execute(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		// Permission check
		if (!(perm.hasPermission(p, "repair.all") 
				&& perm.hasPermission(p, "repair.hand"))) {
			QuickMessage.noPermission(p);
		} else {
			// Detecting incorrect syntax (no args or incorrect args)
			if (args.length == 0 
					|| !(args[0].equalsIgnoreCase("all") 
							|| args[0].equalsIgnoreCase("hand"))) {
				QuickMessage.incorrectSyntax(p, label + " <hand | all>");
			} else {
				// If args[0] is not "all" we know it is "hand" due to check above 
				if (args[0].equalsIgnoreCase("all")) {
					if (perm.hasPermission(p, "repair.all")) { 
						repair(true, p);
						p.sendMessage(LanguageFile.getMessage("repairAll"));
					} else {
						QuickMessage.noPermission(p);
					}
				} else {
					if (perm.hasPermission(p, "repair.hand")) {
						repair(false, p);
						p.sendMessage(LanguageFile.getMessage("repairHand"));
					} else {
						QuickMessage.noPermission(p);
					}
				}
			}
		}
	}
	
	// Repair method for hand or all depending on boolean
	private void repair(boolean all, Player p) {
		// Repairs all inventory
		if (all) {
			repairContents(p.getInventory().getContents());
			repairContents(p.getInventory().getArmorContents());
		} else {
		// Repairs only item in hand
			repairItem(p.getInventory().getItemInMainHand());
		}
	}
	
	// Repairs all items in an array of itemstacks
	private void repairContents(ItemStack[] contents) {
		for (ItemStack i : contents) {
			repairItem(i);
		}
	}
	
	// Repairs an item
	private void repairItem(ItemStack i) {
		if (i != null &&
				i.getItemMeta() instanceof Damageable) {
			Damageable m = (Damageable) i.getItemMeta();
			m.setDamage(0);
			i.setItemMeta((ItemMeta)m);
		}
	}
}
