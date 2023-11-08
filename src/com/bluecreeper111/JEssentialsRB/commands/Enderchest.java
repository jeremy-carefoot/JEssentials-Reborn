package com.bluecreeper111.JEssentialsRB.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.JEssentialsRB.main.JEssentialsRB;
import com.bluecreeper111.JEssentialsRB.utils.QuickMessage;

public class Enderchest extends JERBCommand {
	
	public Enderchest() {
		super("enderchest", "enderchest", false);
		perm.addPermission("enderchest.others");
	}

	// Run on command execution
	public void execute(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if (args.length == 0) {
			openEnderchest(p);
		} else {
			if (!perm.hasPermission(p, "enderchest.others")) {
				QuickMessage.noPermission(p);
			} else {
				Player tar = JEssentialsRB.getPlayer(args[0]);
				if (tar == null) {
					QuickMessage.playerNotFound(p, args[0]);
				} else {
					openOtherEnderchest(p, tar);
				}
			}
		}
	}
	
	// Method for opening a player's OWN enderchest
	private void openEnderchest(Player p) {
		p.openInventory(p.getEnderChest());
	}
	
	// Method for opening another player's enderchest
	private void openOtherEnderchest(Player p, Player tar) {
		p.openInventory(tar.getEnderChest());
	}
}
