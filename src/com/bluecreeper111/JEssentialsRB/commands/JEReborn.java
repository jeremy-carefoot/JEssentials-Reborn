package com.bluecreeper111.JEssentialsRB.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginDescriptionFile;

import com.bluecreeper111.JEssentialsRB.main.LanguageFile;
import com.bluecreeper111.JEssentialsRB.utils.QuickMessage;

public class JEReborn extends JERBCommand {
	
	public JEReborn() {
		super("jereborn", "jereborn", true);
		perm.addPermission("jereborn.reload");
	}

	// Run on command execution
	public void execute(CommandSender sender, Command cmd, String label, String[] args) {
		PluginDescriptionFile pdf = plugin.getDescription();
		if (args.length == 0 ||
				(args.length == 1 && args[0].equalsIgnoreCase("info"))) {
			sender.sendMessage("§aRunning §2Just Essentials §6Reborn §aversion §7V." + pdf.getVersion()
			+ "§a developed by §7" + pdf.getAuthors().get(0));
		} else if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
			if (!perm.hasPermission(sender, "jereborn.reload")) {
				QuickMessage.noPermission(sender);
			} else {
				plugin.reloadConfig();
				sender.sendMessage(LanguageFile.getMessage("reloadConfig"));
			}
		} else {
			QuickMessage.incorrectSyntax(sender, label + " [reload | info]");
		}
	}
}
