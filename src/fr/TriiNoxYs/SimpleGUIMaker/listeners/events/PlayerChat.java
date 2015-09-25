package fr.TriiNoxYs.SimpleGUIMaker.listeners.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import fr.TriiNoxYs.SimpleGUIMaker.handlers.Menus;
import fr.TriiNoxYs.SimpleGUIMaker.utils.ChatUtils;
import fr.TriiNoxYs.SimpleGUIMaker.utils.MathUtils;


public class PlayerChat implements Listener{
    
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
        String msg = e.getMessage();
        String[] args = msg.split(" ");
        
        if(Menus.getStep(p) != "no" && Menus.creatingMenu.containsKey(p)){
            e.setCancelled(true);
            
            if(Menus.getStep(p) == "naming item"){
                Menus.item.get(p).getItemMeta().setDisplayName(msg);
                Menus.invs.get(p).setItem(Menus.itemSlot.get(p), Menus.item.get(p));
                p.sendMessage("§6§lItem renamed.");
                Menus.setStep(p, "editing items");
            }
            
            if(Menus.askFinished.containsKey(p) && Menus.askFinished.get(p)){
                e.setCancelled(true);
                if(msg.equalsIgnoreCase("yes")){
                    Menus.setCreatingMenu(p, "finish");
                }
                else if(msg.equalsIgnoreCase("no")){
                    p.openInventory(Menus.invs.get(p));
                    PlayerInv.redo.put(p, true);
                }
                else{
                    p.sendMessage("");
                    p.sendMessage("§6§lPlease type §r§a§oyes§r§6§l, §r§c§ono §r§6§lor §r§e§o@cancel§r§6§l.");
                }
            }
            
            if(msg.equalsIgnoreCase("@cancel")) 
                Menus.setCreatingMenu(p, "cancel");
            
            else{
                if(Menus.getStep(p) == "naming"){
                    PlayerInv.redo.put(p, true);
                    Menus.names.put(p, msg);
                    ChatUtils.sendMsg(p, "§6Menu name:§r " + msg);
                    Menus.setStep(p, "choosing lines");
                    ChatUtils.sendMsg(p, "§6Please, enter the lines count for this menu.");
                }
                else if(Menus.getStep(p) == "choosing lines"){
                    if(MathUtils.isNumeric(args[0])){
                        int num = Integer.valueOf(args[0]);
                        
                        if(num > 0 && num <= 6){
                            PlayerInv.redo.put(p, true);
                            Menus.lines.put(p, Integer.valueOf(msg));
                            ChatUtils.sendMsg(p, "§6Lines count:§r " + msg);
                            Menus.setStep(p, "adding items"); 
                        }
                        else p.sendMessage("§cYou must enter a integer between 1 and 6.");
                    }
                    else p.sendMessage("§cYou must enter a number (integer).");
                }
                else if(Menus.getStep(p) == "bug"){
                    p.sendMessage("§cSorry but the plugin encountered an error :/");
                    Menus.setCreatingMenu(p, "cancel");
                    return;
                }
            }
        }
    }
    
}
