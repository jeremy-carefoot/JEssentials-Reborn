package com.bluecreeper111.JEssentialsRB.modules;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.bluecreeper111.JEssentialsRB.main.LanguageFile;

public class JEDialogueListener implements Listener {
	
	private final HashMap<UUID, JEDialogue> dialogues = new HashMap<>();
	
	// Add a player to active map
	public void addPlayer(UUID id, JEDialogue d) {
		dialogues.put(id, d);
	}
	
	// Remove a player from the dialogue map
	public void removePlayer(UUID id) {
		dialogues.remove(id);
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void chatEvent(AsyncPlayerChatEvent e) {
		if (dialogues.containsKey(e.getPlayer().getUniqueId())) {
			e.setCancelled(true);
			JEDialogue d = dialogues.get(e.getPlayer().getUniqueId());
			List<Entry<String, Runnable>> list = d.getActions().get(d.getStage());
			for (Entry<String, Runnable> a : list) {
				if (a.getKey().equalsIgnoreCase(e.getMessage())) {
					a.getValue().run();
					return;
				}
			}
			e.getPlayer().sendMessage(LanguageFile.getMessage("invalidArgument").replaceAll("%argument%", e.getMessage()));
		}
	}
	
	// Disables commands while in the GUI
	@EventHandler(priority = EventPriority.NORMAL)
	public void onCommand(PlayerCommandPreprocessEvent e) {
		if (dialogues.containsKey(e.getPlayer().getUniqueId())) {
			e.setCancelled(true);
		}
	}

}
