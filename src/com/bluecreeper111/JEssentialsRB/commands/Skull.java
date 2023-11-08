package com.bluecreeper111.JEssentialsRB.commands;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.bluecreeper111.JEssentialsRB.main.JEssentialsRB;
import com.bluecreeper111.JEssentialsRB.main.LanguageFile;
import com.bluecreeper111.JEssentialsRB.utils.JEItem;
import com.bluecreeper111.JEssentialsRB.utils.QuickMessage;

public class Skull extends JERBCommand {
	
	public Skull() {
		super("skull", "skull", false);
		perm.addPermission("skull.modify");
		perm.addPermission("skull.others");
		perm.addPermission("skull.spawn");
	}

	// Run on command execute
	public void execute(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if (args.length > 0) {
			if (!args[0].equals(p.getName())
					&& !perm.hasPermission(p, "skull.others")) {
				QuickMessage.noPermission(p);
			} else {
				if (p.getInventory().getItemInMainHand().getType() == Material.PLAYER_HEAD) {
					if (!perm.hasPermission(p, "skull.modify")) {
						QuickMessage.noPermission(p);
					} else {
						OfflinePlayer target = JEssentialsRB.getOfflinePlayer(args[0]);
						if (target == null) {
							QuickMessage.playerNotFound(p, args[0]);
						} else {
							SkullMeta skull = (SkullMeta) p.getInventory().getItemInMainHand().getItemMeta();
							skull.setOwningPlayer(target);
							p.getInventory().getItemInMainHand().setItemMeta(skull);
							p.sendMessage(LanguageFile.getMessage("skullModified")
									.replaceAll("%player%", args[0]));
						}
					}
				} else {
					if (!perm.hasPermission(p, "skull.spawn")) {
						QuickMessage.noPermission(p);
					} else {
						OfflinePlayer target = JEssentialsRB.getOfflinePlayer(args[0]);
						if (target == null) {
							QuickMessage.playerNotFound(p, args[0]);
						} else {
							ItemStack item = new ItemStack(Material.PLAYER_HEAD);
							SkullMeta skull = (SkullMeta) item.getItemMeta();
							skull.setOwningPlayer(target);
							item.setItemMeta(skull);
							JEItem.giveItem(p, item);
							p.sendMessage(LanguageFile.getMessage("givenSkull")
									.replaceAll("%player%", args[0]));
						}
					}
				}
			}
		} else {
			if (!perm.hasPermission(p, "skull.spawn")) {
				QuickMessage.noPermission(p);
			} else {
					ItemStack item = new ItemStack(Material.PLAYER_HEAD);
					SkullMeta skull = (SkullMeta) item.getItemMeta();
					skull.setOwningPlayer(p);
					item.setItemMeta(skull);
					JEItem.giveItem(p, item);
					p.sendMessage(LanguageFile.getMessage("givenSkull")
							.replaceAll("%player%", p.getName()));
			}
		}
	}

}
