package com.bluecreeper111.JEssentialsRB.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.JEssentialsRB.main.JEssentialsRB;
import com.bluecreeper111.JEssentialsRB.utils.QuickMessage;

public class GetPos extends JERBCommand {
	
	public GetPos() {
		super("getpos", "getpos", true);
		perm.addPermission("getpos.others");
	}

	// Run on command execution
	public void execute(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			if (!(sender instanceof Player)) {
				QuickMessage.onlyPlayersCanExecuteCommand();
			} else {
				Location l = ((Player)sender).getLocation();
				sendCoords(sender, l.getBlockX(), l.getBlockY(), l.getBlockZ());
			}
		} else {
			if (!perm.hasPermission(sender, "getpos.others")) {
				QuickMessage.noPermission(sender);
			} else {
				Player tar = JEssentialsRB.getPlayer(args[0]);
				if (tar == null) {
					QuickMessage.playerNotFound(sender, args[0]);
				} else {
					Location l = tar.getLocation();
					sendCoords(sender, l.getBlockX(), l.getBlockY(), l.getBlockZ());
				}
			}
		}
	}
	// Method for sending coords 
	private void sendCoords(CommandSender sender, int x, int y, int z) {
		sender.sendMessage("§6Coordinates §fX: " + x + ", Y: " + y + ", Z: " + z);
	}

}