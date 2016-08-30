package fr.TriiNoxYs.SimpleGUIMaker.listeners.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import fr.TriiNoxYs.SimpleGUIMaker.Main;
import fr.TriiNoxYs.SimpleGUIMaker.handlers.DeleteInv;
import fr.TriiNoxYs.SimpleGUIMaker.handlers.ListInvs;
import fr.TriiNoxYs.SimpleGUIMaker.handlers.LoadInv;
import fr.TriiNoxYs.SimpleGUIMaker.handlers.Menus;


public class SgmCmd implements CommandExecutor{
    
    Main plugin;
    String name;
    
    public SgmCmd(Main instance){
        plugin = instance;
        name = plugin.getDescription().getName();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(sender instanceof Player){
            Player p = (Player) sender;
            
            if(args.length == 0){
                p.sendMessage("§cUsage: /sgm <create | load | list | delete | version | update>");
            }else{
                //----- UPDATE -----//
                if(args[0].equalsIgnoreCase("version") || args[0].equalsIgnoreCase("ver")){
                    sender.sendMessage(plugin.getDescription().getFullName()); 
                }
                else if(args[0].equalsIgnoreCase("update")){
                    plugin.updater.updateCommand(sender, args);
                }
                
                //----- COMMANDS -----//
                else if(args[0].equalsIgnoreCase("create")){
                    if(p.hasPermission("simpleguimaker.create")){
                        if(Menus.getStep(p) == "not creating"){
                                Menus.setCreatingMenu(p, "creating");
                        }else{
                            p.sendMessage("§aYou are already creating a menu !");
                            return true;
                        } 
                    }else{
                        p.sendMessage("§cYou don't have permission !");
                        return true;
                    }
                }
                
                else if(args[0].equalsIgnoreCase("load")){
                    if(args.length >= 2){
                        String name = "";
                        for (int i = 1; i < args.length; ++i)
                            name += (i > 1 ? " " : "") + args[i].trim();
                        new LoadInv(plugin, p, name);
                    }
                    else p.sendMessage("§cUsage: /sgm load <name>");
                }
                
                else if(args[0].equalsIgnoreCase("list")){
                    new ListInvs(plugin, p);
                }
                
                else if(args[0].equalsIgnoreCase("delete")){
                    if(args.length >= 2){
                        String name = "";
                        for (int i = 1; i < args.length; ++i)
                            name += (i > 1 ? " " : "") + args[i].trim();
                        new DeleteInv(plugin, p, name);
                    }
                    else p.sendMessage("§cUsage: /sgm delete <name>");
                }
                else p.sendMessage("§cUsage: /sgm <create | load | list | delete | version | update>");
            }
        }
        else sender.sendMessage(ChatColor.RED + "Only players can perform this command.");
        return false;
    }
}
