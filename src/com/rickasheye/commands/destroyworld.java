package com.rickasheye.commands;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.rickasheye.configPatrol.configReplacer;
import com.rickasheye.main.Main;

public class destroyworld implements CommandExecutor{
	
	loadworld wordLoader;
	configReplacer replace;
	
	public destroyworld(configReplacer replacer, loadworld loader) {
		replace = replacer;
		wordLoader = loader;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
    	if(cmd.getName().equalsIgnoreCase("destroyworld")) {
    		if (player.hasPermission("doubleverse.world.destroyworld")) {
				try {
					player.sendMessage(replace.getString("deletingworld"));
					DestroyWorld(args[0], player);
				} catch (Exception e) {
					player.sendMessage(ChatColor.RED + "Error Was Caused During Deleting: " + args[0]);
					player.sendMessage(ChatColor.RED + "Error is: " + e);
				} 
			}else {
				player.sendMessage(replace.getString("invalidperms"));
			}
    	}
		return false;
	}
	
	public boolean deleteWorld(File path) {
	      if(path.exists()) {
	          File files[] = path.listFiles();
	          for(int i=0; i<files.length; i++) {
	              if(files[i].isDirectory()) {
	                  deleteWorld(files[i]);
	              } else {
	                  files[i].delete();
	              }
	          }
	      }
	      return(path.delete());
	}
	
	public void DestroyWorld(String name, Player player) {
			//Make sure world is loaded first!
			//if not return a message saying world is not loaded load it first!
			World worldSelected;
			worldSelected = Bukkit.getWorld(name);
			
			for(Player p : Bukkit.getOnlinePlayers()) {
				if(p.getWorld() == worldSelected) {
					player.sendMessage(replace.getString("removingplayers"));
					//Remove players from that world and notify them!
					World Default = Bukkit.getWorlds().get(0);
					if (Default != null) {
						p.getPlayer().teleport(Default.getSpawnLocation());
					}else {
						p.kickPlayer(replace.getString("worlddoesntexist"));
					}
				}
			}
			
			if (worldSelected != null) {
				Bukkit.unloadWorld(worldSelected, false);
				File deleteFolder = worldSelected.getWorldFolder();
				deleteWorld(deleteFolder);
				player.sendMessage(replace.getString("deletedworld"));
			} else {
				try {
					player.sendMessage(replace.getString("unloadedworld"));
					boolean loadedworld = wordLoader.LoadWorld(name, player);
					if(loadedworld == true) {
					//return to the start
					DestroyWorld(name, player);
					}else {
						player.sendMessage(replace.getString("unabletoloadword"));
					}
					return;
				} catch (Exception e) {
					player.sendMessage(ChatColor.RED + "Error: " + e);
				}
			} 
	}
}
