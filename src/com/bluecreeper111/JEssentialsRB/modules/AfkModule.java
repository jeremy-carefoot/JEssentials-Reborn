package com.bluecreeper111.JEssentialsRB.modules;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import com.bluecreeper111.JEssentialsRB.main.JEssentialsRB;
import com.bluecreeper111.JEssentialsRB.main.LanguageFile;
import com.bluecreeper111.JEssentialsRB.main.Main;
import com.bluecreeper111.JEssentialsRB.main.Permissions;
import com.sun.istack.internal.Nullable;

public class AfkModule implements Listener {
	
	/*
	 * afkmove = HashMap for tracking when a player moves.
	 * afk = Set of players who are afk
	 * plugin = instance of Main class
	 * perm = instance of Permissions class
	 */
	
	private static HashMap<UUID, Integer> afkmove;
	private static HashSet<UUID> afk;
	private final Main plugin;
	private final Permissions perm;
	
	public AfkModule(Main pl) {
		afkmove = new HashMap<>();
		afk = new HashSet<>();
		plugin = pl;
		perm = new Permissions(pl);
		perm.addPermission("afk.kickexempt");
		perm.addPermission("afk.auto");
	}
	
	// Toggles afk for a player
	public void toggleAfk(Player p, @Nullable Boolean toggle) {
		boolean brafk = plugin.getConfig().getBoolean("broadcastAfk");
		if (toggle == null) {
			if (afk.contains(p.getUniqueId())) {
				afk.remove(p.getUniqueId());
			} else {
				afk.add(p.getUniqueId());
			}
		} else {
			if (toggle) {
				afk.add(p.getUniqueId());
			} else {
				if (afk.contains(p.getUniqueId())) {
					afk.remove(p.getUniqueId());
				} else {
					return;
				}
			}
		}
		if (brafk) {
			Bukkit.broadcastMessage(afk.contains(p.getUniqueId()) ? JEssentialsRB.color(plugin.getConfig().getString("afkBroadcastMessage").replaceAll("%player%", p.getName()))
					: JEssentialsRB.color(plugin.getConfig().getString("noLongerAfkBroadcastMessage").replaceAll("%player%", p.getName())));
		} else {
			p.sendMessage(afk.contains(p.getUniqueId()) ? LanguageFile.getMessage("youAreNowAfk") : 
				LanguageFile.getMessage("youAreNoLongerAfk"));
		}
	}
	
	// Resets a player's afk timer
	private void resetTimer(Player p) {
		long time = plugin.getConfig().getInt("time-until-afk") * 1200L;
		afkmove.put(p.getUniqueId(), Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				if (Bukkit.getPlayer(p.getUniqueId()) != null
						&& Bukkit.getPlayer(p.getUniqueId()).isOnline()) {
					if (plugin.getConfig().getBoolean("kick-on-afk")
							&& !perm.hasPermission(p, "afk.kickexempt")) {
						p.kickPlayer(JEssentialsRB.color(plugin.getConfig().getString("afk-kick-message").replaceAll("%player%", p.getName())));
					} else {
						if (perm.hasPermission(p, "afk.auto")) {
							toggleAfk(p, true);
						}
					}
				}
			}
		}, time));
	}
	
	// Resets a player's afk timer on join
	@EventHandler(priority = EventPriority.NORMAL)
	public void onJoin(PlayerJoinEvent e) {
		resetTimer(e.getPlayer());
	}
	
	// Resets a player's afk timer on move
	@EventHandler(priority = EventPriority.HIGH)
	public void onMove(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (afkmove.containsKey(p.getUniqueId())) {
			Bukkit.getScheduler().cancelTask(afkmove.get(p.getUniqueId()));
		}
		resetTimer(p);
		if (getAfkPlayers().contains(p.getUniqueId())) {
			toggleAfk(p, false);
		}
	}
	
	// Returns set of afk players
	public HashSet<UUID> getAfkPlayers() {
		return afk;
	}
}
