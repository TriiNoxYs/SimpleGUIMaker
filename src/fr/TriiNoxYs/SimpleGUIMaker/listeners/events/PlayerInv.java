package fr.TriiNoxYs.SimpleGUIMaker.listeners.events;

import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import fr.TriiNoxYs.SimpleGUIMaker.handlers.Menus;
import fr.TriiNoxYs.SimpleGUIMaker.utils.ChatUtils;


public class PlayerInv implements Listener{
    
    public static HashMap<Player, Boolean> redo = new HashMap<Player, Boolean>();
    
    @EventHandler
    public void onInvClick(InventoryClickEvent e) throws InterruptedException{
        Player p = (Player) e.getWhoClicked();
        
        if(Menus.getStep(p) == "adding items"){
            
            if(e.isRightClick()){
                e.setCancelled(true);
                if(e.isShiftClick()){
                    Bukkit.broadcastMessage("Modif cmd");
                }
                else{
                    ItemStack item = e.getCurrentItem();
                    if(item != null){
                        p.closeInventory();
                        Menus.item.put(p, item);
                        Menus.itemSlot.put(p, getSlot(Menus.invs.get(p), item));
                        ChatUtils.sendMsg(p, "§6§lPlease enter the name of this item.");
                        Menus.setStep(p, "naming item");
                    }
                }
            }
            
        }
        
    }
    
    @EventHandler
    public void onInvClose(InventoryCloseEvent e){
        Player p = (Player) e.getPlayer();
        Inventory inv = e.getInventory();
        
        if(Menus.invs.get(p).equals(inv)){
            if(Menus.getStep(p) == "adding items" && redo.containsKey(p) && redo.get(p)){
                ChatUtils.sendMsg(p, "§6§lHave you finished setting up the inventory ?");
                ChatUtils.sendMsg(p, "§6§lType §r§a§oyes§r§6§l, §r§c§ono §r§6§lor §r§e§o@cancel§r§6§l.");
                Menus.askFinished.put(p, true);
                redo.put(p, false);
            }
        }
    }
    
    public int getSlot(Inventory inv, ItemStack item){
        int slot = 99;
        for(int i = 0; i < 56; i++){
            if(item.isSimilar(inv.getItem(i))){
                slot = i;
                break;
            }
        }
        Bukkit.broadcastMessage("slot: " + slot);
        
        return slot;
    }
    
}
