package fr.TriiNoxYs.SimpleGUIMaker.handlers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import fr.TriiNoxYs.SimpleGUIMaker.Main;


public class LoadInv{

    public LoadInv(Main main, Player p, String name) {
        if (main.inventories.data.getString(name.trim().replace(" ", "_").replace("&", "§")) == null)
            p.sendMessage("§cThe inventory " + name +" doesn't exists.");
        else {
            try {
                Inventory inv = Bukkit.createInventory(null, main.inventories.data.getInt((name.replace(" ", "_").replace("&", "§") + ".lines"))*9, name.replace("&", "§"));
                
                inv.setContents(ItemSerialization.loadInventory(main.inventories.data.getString(name.replace(" ", "_").replace("&", "§") + ".items")));
                p.openInventory(inv);
            } catch (Exception e) {
                e.printStackTrace();
            }
            p.sendMessage("§7Loaded inventory §a" + name.replace("_", " ").replace("&", "§") + "§7.");
        }
    }
    
}
