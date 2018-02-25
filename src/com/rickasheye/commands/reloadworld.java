package com.rickasheye.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.rickasheye.configPatrol.configReplacer;
import com.rickasheye.main.Main;

public class reloadworld implements CommandExecutor {

	loadworld worldLoader;
	unloadworld worldunLoader;
	configReplacer replace;
	
	public reloadworld(loadworld worldloader, unloadworld worldunloader, configReplacer replacer) {
		worldLoader = worldloader;
		worldunLoader = worldunloader;
		replace = replacer;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		if(cmd.getName().equalsIgnoreCase("reloadworld")){
			if (player.hasPermission("doubleverse.world.reloadworld")) {
				if (args[0] != null) {
					if (Bukkit.getWorld(args[0]) != null) {
						worldunLoader.UnloadWorld(args[0], player);
						worldLoader.LoadWorld(args[0], player);
					}else {
						player.sendMessage(replace.getString("worlddoesntexist"));
					}
				}else {
					player.sendMessage(replace.getString("noworldnameentered"));
				}
			}else {
				player.sendMessage(replace.getString("invalidperms"));
			}
		}
		return false;
	}

}
