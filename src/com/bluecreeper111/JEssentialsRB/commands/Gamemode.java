package com.bluecreeper111.JEssentialsRB.commands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.JEssentialsRB.main.JEssentialsRB;
import com.bluecreeper111.JEssentialsRB.main.LanguageFile;
import com.bluecreeper111.JEssentialsRB.utils.QuickMessage;

// Enum for gamemodes
enum Gamemodes {
	CREATIVE, SURVIVAL, SPECTATOR, ADVENTURE;

	/*
	 * labels = Gamemode command aliases 
	 * arg = Gamemode command 
	 * argument gamemode = Bukkit GameMode instance
	 */
	private List<String> labels;
	private String arg;
	private GameMode gamemode;

	static {
		CREATIVE.labels = Arrays.asList("creative", "creativemode", "gmc");
		CREATIVE.arg = "creative";
		CREATIVE.gamemode = GameMode.CREATIVE;
		SURVIVAL.labels = Arrays.asList("survival", "survivalmode", "gms");
		SURVIVAL.arg = "survival";
		SURVIVAL.gamemode = GameMode.SURVIVAL;
		SPECTATOR.labels = Arrays.asList("spectator", "spectatormode", "gmsp");
		SPECTATOR.arg = "spectator";
		SPECTATOR.gamemode = GameMode.SPECTATOR;
		ADVENTURE.labels = Arrays.asList("adventure", "adventuremode", "gma");
		ADVENTURE.arg = "adventure";
		ADVENTURE.gamemode = GameMode.ADVENTURE;
	}

	// Gets command aliases
	public List<String> getLabels() {
		return labels;
	}

	// Gets the command argument
	public String getArg() {
		return arg;
	}

	// Returns the commands gamemode
	public GameMode getGamemode() {
		return gamemode;
	}
}

public class Gamemode extends JERBCommand {

	public Gamemode() {
		super("gamemode", "gamemode", true);
		perm.addPermission("gamemode.all");
		perm.addPermission("gamemode.");
		perm.addPermission("gamemode.others");
	}

	// Run on command execute
	public void execute(CommandSender sender, Command cmd, String label, String[] args) {
		for (Gamemodes gamemode : Gamemodes.values()) {
			for (String glabel : gamemode.getLabels()) {
				if (label.equalsIgnoreCase(glabel)) {
					if (args.length > 0) {
						Player target = JEssentialsRB.getPlayer(args[0]);
						if (target == null) {
							QuickMessage.playerNotFound(sender, args[0]);
							return;
						} else {
							if (!perm.hasPermission(sender, "gamemode.others")) {
								QuickMessage.noPermission(sender);
								return;
							} else {
								toggleGamemode(target, gamemode.getGamemode());
								sender.sendMessage(
										LanguageFile.getMessage("playerModeSwitched").replaceAll("%target%", args[0])
												.replaceAll("%gamemode%", gamemode.name().toLowerCase()));
								return;
							}
						}
					} else {
						if (!(sender instanceof Player)) {
							QuickMessage.onlyPlayersCanExecuteCommand();
							return;
						}
						if (!perm.hasPermission(sender, "gamemode." + gamemode.name().toLowerCase())
								&& !perm.hasPermission(sender, "gamemode.all")) {
							QuickMessage.noPermission(sender);
							return;
						} else {
							toggleGamemode((Player) sender, gamemode.getGamemode());
							return;
						}
					}
				}
			}
			if (args.length > 0 && 
					args[0].equalsIgnoreCase(gamemode.getArg())) {
				if (args.length >= 2) {
					Player target = JEssentialsRB.getPlayer(args[1]);
					if (target == null) {
						QuickMessage.playerNotFound(sender, args[1]);
						return;
					} else {
						if (!perm.hasPermission(sender, "gamemode.others")) {
							QuickMessage.noPermission(sender);
							return;
						} else {
							toggleGamemode(target, gamemode.getGamemode());
							sender.sendMessage(
									LanguageFile.getMessage("playerModeSwitched").replaceAll("%target%", args[1])
											.replaceAll("%gamemode%", gamemode.name().toLowerCase()));
							return;
						}
					}
				} else {
					if (!(sender instanceof Player)) {
						QuickMessage.onlyPlayersCanExecuteCommand();
						return;
					}
					if (!perm.hasPermission(sender, "gamemode." + gamemode.name().toLowerCase())
							&& !perm.hasPermission(sender, "gamemode.all")) {
						QuickMessage.noPermission(sender);
						return;
					} else {
						toggleGamemode((Player) sender, gamemode.getGamemode());
						return;
					}
				}
			}
		}
		QuickMessage.incorrectSyntax(sender, "gamemode <survival | creative | spectator | adventure> [player]");
	}

	// Toggles players gamemode
	private void toggleGamemode(Player p, GameMode gamemode) {
		p.setGameMode(gamemode);
		p.sendMessage(
				LanguageFile.getMessage("gamemodeSwitch").replaceAll("%gamemode%", gamemode.name().toLowerCase()));
	}
}
