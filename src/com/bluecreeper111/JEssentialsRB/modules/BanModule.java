package com.bluecreeper111.JEssentialsRB.modules;

import java.util.UUID;

import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.JEssentialsRB.commands.Broadcast;
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
		ipBans.addBan(p.getAddress().getHostName(), message, null, bannerName).save();
		PlayerData.writeData(p.getUniqueId(), "ip", p.getAddress().getHostName());
		p.kickPlayer(message);
		if (broadcast) Broadcast.broadcast(LanguageFile.getMessage("ipBanBroadcast").split(" "));
	}
	
	// Pardons an IP-Ban from the server
	public void ipUnban(OfflinePlayer p, CommandSender banner) {
		String ip = (String) PlayerData.getData(p.getUniqueId(), "ip");
		if (ip != null && ipBans.isBanned(ip)) ipBans.pardon(ip);
	}
	
	// Checks if a player is IP banned
	public boolean isIpBanned(UUID id) {
		String ip = (String) PlayerData.getData(id, "ip");
		if (ip != null && ipBans.isBanned(ip)) return true;
		return false;
	}
	
	// TODO implement temp-ban
	
	// TODO implement regular ban
}
