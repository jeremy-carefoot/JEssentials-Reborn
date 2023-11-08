package com.bluecreeper111.JEssentialsRB.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.bluecreeper111.JEssentialsRB.main.JEssentialsRB;
import com.bluecreeper111.JEssentialsRB.main.LanguageFile;
import com.bluecreeper111.JEssentialsRB.utils.QuickMessage;

public class Enchant extends JERBCommand {
	
	public Enchant() {
		super("enchant", "enchant", false);
		perm.addPermission("enchant.allowunsafe");
		perm.addPermission("enchant.*");
		for (Enchantment enchant : Enchantment.values()) {
			perm.addPermission("enchant." + enchant.getKey().getKey());
		}
	}

	// Run on command execution
	public void execute(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if (p.getInventory().getItemInMainHand() == null
				|| p.getInventory().getItemInMainHand().getType() == Material.AIR) {
			p.sendMessage(LanguageFile.getMessage("mustHoldItem"));
		} else {
			if (args.length == 2) {
				Enchantment enchant = getEnchantment(args[0]);
				if (enchant == null) {
					p.sendMessage(LanguageFile.getMessage("enchantDoesNotExist").replaceAll("%enchant%", args[0]));
				} else {
					if (plugin.getConfig().getBoolean("use-individual-permission-for-enchant")
							&& !perm.hasPermission(p, "enchant.*") && !perm.hasPermission(p, "enchant." + enchant.getKey().getKey())) {
						QuickMessage.noPermission(p);
					} else {
						if (!JEssentialsRB.isInt(args[1])) {
							p.sendMessage(LanguageFile.getMessage("invalidNumber")
									.replaceAll("%argument%", args[1]));
						} else {
							int level = Integer.parseInt(args[1]);
							if (level <= enchant.getMaxLevel() && level >= enchant.getStartLevel()
									&& enchant.canEnchantItem(p.getInventory().getItemInMainHand())) {
								enchantItem(p.getInventory().getItemInMainHand(), enchant, level, false);
								p.sendMessage(LanguageFile.getMessage("itemEnchanted").replaceAll("%enchant%", args[0])
										.replaceAll("%enchlevel%", args[1]));
							} else {
								if (!perm.hasPermission(p, "enchant.allowunsafe")) {
									QuickMessage.noPermission(p);
								} else {
									enchantItem(p.getInventory().getItemInMainHand(), enchant, level, true);
									p.sendMessage(LanguageFile.getMessage("itemEnchanted").replaceAll("%enchant%", args[0])
											.replaceAll("%enchlevel%", args[1]));
								}
							}
						}
					}
				}
			} else {
				QuickMessage.incorrectSyntax(sender, "enchant <enchantment name> [level]");
			}
		}
	}
	
	// Gets enchantment by name
	private Enchantment getEnchantment(String arg) {
		for (Enchantment enchant : Enchantment.values()) {
			if (enchant.getKey().getKey().equalsIgnoreCase(arg)) {
				return enchant;
			}
		}
		return null;
	}
	
	// Method for enchanting items
	private void enchantItem(ItemStack item, Enchantment enchant, int level, boolean unsafe) {
		if (unsafe) {
			item.addUnsafeEnchantment(enchant, level);
		} else {
			item.addEnchantment(enchant, level);
		}
	}

}
