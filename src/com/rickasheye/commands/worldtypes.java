package com.rickasheye.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.rickasheye.configPatrol.configReplacer;
import com.rickasheye.main.Main;

public class worldtypes implements CommandExecutor {

	configReplacer replace;
	
	public worldtypes(configReplacer replacer) {
		replace = replacer;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
    	if (player.hasPermission("doubleverse.world.worldtypes")) {
			if (cmd.getName().equalsIgnoreCase("worldtypes")) {
				player.sendMessage(ChatColor.GREEN + "World Type Choice");
				player.sendMessage(ChatColor.YELLOW + "0: Amplified");
				player.sendMessage(ChatColor.YELLOW + "1: Flat");
				player.sendMessage(ChatColor.YELLOW + "2: Large Biomes");
				player.sendMessage(ChatColor.YELLOW + "3: Normal");
				player.sendMessage(ChatColor.GREEN + "Generate Structures Choice");
				player.sendMessage(ChatColor.YELLOW + "0: Generate Structures");
				player.sendMessage(ChatColor.YELLOW + "1: Do Not Generate Structures");
			}else {
				player.sendMessage(replace.getString("invalidperms"));
			}
		}
		return false;
	}

}
