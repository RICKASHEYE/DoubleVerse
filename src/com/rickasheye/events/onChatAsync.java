package com.rickasheye.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

import com.rickasheye.main.Main;

public class onChatAsync implements Listener{
	
	Main mainClass;
	
	public onChatAsync(Main main) {
		mainClass = main;
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent chat) {
		Player player = chat.getPlayer();
		if (mainClass.getConfig().getBoolean("customchatparse")) {
			String message = chat.getMessage().replaceAll(mainClass.getConfig().getString("variablechat"), player.getWorld().getName());
			chat.setMessage(message);
			if (mainClass.getConfig().getBoolean("customchatprefixes")) {
				chat.setFormat("[" + mainClass.getConfig().getString("variablechat") + "] " + player.getDisplayName() + ChatColor.DARK_GRAY + ": "
						+ ChatColor.WHITE + chat.getMessage());
			} else {

			} 
		}
	}
}
