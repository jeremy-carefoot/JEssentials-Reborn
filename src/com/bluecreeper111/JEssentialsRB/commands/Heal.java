package com.bluecreeper111.JEssentialsRB.commands;

import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.JEssentialsRB.main.JEssentialsRB;
import com.bluecreeper111.JEssentialsRB.main.LanguageFile;
import com.bluecreeper111.JEssentialsRB.utils.QuickMessage;

public class Heal extends JERBCommand {
	
	public Heal() {
		super("heal", "heal", true);
		perm.addPermission("heal.others");
	}
	
	// Run on command execution
	public void execute(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			if (sender instanceof Player) {
				healPlayer((Player)sender);
			} else {
				QuickMessage.onlyPlayersCanExecuteCommand();
			}
		} else {
			if (!perm.hasPermission(sender, "heal.others")) {
				QuickMessage.noPermission(sender);
			} else {
				if (JEssentialsRB.isPlayer(args[0])) {
					Player target = JEssentialsRB.getPlayer(args[0]);
					healPlayer(target);
					sender.sendMessage(LanguageFile.getMessage("targetPlayerHealed")
							.replaceAll("%target%", target.getName()));
				} else {
					QuickMessage.playerNotFound(sender, args[0]);
				}
			}
		}
		return;
	}
	
	// Method to heal a player
	private void healPlayer(Player p) {
		p.setHealth(p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
		p.setFoodLevel(20);
		p.sendMessage(LanguageFile.getMessage("playerHealed"));
	}
}
