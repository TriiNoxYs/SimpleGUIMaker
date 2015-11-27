package fr.TriiNoxYs.SimpleGUIMaker.handlers;

import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import fr.TriiNoxYs.SimpleGUIMaker.Main;
import fr.TriiNoxYs.SimpleGUIMaker.utils.ChatUtils;


public class Menus{
    
    private static Main plugin;
    
    public Menus(Main instance){
        plugin = instance;
        System.out.println("PUTAIN ICI ICI CIC ICICICIIICICICIICICIICIICICCICICICICI");
        System.out.println("PUTAIN ICI ICI CIC ICICICIIICICICIICICIICIICICCICICICICI");
        System.out.println("PUTAIN ICI ICI CIC ICICICIIICICICIICICIICIICICCICICICICI");
        System.out.println("PUTAIN ICI ICI CIC ICICICIIICICICIICICIICIICICCICICICICI");
        System.out.println("PUTAIN ICI ICI CIC ICICICIIICICICIICICIICIICICCICICICICI");
        System.out.println("PUTAIN ICI ICI CIC ICICICIIICICICIICICIICIICICCICICICICI");
        System.out.println("PUTAIN ICI ICI CIC ICICICIIICICICIICICIICIICICCICICICICI");
        System.out.println("PUTAIN ICI ICI CIC ICICICIIICICICIICICIICIICICCICICICICI");
    }
    
    public static HashMap<Player, String> creatingMenu = new HashMap<Player, String>();
    public static HashMap<Player, Boolean> askFinished = new HashMap<Player, Boolean>();
    public static HashMap<Player, String>        names = new HashMap<Player, String>();
    public static HashMap<Player, Integer>       lines = new HashMap<Player, Integer>();
    public static HashMap<Player, Inventory>      invs = new HashMap<Player, Inventory>();
    
    public static HashMap<Player, ItemStack>      item = new HashMap<Player, ItemStack>();
    public static HashMap<Player, Integer>    itemSlot = new HashMap<Player, Integer>();
    
    public static void setCreatingMenu(Player p, String value){
        if(value == "creating"){
            creatingMenu.put(p, "naming");
            ChatUtils.sendMsg(p, "§6You are now creating a new menu. Type §e@cancel§6 for cancel.");
            ChatUtils.sendMsg(p, "§6Please, enter the name of the menu.");
        }
        else if(value == "cancel"){
            creatingMenu.remove(p);
            ChatUtils.sendMsg(p, "§6You are no longer creating a menu. You can now talk in the chat.");
        }
        else if(value == "finish"){
            creatingMenu.remove(p);
            askFinished.remove(p);
            ChatUtils.sendMsg(p, "§6§lSuccesfull created menu §r" + names.get(p) + " §6§l !");
            
            new SaveInv(plugin, p, names.get(p), invs.get(p));
        }
    }
    
    public static void setStep(Player p, String step){
        creatingMenu.put(p, step);
        if(step == "adding items"){
            Inventory inv = Bukkit.createInventory(null, lines.get(p) * 9, names.get(p));
            invs.put(p, inv);
            p.openInventory(inv);
        }
        else if(step == "editing items"){
            p.openInventory(invs.get(p));
        }
    }
    
    public static String getStep(Player p){
        if(!creatingMenu.containsKey(p)) return "not creating";
        else return creatingMenu.get(p);
    }
    
}
