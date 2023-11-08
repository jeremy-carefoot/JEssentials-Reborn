package com.bluecreeper111.JEssentialsRB.tabcompleters;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class WeatherTC extends JERBTab {
	
	public WeatherTC() {
		super("weather", "weather");
	}

	// Run on tab complete
	public List<String> tab(CommandSender sender, Command cmd, String label, String[] args) {
		List<String> list = new ArrayList<>();
		if (args.length <= 1) {
			list.add("sun");
			list.add("storm");
			list.add("clear");
			return list;
		}
		return null;
	}

}
