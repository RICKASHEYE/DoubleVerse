package com.rickasheye.configPatrol;

import org.bukkit.ChatColor;

import com.rickasheye.main.Main;

public class configReplacer {
	
	Main main;
	
	public configReplacer(Main mainClass) {
		main = mainClass;
	}
	
	public String getString(String replaceableString) {
		String converter = main.getLabels().getString(replaceableString);
		String converted = ChatColor.translateAlternateColorCodes('&', converter);
		return converted;
	}
	
	public void addPlayerData(String playerDataName, String data) {
		if(main.getPlayers().get(playerDataName) == null) {
			//Create entry or continue
			main.getPlayers().addDefault(playerDataName, data);
		}else {
			main.getPlayers().set(playerDataName, data);
		}
	}
	
	public boolean getPlayerCheckData(String dataString) {
		if(main.getPlayers().get(dataString) != null) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean getPlayerDataString(String dataString, String comparedString) {
		if(main.getPlayers().get(dataString) == comparedString) {
			return true;
		}else {
			return false;
		}
	}
}
