package com.bluecreeper111.JEssentialsRB.modules;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.bluecreeper111.JEssentialsRB.main.JEssentialsRB;
import com.bluecreeper111.JEssentialsRB.main.LanguageFile;
import com.bluecreeper111.JEssentialsRB.main.Main;
import com.bluecreeper111.JEssentialsRB.main.Permissions;
import com.bluecreeper111.JEssentialsRB.tabcompleters.KitTC;
import com.bluecreeper111.JEssentialsRB.utils.JEItem;
import com.bluecreeper111.JEssentialsRB.utils.QuickMessage;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;

public class KitModule {
	
	/* kitFile = kits.yml file where kit data is stored
	 * kits = YamlConfiguration used to access kits.yml
	 * permissionKits = A map with kits that a player has access too
	 * playerGui = A map with already created GUI objects for access to save processing power
	 * kitPreviews = A map with already created GUI objects for access to save processing power
	 * plugin = Main class instance
	 */
	
	private static final File kitFile = new File("plugins/JEssentialsRB/plugindata", "kits.yml");
	private static final YamlConfiguration kits = YamlConfiguration.loadConfiguration(kitFile);
	private final HashMap<UUID, List<String>> permissionKits = new HashMap<>();
	private final HashMap<UUID, HashMap<Integer, JEGui>> playerGUI = new HashMap<>();
	private final HashMap<String, JEGui> kitPreviews = new HashMap<>();
	private Main plugin;
	
	public KitModule(Main plugin) {
		if (!kits.isSet("kits")) {
			kits.createSection("kits");
			saveFile();
		}
		this.plugin = plugin;
		// Initializes /kit tab completer on startup
		new KitTC(this);
	}
	
