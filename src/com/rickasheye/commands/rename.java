package com.rickasheye.commands;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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

public class rename implements CommandExecutor{
	
	configReplacer replace;
	loadworld worldLoader;
	
	public rename(configReplacer replacer, loadworld worldLoaders) {
		replace = replacer;
		worldLoader = worldLoaders;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
    	if (player.hasPermission("doubleverse.world.rename")) {
			if (cmd.getName().equalsIgnoreCase("rename")) {
				Rename(player, args[0], args[1]);
			} 
		}else {
			player.sendMessage(replace.getString("invalidperms"));
		}
		return false;
	}
	
	public void RenameFile(File target, String newName, Player player) {
		player.sendMessage(replace.getString("tryingtorenamefile"));
		try {
			File file2 = new File(newName);
			boolean sucess = target.renameTo(file2);
		} catch (Exception e) {
			player.sendMessage(ChatColor.RED + "Error: " + e);
		}
	}
	
	public void Rename(Player player, String worldName, String newName) {
		World worldChoosen = Bukkit.getWorld(worldName);
		if (worldChoosen != null) {
			player.sendMessage(replace.getString("renamefile"));
			Bukkit.unloadWorld(worldChoosen, true);
			RenameFile(worldChoosen.getWorldFolder(), newName, player);
			player.sendMessage(replace.getString("Succesfully Renamed File!"));
		}else {
			player.sendMessage(replace.getString("unabletoloadword"));
			boolean loadedworld = worldLoader.LoadWorld(worldName, player);
			if(loadedworld == true) {
				//File Exists
				player.sendMessage(replace.getString("foundfile"));
				Rename(player, worldName, newName);
			}else {
				//File does not exist
				player.sendMessage(replace.getString("filedontexist"));
			}
		}
	}
}
