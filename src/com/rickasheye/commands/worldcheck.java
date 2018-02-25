package com.rickasheye.commands;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.rickasheye.configPatrol.configReplacer;
import com.rickasheye.main.Main;

import net.minecraft.server.v1_12_R1.CustomFunction.d;

public class worldcheck implements CommandExecutor {
	
	Main plugin;
	configReplacer replace;
	
	public worldcheck(Main main, configReplacer replacer) {
		plugin = main;
		replace = replacer;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
    	if(cmd.getName().equalsIgnoreCase("worldcheck")) {
    		try {
    			if(player.hasPermission("doubleverse.world.worldcheck")) {
    				//Allow
    				CheckWorld(player, args[0]);
    				if(player.hasPermission("doubleverse.world.worldcheck.players")) {
    					if(args[1].equalsIgnoreCase("players")) {
    						player.sendMessage(ChatColor.GREEN + "There are: ");
    						World world;
    						if(args[0] == "") {
    							world = player.getWorld();
    						}else {
    							world = Bukkit.getWorld(args[0]);
    						}
    						CheckWhatPlayerWorld(player, world.getName());
    					}
    				}else {
    					String converter = plugin.getLabels().getString("invalidperms");
    					String converted = ChatColor.translateAlternateColorCodes('&', converter);
    					player.sendMessage(converted);
    				}
    			}else {
    				//Dont Allow
    				String converter = plugin.getConfig().getString("invalidperms");
    				String converted = ChatColor.translateAlternateColorCodes('&', converter);
    				player.sendMessage(converted);
    			}
			} catch (Exception e) {
				player.sendMessage(ChatColor.RED + "Error Was Caused During Checking: " + args[0]);
				player.sendMessage(ChatColor.RED + "Error is: " + e);
			}
    	}
		return false;
	}
	
	public void CheckWhatPlayerWorld(Player player, String worldName) {
		Collection<? extends Player> players = Bukkit.getOnlinePlayers();
		for(Player p : players) {
			if(p.getWorld() == Bukkit.getWorld(worldName)) {
				player.sendMessage(ChatColor.GREEN + p.getName());
			}else {
				player.sendMessage(ChatColor.RED + p.getName());
			}
		}
	}
	
	public void CheckWorld(Player player, String WorldName) {
		World worldSelected = Bukkit.getWorld(WorldName);
		if (WorldName != null) {
			if (worldSelected != null) {
				player.sendMessage(ChatColor.YELLOW + "Checking " + worldSelected.getName());
				player.sendMessage(ChatColor.BLUE + worldSelected.getName() + " Consists of");
				player.sendMessage(ChatColor.GREEN + "Generate Structures: " + worldSelected.canGenerateStructures());
				player.sendMessage(ChatColor.GREEN + "WorldType: " + worldSelected.getWorldType());
				player.sendMessage(ChatColor.GREEN + "Animal Spawn Limit: " + worldSelected.getAnimalSpawnLimit());
				player.sendMessage(ChatColor.GREEN + "Build Max Height: " + worldSelected.getMaxHeight());
				player.sendMessage(ChatColor.GREEN + "Seed: " + worldSelected.getSeed());
				player.sendMessage(ChatColor.GREEN + "Monster Spawn Limit: " + worldSelected.getMonsterSpawnLimit());
				player.sendMessage(ChatColor.GREEN + "Current Time: " + worldSelected.getTime());
				player.sendMessage(ChatColor.GREEN + "Sea Level: " + worldSelected.getSeaLevel());
				player.sendMessage(ChatColor.GREEN + "Water Animal Spawn Limit: " + worldSelected.getWaterAnimalSpawnLimit());
				player.sendMessage(ChatColor.GREEN + "World Locked?: " + replace.getPlayerCheckData(player.getWorld().getName() + "owner"));
			}else {
				
			}
		}else {
			player.sendMessage(ChatColor.RED + "No World Name Entered!");
		}
	}
}