	// Creates the file upon startup if it doesn't exist
	public static void registerFile() {
		if (!kitFile.exists()) {
			try {
				kitFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	// Saves the kits.yml file after writing to it
	public void saveFile() {
		try {
			kits.save(kitFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// Returns a list of all kits in YML file
	public List<String> listKits() {
		return new ArrayList<>(kits.getConfigurationSection("kits").getKeys(false));
	}
	
	// Returns all kits a certain player has permission for
	public List<String> getPermissibleKits(Player p, Permissions perm) {
		List<String> kits = listKits();
		if (!perm.hasPermission(p, "kits.*")) {
			Iterator<String> iter = kits.iterator();
			while(iter.hasNext()) {
				String k = iter.next();
				if (!perm.hasPermission(p, "kits." + k)) {
					iter.remove();
				}
			}
		}
		return kits;
	}
	
	// Returns a kit gui, can return null if no kits are available
	public JEGui kitsGui(Integer page, Player p, Permissions perm) {
		ConfigurationSection section = kits.getConfigurationSection("kits");
		List<String> permissibleKits = getPermissibleKits(p, perm);
		UUID id = p.getUniqueId();
		// Checks if there is a stored GUI for the player
		if (playerGUI.containsKey(id)
				&& permissionKits.containsKey(id)) {
			if (permissionKits.get(id).equals(permissibleKits)
					&& playerGUI.get(id).containsKey(page)) {
				return playerGUI.get(id).get(page);
			}
		}
		// If no available kits returns null
		if (permissibleKits.size() < 1) return null;
		// 7 kits per row
		Multimap<Integer, String> pages = organizePages(p, permissibleKits);
		List<String> kits = new ArrayList<>(pages.get(page));
		int slotnumber = (int)((Math.ceil((double)kits.size()/ 7.0))*9) + 18;
		JEGui gui = new JEGui(slotnumber > 54 ? 54 : slotnumber, "�6�lKits", id);
		int slot = 10;
		int totalslots = 0;
		for (String k : kits) {
			if (totalslots == 7) {
				totalslots = 0;
				slot += 2;
			}
			// Sets the item for the GUI
			Material m = null;
			if (section.isSet(k + ".display-item")) {
				m = Material.getMaterial(section.getString(k + ".display-item").toUpperCase());
			}
			if (m == null) {
				m = Material.PAPER;
			}
			// Adds content to the preview lore
			ItemStack item = new ItemStack(m, 1);
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(k.contains("&") ? JEssentialsRB.color(k) : "�r�a" + k);
			List<String> lore = new ArrayList<>();
			lore.add("�eContents:");
			// Sorts so that items with no lore and same type will appear as not multiple stacks but one large quantity in preview
			// (E.g: 128 Raw Iron)
			HashMap<Integer, ItemStack> items = convertContents(k);
			HashMap<Integer, Material> materials = new HashMap<>();
			List<ItemStack> hasMeta = new ArrayList<>();
			items.keySet().forEach(i -> {
				ItemStack is = items.get(i);
				if (!is.hasItemMeta()) {
					materials.put(i, is.getType());
				} else {
					hasMeta.add(is);
				}
			});
			boolean one = false;
			Multimap<Material, Integer> multimap = Multimaps.invertFrom(
					Multimaps.forMap(materials), HashMultimap.<Material, Integer>create());
			for (Entry<Material, Collection<Integer>> entry : multimap.asMap().entrySet()) {
				if (entry.getValue().size() == 1) {
					lore.add("�7- " + JEssentialsRB.formatEnumName(entry.getKey().toString()) + ", " + 
							items.get(entry.getValue().toArray()[0]).getAmount());
					one = true;
					continue;
				}
				int total = 0;
				for (Integer i : entry.getValue()) {
					total += items.get(i).getAmount();
				}
				lore.add("�7- " + JEssentialsRB.formatEnumName(entry.getKey().toString()) + ", " + total);
				one =  true;
			}
			for (ItemStack is : hasMeta) {
				lore.add("�7- " + ChatColor.translateAlternateColorCodes('&', is.getItemMeta().getDisplayName()) + "�r �7(" +
						JEssentialsRB.formatEnumName(is.getType().toString()) + "), " + is.getAmount());
				one = true;
			}
			if (!one) {
				lore.add("�c(No Contents)");
			}
			lore.add("�eArmor:");
			// When managing armor contents, 0 is helmet down. (3 is boots)
			// Adds armor contents to preview in lore
			one = false;
			for (int i = 0; i < 4; i++) {
				HashMap<Integer, ItemStack> map = convertArmor(k);
				ItemStack armor = map.get(i);
				if (armor == null) continue;
				if (armor.hasItemMeta()) {
					lore.add("�7- " + ChatColor.translateAlternateColorCodes('&', armor.getItemMeta().getDisplayName()) + "�r �7(" + 
							JEssentialsRB.formatEnumName(armor.getType().toString()));
					one = true;
				} else {
					lore.add("�7- " + JEssentialsRB.formatEnumName(armor.getType().toString()));
					one = true;
				}
			}
			if (!one) {
				lore.add("�c(No Armor)");
			}
			meta.setLore(lore);
			item.setItemMeta(meta);
			gui.setItem(slot, item);
			gui.setItemAction(slot, new Runnable() {
				public void run() {
					giveKit(p, k, perm);
				}
			});
			if (slot == 43) {
				if (pages.containsKey(page +1 )) {
					ItemStack nextPage = new ItemStack(Material.ARROW, 1);
					ItemMeta meta2 = nextPage.getItemMeta();
					meta2.setDisplayName("�6�lNext Page");
					nextPage.setItemMeta(meta2);
					gui.setItem(53, nextPage);
					gui.setItemAction(53, new Runnable() {
						public void run() {
							Bukkit.getScheduler().runTaskLater(plugin, new Runnable () {
								public void run() {
									kitsGui(page + 1, p, perm).openGui();
								}
							}, 1L);
						}
					});
					break;
				}
			}
			if (pages.containsKey(page - 1)) {
				ItemStack lastPage = new ItemStack(Material.ARROW, 1);
				ItemMeta pagemeta = lastPage.getItemMeta();
				pagemeta.setDisplayName("�6�lPrevious Page");
				lastPage.setItemMeta(pagemeta);
				int s = gui.getSize() - 9;
				gui.setItem(s, lastPage);
				gui.setItemAction(s, new Runnable() {
					public void run() {
						Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
							public void run() {
								kitsGui(page - 1, p, perm).openGui();
							}
						}, 1L);
					}
				});
			}
			slot++;
			totalslots++;
		}
		// Saves GUI and kits the player has permission of 
		permissionKits.put(id, permissibleKits);
		HashMap<Integer, JEGui> newmap = playerGUI.getOrDefault(id, new HashMap<>());
		newmap.put(page, gui);
		playerGUI.put(id, newmap);
		return gui;
	}
	
	// Converts contents of kit into usable itemstacks
	private HashMap<Integer, ItemStack> convertContents(String kit) {
		HashMap<Integer, ItemStack> map = new HashMap<>();
		if (!kits.isSet("kits." + kit)) return null;
		for (int i = 0; i < 36; i++) {
			if (kits.isItemStack("kits." + kit + ".contents." + i)) {
				map.put(i, kits.getItemStack("kits." + kit + ".contents." + i));
			} else continue; 
		}
		return map;
	}
	
	// Converts stored armor data into itemstacks
	private HashMap<Integer, ItemStack> convertArmor(String kit) {
		HashMap<Integer, ItemStack> map = new HashMap<>();
		if (!kits.isSet("kits." + kit)) return null;
		for (int i = 0; i < 4; i++) {
			if (kits.isItemStack("kits." + kit + ".armor." + i)) {
				map.put(i, kits.getItemStack("kits." + kit + ".armor." + i));
			} else continue;
		}
		return map;
	}
	
	// Creates a kit
	public void createKit(String name, Player p) {
		for (int i = 0; i < 36; i++) {
			kits.set("kits." + name + ".contents." + i, p.getInventory().getItem(i));
		}
		kits.set("kits." + name + ".armor." + 0, p.getInventory().getHelmet() == null ? null : p.getInventory().getHelmet());
		kits.set("kits." + name + ".armor." + 1, p.getInventory().getChestplate() == null ? null : p.getInventory().getChestplate());
		kits.set("kits." + name + ".armor." + 2, p.getInventory().getLeggings() == null ? null : p.getInventory().getLeggings());
		kits.set("kits." + name + ".armor." + 3, p.getInventory().getBoots() == null ? null : p.getInventory().getBoots());
		saveFile();
	}
	
	// Returns whether or not a kit is already set with the name given
	public boolean isKit(String name) {
		return kits.isSet("kits." + name);
	}
	
	// Creates a dialogue for the player when they are creating a kit to set the kit display item
	public void displayItemDialogue(Player p, String kit) {
		JEDialogue dialogue  = new JEDialogue(p.getUniqueId());
		dialogue.addAction(0, "done", new Runnable () {
			public void run() {
				ItemStack item = p.getInventory().getItemInMainHand();
				if (item == null
						|| item.getType() == Material.AIR) {
					p.sendMessage(LanguageFile.getMessage("mustHoldItem"));
				} else {
					kits.set("kits." + kit + ".display-item", item.getType().toString());
					saveFile();
					p.sendMessage(LanguageFile.getMessage("kitCreated")
							.replaceAll("%kit%", "�r" + kit + "�r"));
					dialogue.end();
				}
			}
		});
		dialogue.addAction(0, "cancel", new Runnable() {
			public void run() {
				p.sendMessage(LanguageFile.getMessage("kitCreated")
						.replaceAll("%kit%", "�r" + kit + "�r"));
				dialogue.end();
			}
		});
		p.sendMessage("�6[!] �ePlease hold a display-item for the kit preview GUI and type �a'done'�e."
				+ "\n�6[!] �eIf you would not like a display-item and prefer the default (paper), type �c'cancel'�e.");
		dialogue.start();
	}
	
	// Gives a kit to the player 
	public void giveKit(Player p, String kit, Permissions perm) {
		if (perm.hasPermission(p, "kits.*")
				|| perm.hasPermission(p, "kits." + kit)) {
			Integer cd = this.getCooldown(kit, p.getUniqueId());
			if (cd == 0
					|| perm.hasPermission(p, "kit.exemptdelay")) {
				HashMap<Integer, ItemStack> items = convertContents(kit);
				HashMap<Integer, ItemStack> armor = convertArmor(kit);
				for (int i = 0; i < 36; i++) {
					if (items.get(i) == null) continue;
					JEItem.giveItem(p, items.get(i));
				}
				if (armor.get(0) != null) {
					JEItem.giveHelmet(p, armor.get(0));
				}
				if (armor.get(1) != null) {
					JEItem.giveChestplate(p, armor.get(1));
				}
				if (armor.get(2) != null) {
					JEItem.giveLeggings(p, armor.get(2));
				}
				if (armor.get(3) != null) {
					JEItem.giveBoots(p, armor.get(3));
				}
				p.sendMessage(LanguageFile.getMessage("givenKit").replace("%kit%", JEssentialsRB.color(kit)));
				int kitCooldown = this.getKitDefaultCooldown(kit);
				if (kitCooldown != 0
						&& !perm.hasPermission(p, "kit.exemptdelay")) {
					this.setKitCooldown(kit, kitCooldown, p.getUniqueId());
				}
			} else {
				p.sendMessage(LanguageFile.getMessage("kitCooldown").replace("%kit%", JEssentialsRB.color(kit))
						.replace("%cooldown%", JEssentialsRB.formatSeconds((long)cd)));
			}
		} else {
			QuickMessage.noPermission(p);
		}
	}
	
	// Gives a kit to other players when a sender executes command 
		public void giveOthersKit(Player p, String kit) {
			HashMap<Integer, ItemStack> items = convertContents(kit);
			HashMap<Integer, ItemStack> armor = convertArmor(kit);
			for (int i = 0; i < 36; i++) {
				if (items.get(i) == null) continue;
				JEItem.giveItem(p, items.get(i));
			}
			if (armor.get(0) != null) {
				JEItem.giveHelmet(p, armor.get(0));
			}
			if (armor.get(1) != null) {
				JEItem.giveChestplate(p, armor.get(1));
			}
			if (armor.get(2) != null) {
				JEItem.giveLeggings(p, armor.get(2));
			}
			if (armor.get(3) != null) {
				JEItem.giveBoots(p, armor.get(3));
			}
			p.sendMessage(LanguageFile.getMessage("givenKit").replace("%kit%", JEssentialsRB.color(kit)));
		}
		
	// Deletes a kit by setting it to null in YAML file
	public void deleteKit(String kit) {
		kits.set("kits." + kit, null);
		// Saves memory by removing instance from saved kit gui previews
		if (kitPreviews.containsKey(kit)) kitPreviews.remove(kit);
		saveFile();
	}
	
	// Uses kits that a player has permission to and organizes them into pages for GUI
	private Multimap<Integer, String> organizePages(Player p, List<String> kits) {
		Multimap<Integer, String> map = HashMultimap.create();
		int pageNumber = 1;
		int i = 0;
		for (String kit : kits) {
			map.put(pageNumber, kit);
			if ((i / 27) == 1) {
				pageNumber++;
				i = 0;
				continue;
			}
			i++;
		}
		return map;
	}
	
	// Opens a GUI preview of a kit for a player
	public void openKitPreview(Player p, String kit, Permissions perm) {
		if (perm.hasPermission(p, "kits.*")
				|| perm.hasPermission(p, "kits." + kit)) {
			if (kitPreviews.get(kit) != null) {
				JEGui gui = kitPreviews.get(kit);
				gui.setNewOwner(p.getUniqueId());
				gui.openGui();
			} else {
				JEGui gui = new JEGui(54, "�6�lKit Preview: �r�a" + kit, p.getUniqueId());
				HashMap<Integer, ItemStack> armor = this.convertArmor(kit);
				HashMap<Integer, ItemStack> items = this.convertContents(kit);
				for (int i = 0; i < 36; i++) {
					if (items.get(i) != null) gui.setItem(i, items.get(i));
					if (i < 4 && armor.get(i) != null) {
						gui.setItem(45 + i, armor.get(i));
					}
				}
				ItemStack filler = new ItemStack(Material.WHITE_STAINED_GLASS);
				ItemMeta m = filler.getItemMeta();
				m.setDisplayName(" ");
				filler.setItemMeta(m);
				for (int i = 36; i < 45; i++) {
					gui.setItem(i, filler);
				}
				ItemStack exit = new ItemStack(Material.BARRIER);
				m = exit.getItemMeta();
				m.setDisplayName("�c�lExit");
				exit.setItemMeta(m);
				gui.setItem(53, exit);
				gui.setItemAction(53, new Runnable() {
					public void run() {
						p.closeInventory();
					}
				});
				gui.openGui();
			}
		} else {
			QuickMessage.noPermission(p);
		}
	}
	
	// Can set a kit so it will be given on a player's first time joining the server
	public void setKitFirstJoin(String kit, boolean bool) {
		kits.set("kits."+ kit + ".firstjoin", bool);
		saveFile();
	}
	
	// Returns a boolean on whether or not the kit should be given on a player's first join
	public boolean isFirstJoin(String kit) {
		if (!kits.isSet("kits."+ kit + ".firstjoin")) return false;
		return kits.getBoolean("kits." + kit + ".firstjoin");
	}
	
	// Returns a kits set cooldown (not player's)
	public Integer getKitDefaultCooldown(String kit) {
		return kits.isSet("kits." + kit + ".defaultcooldown") 
				? kits.getInt("kits." + kit + ".defaultcooldown") : 0;
	}
	
	// Sets a kits default cooldown or specific player cooldown (in seconds)
	public void setKitCooldown(String kit, Integer cd, UUID id) {
		Long time = System.currentTimeMillis() + (cd*1000);
		if (id == null) {
			kits.set("kits." + kit + ".defaultcooldown", cd);
		} else {
			kits.set("kits." + kit + ".playercooldown." + id.toString(), time);
		}
		saveFile();
	}
	
	// Gets the kit's cooldown (seconds) for a specific player
	public Integer getCooldown(String kit, UUID id) {
		String dir = "kits." + kit;
		if (kits.isSet(dir + ".playercooldown." + id.toString())) {
			Integer cd = ((Long)(kits.getLong(dir + ".playercooldown." + id.toString()) - System.currentTimeMillis())).intValue()/1000;
			return cd > 0 ? cd : 0;
		} else {
			return 0;
		}
	}
	
	// Resets a player's cooldown to 0
	public void resetPlayerCooldown(UUID id, String kit) {
		kits.set("kits." + kit + ".playercooldown." + id.toString(), null);
		saveFile();
	}
}
