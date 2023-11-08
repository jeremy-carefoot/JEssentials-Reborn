package com.bluecreeper111.JEssentialsRB.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.JEssentialsRB.main.JEssentialsRB;
import com.bluecreeper111.JEssentialsRB.main.LanguageFile;
import com.bluecreeper111.JEssentialsRB.utils.QuickMessage;

public class Clear extends JERBCommand {
	
	public Clear() {
		super("clear", "clear", true);
		perm.addPermission("clear.others");
		perm.addPermission("clear.multiple");
	}

	// Run on command
	public void execute(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			if (sender instanceof Player) {
				clearInv((Player)sender);
			} else {
				QuickMessage.onlyPlayersCanExecuteCommand();
			}
		} else {
			// If clearing multiple players inventories
			if (args.length < 1) {
				if (perm.hasPermission(sender, "clear.multiple")) {
					for (int i = 0; i < args.length; i++) {
						if (JEssentialsRB.isPlayer(args[i])) {
							clearInv(JEssentialsRB.getPlayer(args[i]));
							sender.sendMessage(LanguageFile.getMessage("targetInventoryCleared")
									.replaceAll("%target%", args[i]));
							continue;
						}
						QuickMessage.playerNotFound(sender, args[i]);
						continue;
					}
				} else {
					QuickMessage.noPermission(sender);
				}
			} else {
				// If clearing a singular players inventory
				if (perm.hasPermission(sender, "clear.others")) {
					if (JEssentialsRB.isPlayer(args[0])) {
						clearInv(JEssentialsRB.getPlayer(args[0]));
						sender.sendMessage(LanguageFile.getMessage("targetInventoryCleared")
								.replaceAll("%target%", args[0]));
					} else {
						QuickMessage.playerNotFound(sender, args[0]);
					}
				} else {
					QuickMessage.noPermission(sender);
				}
			}
		}
		return;
	}
	
	// Method for clearing a players inventory
	private void clearInv(Player p) {
		p.getInventory().clear();
		p.getInventory().setHelmet(null);
		p.getInventory().setChestplate(null);
		p.getInventory().setLeggings(null);
		p.getInventory().setBoots(null);
		p.sendMessage(LanguageFile.getMessage("inventoryCleared"));
	}
}
