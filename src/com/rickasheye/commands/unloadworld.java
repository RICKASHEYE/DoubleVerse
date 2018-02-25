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

import com.rickasheye.configPatrol.configReplacer;
import com.rickasheye.main.Main;

public class unloadworld implements CommandExecutor {

	configReplacer replace;
	
	public unloadworld(configReplacer replacer) {
		replace = replacer;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
    	if(cmd.getName().equalsIgnoreCase("unloadworld")) {
    		if (player.hasPermission("doubleverse.world.unloadworld")) {
				try {
					UnloadWorld(args[0], player);
				} catch (Exception e) {
					player.sendMessage(replace.getString("unabletounloadworld"));
					player.sendMessage("Error: " + e);
				} 
			}else {
				player.sendMessage(replace.getString("invalidperms"));
			}
    	}
		return false;
	}
	
	public void UnloadWorld(String WorldName, Player player) {
		World world = Bukkit.getWorld(WorldName);
		
		if (world != null) {
			player.sendMessage(replace.getString("unloadingworld"));
			Bukkit.unloadWorld(world, true);
			
			for(Player p : Bukkit.getOnlinePlayers()) {
				if(p.getWorld() == world) {
					player.sendMessage(replace.getString("removingplayers"));
					//Remove players from that world and notify them!
					World Default = Bukkit.getWorlds().get(0);
					if (Default != null) {
						p.getPlayer().teleport(Default.getSpawnLocation());
					}else {
						p.kickPlayer(replace.getString("kickmessageworldnoexist"));
					}
				}
			}
			
			player.sendMessage(replace.getString("unloadedworld"));
		}else {
			player.sendMessage(replace.getString("worlddoesntexist"));
		}
	}
	
}
