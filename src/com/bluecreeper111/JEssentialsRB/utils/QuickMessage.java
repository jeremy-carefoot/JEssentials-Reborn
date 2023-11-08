package com.bluecreeper111.JEssentialsRB.utils;

import org.bukkit.command.CommandSender;
import com.bluecreeper111.JEssentialsRB.main.LanguageFile;

public class QuickMessage {
	
	public static void noPermission(CommandSender sender) {
		sender.sendMessage(LanguageFile.getMessage("noPermission"));
	}
	public static void onlyPlayersCanExecuteCommand() {
		Console.sendLang("onlyPlayerCanExecuteCommand");
	}
	public static void playerNotFound(CommandSender recipient, String query) {
		recipient.sendMessage(LanguageFile.getMessage("playerNotFound").replaceAll("%target%", query));
	}
	public static void incorrectSyntax(CommandSender sender, String syntax) {
		sender.sendMessage(LanguageFile.getMessage("incorrectSyntax")
				.replaceAll("%syntax%", "/" + syntax));
	}

}
