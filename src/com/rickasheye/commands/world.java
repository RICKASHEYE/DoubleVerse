package com.rickasheye.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.rickasheye.configPatrol.configReplacer;
import com.rickasheye.main.Main;

public class world implements CommandExecutor {
	
	configReplacer replace;
	loadworld loader;
	
	public world(configReplacer replacer, loadworld load) {
		loader = load;
		replace = replacer;
    }
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
    	if (player.hasPermission("doubleverse.world")) {
			if (cmd.getName().equalsIgnoreCase("world")) {
				try {
					if (args[1] == "") {
						TeleportWorld(args[0], player);
					}else {
						if (player.hasPermission("doubleverse.world.other")) {
							Player playerSelected = Bukkit.getPlayer(args[1]);
							TeleportWorld(args[0], playerSelected);
						}else {
							player.sendMessage(replace.getString("invalidperms"));
						}
					}
				} catch (Exception e) {
					player.sendMessage("Error either invalid world name or something else!");
				}
			} 
		}else {
			player.sendMessage(replace.getString("invalidperms"));
		}
		return false;
	}
	
	public World TeleportWorld(String WorldName, Player player) {	
		if (replace.getPlayerDataString(player.getWorld().getName() + "owner", player.getUniqueId().toString()) == true || replace.getPlayerCheckData(player.getWorld().getName() + "owner") == false || player.hasPermission("doubleverse.lock.override")) {
			World world;
			world = Bukkit.getWorld(WorldName);
			if (world != null) {
				player.getPlayer().teleport(world.getSpawnLocation());
			} else {
				//World Does not exist or is unloaded!
				player.sendMessage(replace.getString("worldunloadedalready"));
				if (loader.LoadWorld(WorldName, player) == false) {
					//World does not exist!
					player.sendMessage(replace.getString("worlddoesntexist"));
				} else {
					player.sendMessage(replace.getString("retryteleport"));
					TeleportWorld(WorldName, player);
				}
				//Try to reload the world!

			} 
			return world;
		}else {
			player.sendMessage(replace.getString("lockednotification"));
			return null;
		}
	}
}
