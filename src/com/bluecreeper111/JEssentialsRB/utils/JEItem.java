package com.bluecreeper111.JEssentialsRB.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class JEItem {
	
	// Gives a player an item WITH inventory full detection
	public static void giveItem(Player p, ItemStack item) {
		if (p.getInventory().firstEmpty() == -1) {
			p.getLocation().getWorld().dropItem(p.getLocation(), item);
		} else {
			p.getInventory().addItem(item);
		}
	}
	
	// Creates item lore
	public static List<String> createLore(String... lore) {
		List<String> list = new ArrayList<>();
		for (String l : lore) {
			list.add(ChatColor.translateAlternateColorCodes('&', l));
		}
		return list;
	}
	
	// Next 4 functions give player armor while considering if there is already an item present
	public static void giveHelmet(Player p, ItemStack item) {
		if (p.getInventory().getHelmet() == null) {
			p.getInventory().setHelmet(item);
		} else {
			giveItem(p, item);
		}
	}
	
	public static void giveChestplate(Player p, ItemStack item) {
		if (p.getInventory().getChestplate() == null) {
			p.getInventory().setChestplate(item);
		} else {
			giveItem(p, item);
		}
	}
	
	public static void giveLeggings(Player p, ItemStack item) {
		if (p.getInventory().getLeggings() == null) {
			p.getInventory().setLeggings(item);
		} else {
			giveItem(p, item);
		}
	}
	
	public static void giveBoots(Player p, ItemStack item) {
		if (p.getInventory().getBoots() == null) {
			p.getInventory().setBoots(item);
		} else {
			giveItem(p, item);
		}
	}

}
