package com.bluecreeper111.JEssentialsRB.modules;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import com.bluecreeper111.JEssentialsRB.main.LanguageFile;
import com.bluecreeper111.JEssentialsRB.main.Main;
import com.bluecreeper111.JEssentialsRB.main.Permissions;

public class Teleportation implements Listener {
	
	private static Main plugin;
	
	public Teleportation(Main pl) {
		plugin = pl;
		teleportdelay = new HashMap<>();
		previouslocation = new HashMap<>();
		toggledoff = new HashSet<>();
		teleportsafety = new HashSet<>();
	}
	
	/* previouslocation - Stores locations of players for /back
	 * toggledoff - Players with teleportation toggled off
	 * teleportdelay - Stores information about a player for the PlayerMoveEvent (controls teleportation delay)
	 * teleportsafety = Set of players that are protected from damage
	 */ 
	private static HashMap<UUID, Location> previouslocation, teleportdelay;
	private static HashSet<UUID> toggledoff, teleportsafety;
	
	// Fetches locations set
	public static HashMap<UUID, Location> getPrevLocations() {
		return previouslocation;
	}
	
	// Toggles teleportation
	public static void toggleTeleportation(Player p) {
		if (toggledoff.contains(p.getUniqueId())) {
			toggledoff.remove(p.getUniqueId());
		} else {
			toggledoff.add(p.getUniqueId());
		} 
	}
	
	// Boolean for whether or not player has teleportation disabled
	public static boolean isTeleporationToggledOff(Player p) {
		if (toggledoff.contains(p.getUniqueId())) return true;
		return false;
	}
	
	// Predefined teleport methods for cooldowns, storing last location (/back) etc.
	public static void teleportPlayer(Player p, Location loc, boolean byop) {
		long delay = plugin.getConfig().getInt("teleportationDelay") * 20L;
		Permissions perms = new Permissions(plugin);
		// If delay is not enabled or player has bypass permission
		if (delay == 0 || perms.hasPermission(p, "tpdelay.bypass")
				|| byop) {
			getPrevLocations().put(p.getUniqueId(), p.getLocation());
			p.teleport(loc);
			p.sendMessage(LanguageFile.getMessage("teleported"));
			teleportSafety(p);
			return;
		}
		// Teleporting with delay
		p.sendMessage(LanguageFile.getMessage("teleportingIn")
				.replaceAll("%time%", Long.toString(delay / 20)));
		teleportdelay.put(p.getUniqueId(), p.getLocation());
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
		  public void run() {
			if (!teleportdelay.containsKey(p.getUniqueId())) {
				p.sendMessage(LanguageFile.getMessage("teleportCancelled"));
			} else {
				getPrevLocations().put(p.getUniqueId(), p.getLocation());
				p.teleport(loc);
				p.sendMessage(LanguageFile.getMessage("teleported"));
				teleportdelay.remove(p.getUniqueId());
				teleportSafety(p);
			}
		  }
		}, delay);
	}
	
	public static void teleportPlayer(Player p, Player target, boolean byop) {
		long delay = plugin.getConfig().getInt("teleportationDelay") * 20L;
		Permissions perms = new Permissions(plugin);
		if (delay == 0 || perms.hasPermission(p, "tpdelay.bypass")
				|| byop) {
			getPrevLocations().put(p.getUniqueId(), p.getLocation());
			p.teleport(target.getLocation());
			p.sendMessage(LanguageFile.getMessage("teleported"));
			teleportSafety(p);
			return;
		}
		p.sendMessage(LanguageFile.getMessage("teleportingIn")
				.replaceAll("%time%", Long.toString(delay / 20)));
		teleportdelay.put(p.getUniqueId(), p.getLocation());
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
		  public void run() {
			if (!teleportdelay.containsKey(p.getUniqueId())) {
				p.sendMessage(LanguageFile.getMessage("teleportCancelled"));
			} else {
				getPrevLocations().put(p.getUniqueId(), p.getLocation());
				p.teleport(target.getLocation());
				p.sendMessage(LanguageFile.getMessage("teleported"));
				teleportdelay.remove(p.getUniqueId());
				teleportSafety(p);
			}
		  }
		}, delay);
	}
	
	// Adds teleport safety to player
	private static void teleportSafety(Player p) {
		if (!plugin.getConfig().getBoolean("enable-teleport-safety")) return;
		long delay = plugin.getConfig().getInt("teleport-safety-length") * 20L;
		teleportsafety.add(p.getUniqueId());
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				teleportsafety.remove(p.getUniqueId());
			}
		}, delay);
	}
	
	// Handles detecting movement during teleport delay
	@EventHandler
	public void delay(PlayerMoveEvent e) {
		if (teleportdelay.containsKey(e.getPlayer().getUniqueId())) {
			Location ploc = teleportdelay.get(e.getPlayer().getUniqueId());
			int x = ploc.getBlockX();
			int y = ploc.getBlockY();
			int z = ploc.getBlockZ();
			if (e.getTo().getBlockX() != x || e.getTo().getBlockY() != y
					|| e.getTo().getBlockZ() != z) {
				teleportdelay.remove(e.getPlayer().getUniqueId());
			}
		}
	}
	
	// Handles detecting damage for teleport safety
	@EventHandler
	public void damage(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player p = (Player)e.getEntity();
			if (teleportsafety.contains(p.getUniqueId())) {
				e.setCancelled(true);
			}
		}
	}

}
