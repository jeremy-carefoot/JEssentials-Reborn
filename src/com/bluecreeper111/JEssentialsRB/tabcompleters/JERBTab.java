package com.bluecreeper111.JEssentialsRB.tabcompleters;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import com.bluecreeper111.JEssentialsRB.main.Main;
import com.bluecreeper111.JEssentialsRB.main.Permissions;

public abstract class JERBTab implements TabCompleter {
	
	private final String commandName;
	private final String commandPermission;
	public static Main plugin;
	public static Permissions perm;
	
	public JERBTab(String commandName, String commandPermission) {
		this.commandName = commandName;
		this.commandPermission = commandPermission;
		plugin.getCommand(commandName).setTabCompleter(this);
	}
	
	public final static void obtainInstance(Main pl) {
		plugin = pl;
		perm = new Permissions(pl);
	}
	
	public abstract List<String> tab(CommandSender sender, Command cmd, String label, String[] args);
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if (!cmd.getName().equalsIgnoreCase(commandName)) return null;
		if (!perm.hasPermission(sender, commandPermission)) return null;
		return tab(sender, cmd, label, args);
	}

}
