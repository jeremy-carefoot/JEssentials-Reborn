package com.bluecreeper111.JEssentialsRB.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.JEssentialsRB.main.JEssentialsRB;
import com.bluecreeper111.JEssentialsRB.main.Main;
import com.bluecreeper111.JEssentialsRB.modules.AfkModule;
import com.bluecreeper111.JEssentialsRB.utils.QuickMessage;

public class Afk extends JERBCommand {
	
	private final AfkModule afk;
	
	public Afk() {
		super("afk", "afk", true);
		perm.addPermission("afk.others");
		afk = new AfkModule((Main)plugin);
	}

	// Run on command execute
	public void execute(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			if (!(sender instanceof Player)) {
				QuickMessage.onlyPlayersCanExecuteCommand();
			} else {
				Player p = (Player) sender;
				afk.toggleAfk(p, null);
			}	
		} else {
			if (!perm.hasPermission(sender, "afk.others")) {
				QuickMessage.noPermission(sender);
			} else {
				Player tar = JEssentialsRB.getPlayer(args[0]);
				if (tar == null) {
					QuickMessage.playerNotFound(sender, args[0]);
				} else {
					afk.toggleAfk(tar, null);
				}
			} 
		}
	}

}
