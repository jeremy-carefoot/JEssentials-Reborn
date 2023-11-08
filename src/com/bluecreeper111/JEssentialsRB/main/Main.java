package com.bluecreeper111.JEssentialsRB.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.bluecreeper111.JEssentialsRB.commands.JERBCommand;
import com.bluecreeper111.JEssentialsRB.modules.AfkModule;
import com.bluecreeper111.JEssentialsRB.modules.JEDialogueListener;
import com.bluecreeper111.JEssentialsRB.modules.JEGuiListener;
import com.bluecreeper111.JEssentialsRB.modules.KitModule;
import com.bluecreeper111.JEssentialsRB.modules.PlayerConnection;
import com.bluecreeper111.JEssentialsRB.modules.PlayerDeath;
import com.bluecreeper111.JEssentialsRB.modules.SpawnModule;
import com.bluecreeper111.JEssentialsRB.modules.Teleportation;
import com.bluecreeper111.JEssentialsRB.tabcompleters.JERBTab;
import com.bluecreeper111.JEssentialsRB.utils.Console;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public class Main extends JavaPlugin {
	
	private final PluginManager pm = Bukkit.getPluginManager();
	private static JEGuiListener guiListener;
	private static JEDialogueListener dialogueListener;
	private double version;
	private double checkedVersion;
	private boolean enabled;
	private final Console log = new Console("§6§l[JEssentialsRB]");
	private final File plugindatafolder = new File(this.getDataFolder(), "plugindata");
	private static boolean vaultEnabled;
	private static boolean vPermissionsEnabled;
	private static boolean vEconomyEnabled;
	
	// Vault instances
	private static Economy econ = null;
	private static Permission perms = null;
	private static Chat chat = null;
	
	/*
	 * The onEnable() method takes care of enabling the plugin. The following code is run
	 * when the plugin is started.
	 */
	public void onEnable() {
		this.enabled = true;
		version = Double.parseDouble(this.getDescription().getVersion().replaceFirst("1.", ""));
		log.info("§eEnabling §av." + this.getDescription().getVersion() + "§e...");
		// Registering commands, permissions etc.
		setupFiles();
		registerModules();
		JERBTab.obtainInstance(this);
		JERBCommand.obtainInstance(this);
		InitializeClasses.initializeCommandClasses();
		InitializeClasses.initializeTabCompletion();
		setupVault();
		
		// Checks for if plugin has a new update
		if (getConfig().getBoolean("checkForNewUpdates")) {
				log.info("§eChecking for updates...");
				checkedVersion = fetchNewestVersion(version);
				if (checkedVersion > version) {
					log.info("§2§lA new JEssentialsRB update is available!");
					log.info("§2JEssentialsRB §eV1." + checkedVersion + "§2 can be downloaded off of the plugin's page.");
				} else {
					log.info("§eYou are running the latest version of JEssentialsRB.");
				}
		}
		// Registers your plugin and sends metrics!
		new Metrics(this);
		if (!this.enabled == false) log.info("§ePlugin successfully enabled");
	}
	/*
	 * The onDisable() method is run when the plugin is disabled.
	 */
	public void onDisable() {
		log.info("§cPlugin disabled.");
	}
	
	// Verifies YAML files have been generated correctly
	private void setupFiles() {
		if (!new File(getDataFolder(), "config.yml").exists()) {
			saveResource("config.yml", false);
		}
		// Initializes lang.yml
		LanguageFile.initializeLanguageFile(this);
		// Generates plugin data folder
		if (!this.plugindatafolder.exists()) {
			this.plugindatafolder.mkdirs();
		}
		SpawnModule.registerFile(); // Registers spawn.yml file
		KitModule.registerFile(); // Registers kits.yml file
		new PlayerData(); // Registers playerdata.yml file
	}
	
	// Registers other classes
	private void registerModules() {
		pm.registerEvents(new Teleportation(this), this);
		pm.registerEvents(new com.bluecreeper111.JEssentialsRB.modules.Chat(this), this);
		pm.registerEvents(new PlayerConnection(this), this);
		pm.registerEvents(new PlayerDeath(), this);
		pm.registerEvents(new AfkModule(this), this);
		guiListener = new JEGuiListener();
		pm.registerEvents(guiListener, this);
		dialogueListener = new JEDialogueListener();
		pm.registerEvents(dialogueListener, this);
	}
	
	// Checks if there is a new update for JEssentialsRB
	// TODO Update project link when it is created on Spigot for update detection (currently original JEssentials)
	private double fetchNewestVersion(double version) {
		try {
			URL url = new URL("https://servermods.forgesvc.net/servermods/files?projectids=316204");
			URLConnection connect = url.openConnection();
			connect.setReadTimeout(5000);
			connect.setDoOutput(true);
			BufferedReader read = new BufferedReader(new InputStreamReader(connect.getInputStream()));
			String respond = read.readLine();
			JSONArray array = (JSONArray) JSONValue.parse(respond);
			if (array.size() == 0) {
				log.info("§7Could not check for updates. Could be an error with CurseForgeAPI.");
				return version;
			}
			return checkedVersion = Double.parseDouble(((String) ((JSONObject) array.get(array.size() - 1)).get("name"))
					.replace("JEssentials V", "").replace("JEssentialsRB V", "")
						.replace("1.", "").trim());
		} catch (Exception e) {
			log.info("§cError: Failed to check for a new update.");
			return version;
		}
	}
	
	// Sets up vault and deals with dependencies
	private void setupVault() {
		if (this.getServer().getPluginManager().getPlugin("Vault") == null) {
			Main.vaultEnabled = false;
			log.info("§8Vault was not found... Using built-in libraries");
		} else {
			RegisteredServiceProvider<Economy> rsp = this.getServer().getServicesManager().getRegistration(Economy.class);
			if (rsp == null) {
				Main.vEconomyEnabled = false;
			} else {
				Main.vEconomyEnabled = true;
				econ = rsp.getProvider();
			}
			RegisteredServiceProvider<Permission> rsp2 = this.getServer().getServicesManager().getRegistration(Permission.class);
			if (rsp2 == null) {
				Main.vPermissionsEnabled = false;
			} else {
				Main.vPermissionsEnabled = true;
				perms = rsp2.getProvider();
			}
			RegisteredServiceProvider<Chat> rsp3 = this.getServer().getServicesManager().getRegistration(Chat.class);
			chat = rsp3.getProvider();
		}
	}
	
	// Returns vault economy
	public static Economy getEconomy() {
		return econ;
	}
	
	// Returns vault permissions
	public static Permission getPermissions() {
		return perms;
	}
		
	// Returns vault chat
	public static Chat getChat() {
		return chat;
	}
	
	public static boolean isVaultEnabled() {
		return vaultEnabled;
	}
	
	// Returns whether or not a permission plugin was detected
	public static boolean areVaultPermissionsEnabled() {
		return vPermissionsEnabled;
	}
	
	// Returns whether or not an economy plugin was detected
	public static boolean isVaultEconomyEnabled() {
		return vEconomyEnabled;
	}
	
	// Returns gui listener instance
	public static JEGuiListener getGuiListener() {
		return guiListener;
	}
	
	// Returns instance of dialogue listener
	public static JEDialogueListener getDialogueListener() {
		return dialogueListener;
	}

}