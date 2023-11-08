package com.bluecreeper111.JEssentialsRB.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.JEssentialsRB.main.LanguageFile;

public class More extends JERBCommand {
	
	public More() {
		super("more", "more", false);
	}

	// Run on command execution
	public void execute(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if (p.getInventory().getItemInMainHand() == null
				|| p.getInventory().getItemInMainHand().getType() == Material.AIR) {
			p.sendMessage(LanguageFile.getMessage("mustHoldItem"));
		} else {
			p.getInventory().getItemInMainHand().setAmount(
					p.getInventory().getItemInMainHand().getMaxStackSize());
			p.sendMessage(LanguageFile.getMessage("maxStack"));
		}
	}

}
