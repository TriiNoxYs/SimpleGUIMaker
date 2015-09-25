package fr.TriiNoxYs.SimpleGUIMaker.handlers;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import fr.TriiNoxYs.SimpleGUIMaker.Main;
import fr.TriiNoxYs.SimpleGUIMaker.utils.TitleUtils;


public class SaveInv{

    public SaveInv(Main main, Player p, String name, Inventory inv){
        main.inventories.data.set(name.replace(" ", "_").replace("&", "§") + ".items", ItemSerialization.saveInventory(inv));
        main.inventories.data.set(name.replace(" ", "_") + ".lines", inv.getSize()/9);
        main.inventories.save();
        p.sendMessage("§7Inventory saved as §a" + name + "§7.");
        
        //TODO: Remove this line if your server version is < 1.8
        TitleUtils.sendTitle(p, 0, 3*20, 20, "&aInventory saved !", null);
    }
}
