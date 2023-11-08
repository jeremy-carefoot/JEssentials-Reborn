package com.bluecreeper111.JEssentialsRB.modules;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.bluecreeper111.JEssentialsRB.main.Main;

public class JEDialogue {
	
	private UUID owner;
	private int stage;
	private HashMap<Integer, List<Entry<String, Runnable>>> actions;
	private JEDialogueListener l;
	
	public JEDialogue(UUID owner) {
		this.owner = owner;
		stage = 0;
		actions = new HashMap<>();
		l = Main.getDialogueListener();
	}
	
	// Returns owner of the dialogue
	public UUID getOwner() {
		return this.owner;
	}
	
	// Returns what stage the dialogue is on
	public int getStage() {
		return this.stage;
	}
	
	
	// Returns the action map of the dialogue
	public HashMap<Integer, List<Entry<String, Runnable>>> getActions() {
		return this.actions;
	}
	
	// Moves dialogue to next stage
	public void nextStage() {
		this.stage++;
	}
	
	// Adds an action to the map of actions and phrases
	public void addAction(Integer stage, String phrase, Runnable a) {
		List<Entry<String, Runnable>> list = actions.getOrDefault(stage, new ArrayList<>());
		list.add(new SimpleEntry<String, Runnable>(phrase, a));
		actions.put(stage, list);
	}
	
	// Starts the dialogue for the player
	public void start() {
		Player p = Bukkit.getPlayer(owner);
		if (p != null
				&& p.isOnline()) {
			l.addPlayer(owner, this);
		}
	}
	
	// Ends the dialogue
	public void end() {
		l.removePlayer(owner);
	}

}
