package com.bluecreeper111.JEssentialsRB.commands;

import java.util.HashSet;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.bluecreeper111.JEssentialsRB.main.JEssentialsRB;
import com.bluecreeper111.JEssentialsRB.main.LanguageFile;
import com.bluecreeper111.JEssentialsRB.utils.QuickMessage;

public class God extends JERBCommand implements Listener {
	
	public God() {
		super("god", "god", true);
		perm.addPermission("god.others");
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	// godmode - Contains all players in god mode
	private final HashSet<UUID> godmode = new HashSet<>();

	// Run on command execution
	public void execute(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			if (sender instanceof Player) {
				toggleGod((Player)sender);
			} else {
				QuickMessage.onlyPlayersCanExecuteCommand();
			}
		} else {
			if (!perm.hasPermission(sender, "god.others")) {
				QuickMessage.noPermission(sender);
			} else {
				Player tar = JEssentialsRB.getPlayer(args[0]);
				if (tar == null) {
					QuickMessage.playerNotFound(sender, args[0]);
				} else {
					toggleGod(tar);
					sender.sendMessage(LanguageFile.getMessage("targetGodToggled")
							.replaceAll("%toggle%", isGod(tar) ? "enabled" : "disabled")
							.replaceAll("%target%", tar.getName()));
				}
			}
		}
	}
	
	// Toggles god mode for player
	private void toggleGod(Player p) {
		if (godmode.contains(p.getUniqueId())) {
			godmode.remove(p.getUniqueId());
			p.sendMessage(LanguageFile.getMessage("godToggled")
					.replaceAll("%toggle%", "disabled"));
		} else {
			godmode.add(p.getUniqueId());
			p.sendMessage(LanguageFile.getMessage("godToggled")
					.replaceAll("%toggle%", "enabled"));
		}
	}

	// Boolean for whether player is in god mode
	private boolean isGod(Player p) {
		if (godmode.contains(p.getUniqueId())) return true;
		return false;
	}
	
	// Checks if player is in god mode when damaged, and if he is, cancels event
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player
				&& godmode.contains(((Player)e.getEntity()).getUniqueId())) {
			e.setCancelled(true);
		}
	}
	
	// Removes player from god mode on leave
	@EventHandler
	public void leave(PlayerQuitEvent e) {
		if (godmode.contains(e.getPlayer().getUniqueId())) {
			godmode.remove(e.getPlayer().getUniqueId());
		}
	}
}
