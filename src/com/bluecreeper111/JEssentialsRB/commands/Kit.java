package com.bluecreeper111.JEssentialsRB.commands;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.bluecreeper111.JEssentialsRB.main.JEssentialsRB;
import com.bluecreeper111.JEssentialsRB.main.LanguageFile;
import com.bluecreeper111.JEssentialsRB.modules.KitModule;
import com.bluecreeper111.JEssentialsRB.utils.QuickMessage;

public class Kit extends JERBCommand {

	private final KitModule kits;

	public Kit() {
		super("kit", "kit", true);
		kits = new KitModule(plugin);
		for (String k : kits.listKits()) {
			perm.addPermission("kits." + k);
		}
		perm.addPermission("kits.*");
		perm.addPermission("kit.others");
		perm.addPermission("kit.exemptdelay");
		perm.addPermission("kitdel");
		perm.addPermission("kitcreate");
		perm.addPermission("kitreset");
		perm.addPermission("kitreset.others");
		perm.addPermission("kitoptions");
	}

	// Run on command execute
	public void execute(CommandSender sender, Command cmd, String label, String[] args) {
		if (label.equalsIgnoreCase("kit")
				|| label.equalsIgnoreCase("kits")) {
			// Lists the kits available
			if (args.length == 0) {
				List<String> k = kits.getPermissibleKits((Player)sender, perm);
				if (k.size() == 0) {
					sender.sendMessage(LanguageFile.getMessage("noKits"));
					return;
				}
				if (!plugin.getConfig().getBoolean("use-kit-gui-for-list")
						|| (!(sender instanceof Player))) {
					String list = "�6Kits: �r";
					for (String s : k) {
						list += JEssentialsRB.color(s) + "�r, ";
					}
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', list));
				} else {
					kits.kitsGui(1, (Player)sender, perm).openGui();
				}
			// Giving kit to executor
			} else if (args.length == 1) {
				if (sender instanceof Player) {
					Player p = (Player) sender;
					if (kits.isKit(args[0])) {
						kits.giveKit(p, args[0], perm);
					} else {
						p.sendMessage(LanguageFile.getMessage("invalidKit").replaceAll("%kit%", args[0]));
					}
				} else {
					QuickMessage.onlyPlayersCanExecuteCommand();
				}
			} else {
				// Giving kits to other players
				if (perm.hasPermission(sender, "kit.others")) {
					if (kits.isKit(args[0])) {
						Player target = JEssentialsRB.getPlayer(args[1]);
						if (target != null) {
							kits.giveOthersKit(target, args[0]);
							sender.sendMessage(LanguageFile.getMessage("playerGivenKit").replaceAll("%kit%", args[0])
									.replaceAll("%player%", args[1]));
						} else {
							QuickMessage.playerNotFound(sender, args[1]);
						}
					} else {
						sender.sendMessage(LanguageFile.getMessage("invalidKit").replaceAll("%kit%", args[0]));
					}
				} else {
					QuickMessage.noPermission(sender);
				}
			}
		// Creates a kit based on players inventory
		} else if (label.equalsIgnoreCase("kitcreate")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (perm.hasPermission(p, "kitcreate")) {
					if (args.length > 0) {
						if (args[0].contains(".") || args[0].contains("*")) {
							p.sendMessage(LanguageFile.getMessage("characterNotAllowed").replaceAll("%char%", "."));
							return;
						}
						if (!kits.isKit(args[0])) {
							kits.createKit(args[0], p);
							if (plugin.getConfig().getBoolean("use-kit-gui-for-list")) {
								kits.displayItemDialogue(p, args[0]);
							} else {
								p.sendMessage(LanguageFile.getMessage("kitCreated")
										.replaceAll("%kit%", args[0]));
							}
						} else {
							p.sendMessage(LanguageFile.getMessage("kitAlreadyExists")
									.replaceAll("%kit%", args[0]));
						}
					} else {
						QuickMessage.incorrectSyntax(sender, "kitcreate [name]");
					}
				} else {
					QuickMessage.noPermission(p);
				}
			} else {
				QuickMessage.onlyPlayersCanExecuteCommand();
			}
		// Deletes a kit
		} else if (label.equalsIgnoreCase("kitdel")) {
			if (perm.hasPermission(sender, "kitdel")) {
				if (args.length > 0) {
					if (kits.isKit(args[0])) {
						kits.deleteKit(args[0]);
						sender.sendMessage(LanguageFile.getMessage("kitDeleted").replaceAll("%kit%", args[0]));
					} else {
						sender.sendMessage(LanguageFile.getMessage("invalidKit").replaceAll("%kit%", args[0]));
					}
				} else {
					QuickMessage.incorrectSyntax(sender, "kitdel [kit]");
				}
			} else {
				QuickMessage.noPermission(sender);
			}
		// Shows a preview of a kit to a player
		} else if (label.equalsIgnoreCase("kitshow")) {
			if (sender instanceof Player) {
				if (args.length > 0) {
					if (kits.isKit(args[0])) {
						kits.openKitPreview((Player)sender, args[0], perm);
					} else {
						sender.sendMessage(LanguageFile.getMessage("invalidKit").replaceAll("%kit%", args[0]));
					}
				} else {
					QuickMessage.incorrectSyntax(sender, "kitshow [name]");
				}
			} else {
				QuickMessage.onlyPlayersCanExecuteCommand();
			}
		// Reset a player's kit cooldown (others or you're own)
		} else if (label.equalsIgnoreCase("kitreset")) {
			if (perm.hasPermission(sender, "kitreset")) {
				switch(args.length) {
				case 1:
					if (sender instanceof Player) {
						if (kits.isKit(args[0])) {
							kits.resetPlayerCooldown(((Player)sender).getUniqueId(), args[0]);
							sender.sendMessage(LanguageFile.getMessage("resetKitCooldown").replaceAll("%kit%", args[0]));
						} else {
							sender.sendMessage(LanguageFile.getMessage("invalidKit").replace("%kit%", args[0]));
						}
					} else {
						QuickMessage.onlyPlayersCanExecuteCommand();
					}
					break;
				case 2:
					if (perm.hasPermission(sender, "kitreset.others")) {
						if (kits.isKit(args[0])) {
							if (JEssentialsRB.isPlayer(args[1])) {
								kits.resetPlayerCooldown(JEssentialsRB.getPlayer(args[1]).getUniqueId(), args[0]);
								sender.sendMessage(LanguageFile.getMessage("resetOtherPlayerKitCooldown")
										.replace("%kit%", args[0]).replace("%target%", args[1]));
							} else {
								QuickMessage.playerNotFound(sender, args[1]);
							}
						} else {
							sender.sendMessage(LanguageFile.getMessage("invalidKit").replace("%kit%", args[0]));
						}
					} else {
						QuickMessage.noPermission(sender);
					}
					break;
				default:
					if (!perm.hasPermission(sender, "kitreset.others")) {
						QuickMessage.incorrectSyntax(sender, "kitreset [kit]");
					} else {
						QuickMessage.incorrectSyntax(sender, "kitreset [kit] [player]");
					}
					break;
				}
			} else {
				QuickMessage.noPermission(sender);
			}
		// Change basic options of the kit including whether it will be given on a player's first join or set cooldowns
		} else {
			if (perm.hasPermission(sender, "kitoptions")) {
				switch (args.length) {
				case 2:
					if (args[1].equalsIgnoreCase("firstjoin")) {
						if (kits.isKit(args[0])) {
							boolean bool = !kits.isFirstJoin(args[0]);
							kits.setKitFirstJoin(args[0], bool);
							sender.sendMessage(LanguageFile.getMessage("kitSetFirstJoin").replace("%kit%", args[0])
									.replace("%bool%", Boolean.toString(bool)));
						} else {
							sender.sendMessage(LanguageFile.getMessage("invalidKit").replace("%kit%", args[0]));
						}
					} else {
						QuickMessage.incorrectSyntax(sender, "kitoptions <kit> [firstjoin|cooldown] [?cooldown]");
					}
					break;
				case 3:
					if (args[1].equalsIgnoreCase("cooldown")) {
						if (kits.isKit(args[0])) {
							if (JEssentialsRB.isInt(args[2])) {
								kits.setKitCooldown(args[0], Integer.parseInt(args[2]), null);
								sender.sendMessage(LanguageFile.getMessage("kitSetDefaultCooldown").replace("%kit%", args[0])
										.replace("%cooldown%", JEssentialsRB.formatSeconds(Long.parseLong(args[2]))));
							} else {
								sender.sendMessage(LanguageFile.getMessage("invalidNumber").replace("%argument%", args[2]));
							}
						} else {
							QuickMessage.incorrectSyntax(sender, "kitoptions <kit> [firstjoin, cooldown] [?cooldown]");
						}
					} else {
						QuickMessage.incorrectSyntax(sender, "kitoptions <kit> [firstjoin, cooldown] [?cooldown]");
					}
					break;
				default:
					QuickMessage.incorrectSyntax(sender, "kitoptions <kit> [firstjoin, cooldown] [?cooldown]");
					break;
				}
			} else {
				QuickMessage.noPermission(sender);
			}
		}
	}

}
