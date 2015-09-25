package fr.TriiNoxYs.SimpleGUIMaker.handlers;

import org.bukkit.entity.Player;
import fr.TriiNoxYs.SimpleGUIMaker.Main;
import fr.TriiNoxYs.SimpleGUIMaker.utils.TitleUtils;


public class DeleteInv{

    public DeleteInv(Main main, Player p, String name) {
        if (main.inventories.data.getString(name.replace(" ", "_").replace("&", "§")) == null) {
            p.sendMessage("§cThe inventory " + name +" doesn't exists.");
        }
        else{
            main.inventories.data.set(name.replace(" ", "_").replace("&", "§"), null);
            main.inventories.save();
            p.sendMessage("§7Deleted inventory §a" + name + "§7.");
            
            //TODO: Remove this line if your server version is < 1.8
            TitleUtils.sendTitle(p, 0, 3*20, 20, "&aInventory deleted !", null);
        }
    }
}
