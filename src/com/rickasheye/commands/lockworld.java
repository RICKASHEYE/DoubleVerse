package com.rickasheye.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.rickasheye.configPatrol.configReplacer;
import com.rickasheye.main.Main;

public class lockworld implements CommandExecutor {

	configReplacer replace;
	
	public lockworld(configReplacer main) {
		// TODO Auto-generated constructor stub
		replace = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("lockworld")) {
			Player player = (Player) sender;
			//Lock the world
			if(player.hasPermission("doubleverse.lock.lockworld")) {
				//Execute command
				replace.addPlayerData(player.getWorld().getName() + "owner", player.getUniqueId().toString());
				player.sendMessage(replace.getString("lockworld"));
			}else {
				//Player is unable to execute command
				player.sendMessage(replace.getString("invalidperms"));
			}
		}
		return false;
	}
}
