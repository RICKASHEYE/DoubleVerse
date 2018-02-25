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

public class worlds implements CommandExecutor{
	
	configReplacer replace;
	
	public worlds(configReplacer replacer) {
		replace = replacer;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
    	if (player.hasPermission("doubleverse.world.worlds")) {
			if (cmd.getName().equalsIgnoreCase("worlds")) {
				player.sendMessage(ChatColor.YELLOW + "There are: ");
				player.sendMessage(ChatColor.WHITE + Worlds());
			} 
		}else {
			player.sendMessage(replace.getString("invalidperms"));
		}
		return false;
	}
	
	public String Worlds() {
		String[] worldNames = new String[Bukkit.getServer().getWorlds().size()];
		int count = 0;
		for(World w : Bukkit.getServer().getWorlds()){
		worldNames[count] = w.getName();
		count++;
		}
		for(String s : worldNames){
		System.out.println("World Names = " + s);
		}
		String FinalNames = "";
		for(int i = 0; i<worldNames.length; i++) {
			if (i != worldNames.length - 1) {
				FinalNames += worldNames[i] + ", ";
			}else {
				FinalNames += worldNames[i] + ".";
			}
		}
		return FinalNames;
	}
}
