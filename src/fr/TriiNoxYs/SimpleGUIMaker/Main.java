//Test uBugTrack

package fr.TriiNoxYs.SimpleGUIMaker;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import fr.TriiNoxYs.SimpleGUIMaker.handlers.InventoriesFile;
import fr.TriiNoxYs.SimpleGUIMaker.handlers.Menus;
import fr.TriiNoxYs.SimpleGUIMaker.listeners.commands.SgmCmd;
import fr.TriiNoxYs.SimpleGUIMaker.listeners.events.PlayerChat;
import fr.TriiNoxYs.SimpleGUIMaker.listeners.events.PlayerInv;
import fr.TriiNoxYs.SimpleGUIMaker.listeners.events.PlayerQuit;
import fr.TriiNoxYs.SimpleGUIMaker.utils.Updater;

public class Main extends JavaPlugin{
    
    public static Main plugin;
    
    public InventoriesFile inventories;
    public Updater updater = new Updater(this);;
    
    public void onEnable(){
        plugin = this;
        
        new Menus(this);
        
        getCommand("simpleguimaker").setExecutor(new SgmCmd(this));
        
        Bukkit.getPluginManager().registerEvents(new PlayerChat(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInv(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuit(), this);
        
        if (!getDataFolder().exists())
            getDataFolder().mkdir();
        
        inventories = new InventoriesFile(this);
        
        Updater.checkUpdate(true);
    }
    
    public void onDisable(){ 
        inventories.save();
    }
    
}
