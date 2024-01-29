package com.bluecreeper111.JEssentialsRB.modules;

import java.util.Date;
import java.util.UUID;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.JEssentialsRB.commands.Broadcast;
import com.bluecreeper111.JEssentialsRB.main.JEssentialsRB;
import com.bluecreeper111.JEssentialsRB.main.LanguageFile;
import com.bluecreeper111.JEssentialsRB.main.Main;
import com.bluecreeper111.JEssentialsRB.main.PlayerData;

public class BanModule {
	
	// TODO initialize in Ban command class
	
	private final BanList bans = Bukkit.getBanList(BanList.Type.NAME);
	private final BanList ipBans = Bukkit.getBanList(BanList.Type.IP);
	private boolean broadcast;
	private Main plugin;
	
	public BanModule(Main pl) {
		plugin = pl;
		broadcast = plugin.getConfig().getBoolean("broadcastBans");
	}
	
	// IP-Bans a player from the server
	public void ipBan(Player p, CommandSender banner) {
		String bannerName = banner instanceof Player ? ((Player)banner).getName() : "Console";
		String message = LanguageFile.getMessage("ipBannedConnectionScreen").replace("%player%", bannerName);
		String broadcastMessage = LanguageFile.getMessage("ipBanBroadcast")
				.replaceAll("%player%", bannerName).replaceAll("%target%", p.getName());
		ipBans.addBan(p.getAddress().getHostName(), message, null, bannerName).save();
		PlayerData.writeData(p.getUniqueId(), "ip", p.getAddress().getHostName());
		p.kickPlayer(message);
		if (broadcast) Broadcast.broadcast(broadcastMessage.split(" "));
	}
	
	// Pardons an IP-Ban from the server
	public void ipUnban(OfflinePlayer p) {
		String ip = (String) PlayerData.getData(p.getUniqueId(), "ip");
		if (ip != null && ipBans.isBanned(ip)) ipBans.pardon(ip);
	}
	
	// Checks if a player is IP banned
	public boolean isIpBanned(UUID id) {
		String ip = (String) PlayerData.getData(id, "ip");
		if (ip != null && ipBans.isBanned(ip)) return true;
		return false;
	}
	
	// Temporarily bans a player from the server
	// arg "time" is in seconds
	public void tempBanPlayer(Player p, Long time, CommandSender banner) {
		String bannerName = banner instanceof Player ? ((Player)banner).getName() : "Console";
		String message = LanguageFile.getMessage("tempBanConnectionScreen");
		// TODO make lang file temp ban broadcast message
		String broadcastMessage = LanguageFile.getMessage("tempBanBroadcast")
				.replaceAll("%target%", p.getName()).replaceAll("%time%", JEssentialsRB.formatSeconds(time)); // %time% and %target% placeholders
		bans.addBan(p.getName(), message.replaceAll("%player%", bannerName), 
				new Date(System.currentTimeMillis() + time*1000), bannerName);
		if (broadcast) Broadcast.broadcast(broadcastMessage.split(" "));
		
		// TODO implement tempban broadcast message
	}
	
	public void banPlayer(Player p, CommandSender banner) {
		// TODO implement this for regular bans
	}
	
	public void unbanPlayer(OfflinePlayer p) {
		// TODO implement this for regular unban and temp bans (should be the same)
	}
	
}
