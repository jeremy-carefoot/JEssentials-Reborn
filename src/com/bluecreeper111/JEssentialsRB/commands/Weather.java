package com.bluecreeper111.JEssentialsRB.commands;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.JEssentialsRB.main.LanguageFile;
import com.bluecreeper111.JEssentialsRB.utils.QuickMessage;

public class Weather extends JERBCommand {
	
	public Weather() {
		super("weather", "weather", false);
	}

	// Run on command execute
	public void execute(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;
		if (label.equalsIgnoreCase("sun") || label.equalsIgnoreCase("sky")
				|| (args.length == 1 && (args[0].equalsIgnoreCase("sun") 
						|| args[0].equalsIgnoreCase("clear")))) {
			toggleWeather(p.getWorld(), false);
			p.sendMessage(LanguageFile.getMessage("weatherSunny"));
		} else if (label.equals("storm") || label.equalsIgnoreCase("rain")
				|| (args.length == 1 && args[0].equalsIgnoreCase("storm"))) {
			toggleWeather(p.getWorld(), true);
			p.sendMessage(LanguageFile.getMessage("weatherStormy"));
		} else {
			QuickMessage.incorrectSyntax(p, "weather <storm | sun | clear>");
		}
	}
	
	// Toggles weather on or off
	private void toggleWeather(World world, boolean toggle) {
		world.setThundering(toggle);
		world.setStorm(toggle);
	}

}
