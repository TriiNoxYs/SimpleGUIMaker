package fr.TriiNoxYs.SimpleGUIMaker.handlers;

import java.io.File;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import fr.TriiNoxYs.SimpleGUIMaker.Main;


public class InventoriesFile {

    @SuppressWarnings("unused")
    private final Main main;
    public final YamlConfiguration data = new YamlConfiguration();
    private final File dataFile;

    public InventoriesFile(Main main) {
        this.main = main;
        dataFile = new File(main.getDataFolder(), "inventories.yml");
        if (!dataFile.exists())
            try {
                dataFile.createNewFile();
            } catch (Exception e) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[ERROR] Unable to create file \"inventories.yml\" in folder \"/plugins/SimpleGUIMaker\" !");
                e.printStackTrace();
            }
        load();
    }

    public void save() {
        try {
            data.save(dataFile);
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[ERROR] Unable to save file \"inventories.yml\" in folder \"/plugins/SimpleGUIMaker\" !");
            e.printStackTrace();
        }
    }

    public void load() {
        try {
            data.load(dataFile);
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[ERROR] Unable to load file \"inventories.yml\" in folder \"/plugins/SimpleGUIMaker\" !");
            e.printStackTrace();
        }
    }
    
}
