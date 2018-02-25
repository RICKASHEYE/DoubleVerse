package com.rickasheye.main;

import com.rickasheye.commands.*;
import com.rickasheye.configPatrol.configReplacer;
import com.rickasheye.events.onChatAsync;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener{
	
    private File configf, specialf;
    public FileConfiguration configLabels, configPlayers;

    
    public FileConfiguration getLabels() {
    	return this.configLabels;
    }
    
    public FileConfiguration getPlayers() {
    	return this.configPlayers;
    }
    
    private void createFiles() {

        configf = new File(getDataFolder(), "labels.yml");
        specialf = new File(getDataFolder(), "players.yml");

        if (!configf.exists()) {
            configf.getParentFile().mkdirs();
            saveResource("labels.yml", false);
        }
        if (!specialf.exists()) {
            specialf.getParentFile().mkdirs();
            saveResource("players.yml", false);
         }

        configLabels = new YamlConfiguration();
        configPlayers = new YamlConfiguration();
        
        try {
            configLabels.load(configf);
            configPlayers.load(specialf);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
	
	public void onEnable() {
		InitializeThings();
		createFiles();
	}
	
	public void InitializeThings() {
		PluginDescriptionFile p = this.getDescription();
		this.getLogger().info(p.getName() + " Has been Enabled!");
        InitComs();
        RegisterEvents();
		getConfig().options().copyDefaults(true);
	}
	
	public void RegisterEvents() {
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new onChatAsync(this), this);
	}
	
	public void InitComs() {
        getCommand("world").setExecutor(new world(new configReplacer(this), new loadworld(new configReplacer(this))));
        getCommand("createworld").setExecutor(new createworld(this, new world(new configReplacer(this), new loadworld(new configReplacer(this)))));
        getCommand("worldtypes").setExecutor(new worldtypes(new configReplacer(this)));
        getCommand("destroyworld").setExecutor(new destroyworld(new configReplacer(this), new loadworld(new configReplacer(this))));
        getCommand("unloadworld").setExecutor(new unloadworld(new configReplacer(this)));
        getCommand("loadworld").setExecutor(new loadworld(new configReplacer(this)));
        getCommand("worlds").setExecutor(new worlds(new configReplacer(this)));
        getCommand("rename").setExecutor(new rename(new configReplacer(this), new loadworld(new configReplacer(this))));
        getCommand("worldcheck").setExecutor(new worldcheck(this, new configReplacer(this)));
        getCommand("lockworld").setExecutor(new lockworld(new configReplacer(this)));
        getCommand("reloadworld").setExecutor(new reloadworld(new loadworld(new configReplacer(this)), new unloadworld(new configReplacer(this)), new configReplacer(this)));
	}
}
