package com.rickasheye.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.rickasheye.main.Main;

public class createworld implements CommandExecutor {
	
    Main plugin;
    world WorldFinder;
	
	public createworld(Main instance, world worldFinder) {
		plugin = instance;
		WorldFinder = worldFinder;
    }
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
    	if(cmd.getName().equalsIgnoreCase("createworld")) {
    		if (player.hasPermission("doubleverse.world.createworld")) {
				try {
					CreateWorld(args[0], player, args[1], args[2]);
				} catch (Exception e) {
					player.sendMessage(ChatColor.RED + "World is invalid or error occured!");
				} 
			}else {
				String converter = plugin.getLabels().getString("invalidperms");
				String converted = ChatColor.translateAlternateColorCodes('&', converter);
				player.sendMessage(converted);
			}
    	}
		return false;
	}
	
	public World CreateWorld(String WorldName, Player player, String worldtype, String generatestructures) {
		World newWorld;
		
		player.sendMessage(ChatColor.YELLOW + "Creating World...");
		WorldType type;
		
		if (WorldName == " " || WorldName == "") {
			WorldName = "unnamed_world";
		}

		if (worldtype == "0") {
			type = WorldType.AMPLIFIED;
		} else if (worldtype == "1") {
			type = WorldType.FLAT;
		} else if (worldtype == "2") {
			type = WorldType.LARGE_BIOMES;
		} else if (worldtype == "3") {
			type = WorldType.NORMAL;
		} else {
			type = WorldType.NORMAL;
		}
		
		boolean generateStructures = false;
		if (generatestructures == "0") {
			generateStructures = true;
		} else if (generatestructures == "1") {
			generateStructures = false;
		} else if(generatestructures == "" || generatestructures == " "){
			generateStructures = true;
		}
		WorldCreator c = new WorldCreator(WorldName);
		c.generateStructures(generateStructures);
		newWorld = c.createWorld();
		WorldFinder.TeleportWorld(newWorld.getName(), player);
		player.sendMessage(ChatColor.GREEN + "You are now in: " + WorldName + " With the world type of: " + type.getName() + " Which has been newly created!");
		return newWorld;
	}
}
