package com.rickasheye.commands;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.rickasheye.configPatrol.configReplacer;
import com.rickasheye.main.Main;

public class loadworld implements CommandExecutor{

	configReplacer replace;
	
	public loadworld(configReplacer replacer) {
		replace = replacer;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
    	if(cmd.getName().equalsIgnoreCase("loadworld")) {
    		if (player.hasPermission("doubleverse.world.loadworld")) {
				try {
					LoadWorld(args[0], player);
				} catch (Exception e) {
					player.sendMessage(ChatColor.RED + "Error: " + e);
				} 
			}else {
				player.sendMessage(replace.getString("invalidperms"));
			}
    	}
		return false;
	}
	
	public boolean LoadWorld(String name, Player player) {
		File fileNew = new File(name);
		if (fileNew.exists() == true) {
			player.sendMessage(replace.getString("loadingworld"));
			World world = Bukkit.getServer().createWorld(new WorldCreator(name));
			player.sendMessage(replace.getString("loadedworld"));
			return true;
		}else {
			player.sendMessage(replace.getString("worlddoesntexist"));
			return false;
		}
	}
}
