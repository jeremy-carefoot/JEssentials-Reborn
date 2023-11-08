package com.bluecreeper111.JEssentialsRB.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.JEssentialsRB.main.JEssentialsRB;
import com.bluecreeper111.JEssentialsRB.main.LanguageFile;
import com.bluecreeper111.JEssentialsRB.modules.SpawnModule;
import com.bluecreeper111.JEssentialsRB.utils.QuickMessage;

public class Spawn extends JERBCommand {

	private final SpawnModule spawn;

	public Spawn() {
		super("spawn", "spawn", true);
		perm.addPermission("spawn.others");
		spawn = new SpawnModule();
	}

	// Run on command execute
	public void execute(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length > 0) {
			if (!perm.hasPermission(sender, "spawn.others")) {
				QuickMessage.noPermission(sender);
			} else {
				Player target = JEssentialsRB.getPlayer(args[0]);
				if (target == null) {
					QuickMessage.playerNotFound(sender, args[0]);
				} else {
					String group = JEssentialsRB.getPlayerGroup(target);
					if (spawn.spawnSet(group)) {
						spawn.teleportToSpawn(target, group, true);
						sender.sendMessage(
								LanguageFile.getMessage("playerTeleportingSender").replaceAll("%target%", args[0]));
					} else {
						if (spawn.spawnSet(null)) {
							teleportToDefaultSpawn(target, true);
							sender.sendMessage(
									LanguageFile.getMessage("playerTeleportingSender").replaceAll("%target%", args[0]));
						} else {
							sender.sendMessage(LanguageFile.getMessage("noSpawn"));
						}
					}
				}
			}
		} else {
			if (!(sender instanceof Player)) {
				QuickMessage.onlyPlayersCanExecuteCommand();
			} else {
				Player p = (Player) sender;
				String group = JEssentialsRB.getPlayerGroup(p);
				if (spawn.spawnSet(group)) {
					spawn.teleportToSpawn(p, group, false);
				} else {
					if (spawn.spawnSet(null)) {
						teleportToDefaultSpawn(p, false);
					} else {
						p.sendMessage(LanguageFile.getMessage("noSpawn"));
					}
				}
			}
		}
	}

	// Teleports a player to the default spawnpoint
	private void teleportToDefaultSpawn(Player p, boolean byop) {
		spawn.teleportToSpawn(p, null, byop);
	}

}
