package fr.TriiNoxYs.SimpleGUIMaker.handlers;

import java.util.ArrayList;
import java.util.Set;
import org.bukkit.entity.Player;
import fr.TriiNoxYs.SimpleGUIMaker.Main;


public class ListInvs{

    public ListInvs(Main main, Player player) {
        if (main.inventories.data.getString(player.getUniqueId().toString()) == null)
            player.sendMessage("§7You don't have any saved inventories.");
        else {
            //Set<String> inventories = main.inventories.data.getConfigurationSection(player.getUniqueId().toString()).getKeys(true);
            Set<String> inventories = main.inventories.data.getKeys(false);
            player.sendMessage("§7Saved inventories:");
            ArrayList<String> invs = new ArrayList<String>();
            for (String inv : inventories)
                if (!inv.contains("."))
                    invs.add(inv);
            player.sendMessage("§a" + invs.toString());
        }
    }
}
