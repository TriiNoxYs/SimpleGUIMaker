package fr.TriiNoxYs.SimpleGUIMaker.utils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import fr.TriiNoxYs.SimpleGUIMaker.Main;
import fr.TriiNoxYs.SimpleGUIMaker.listeners.events.PlayerChat;


public class Updater{
    
    /*
     * @author TriiNoxYs
     * Please do not copy this class.
     */
    
    private static Main plugin;
    
    public Updater(Main instance){
        plugin = instance;
    }
    
    private static String name;
    private static String check_adress;
    private static String download_adress;
    private static String update_path;
    
    public static String checkUpdate(boolean showInfos){
        name = plugin.getDescription().getName();
        check_adress = "http://ftp.triinoxys.altervista.org/Uploads/Files/Plugins/" + name + "/check_update.php";
        download_adress = "http://ftp.triinoxys.altervista.org/Uploads/Files/Plugins/" + name + "/" + name + ".jar";
        update_path = "plugins"+ File.separator + name + ".jar";
        
        
        String serverReturn = "failed";
        String version = plugin.getDescription().getVersion();
        
        try{
            BufferedReader rd;
            URL url = new URL(check_adress);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
            wr.write("getVersion=true");
            wr.close();

            if (connection.getResponseCode() == 200) rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            else rd = new BufferedReader(new InputStreamReader(connection.getErrorStream()));

            serverReturn = rd.readLine();
        }catch(Exception e){
            Bukkit.getLogger().warning("" + e);
        }
        
        if(showInfos){
            if(!serverReturn.equals("failed")){
                if(needUpdate(version, serverReturn)){
                    for(Player p : Bukkit.getOnlinePlayers()){
                        if(p.hasPermission(name.toLowerCase() + ".update") || p.isOp()){
                            p.sendMessage("");
                            p.sendMessage("§6§l" + name + " §8§l>>> §a§lNew version available !");
                            p.sendMessage("§6§l" + name + " §8§l>>> §a§lCurrent: §c" + version);
                            p.sendMessage("§6§l" + name + " §8§l>>> §a§lUpdate:  §6" + serverReturn);
                            p.sendMessage("§6§l" + name + " §8§l>>> §a§lType §6/" + name.toLowerCase() + " update§a§l to update !");
                            p.sendMessage("");
                        }
                    } 
                }
            }
        }
        
        return serverReturn;
    }
    
    
    public void download(CommandSender sender, String reload){
        OutputStream out = null;
        URLConnection conn = null;
        InputStream in = null;
        
        sender.sendMessage("§8§lUpdating §6" + name + "§8...");
        
        try{
            URL url = new URL (download_adress);
            out = new BufferedOutputStream(new FileOutputStream(update_path));
            conn = url.openConnection();
            in = conn.getInputStream();
            
            byte[] buffer = new byte[1024];
            int numRead;
            
            while((numRead = in.read(buffer)) != -1){
                out.write(buffer, 0, numRead);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            try{
                if (in != null) in.close();
                if (out != null) out.close();
                sender.sendMessage("§6§l" + name + "§a has been updated !");
                if(reload == "true") Bukkit.getServer().reload();
                else if(reload == "false") sender.sendMessage("§aNow you need to reload/restart your server !");
                else askReload(sender);
            }catch(IOException ioe){
                ioe.printStackTrace();
            }
        }
    }
    
    public void updateCommand(CommandSender sender, String[] args){
        String serverReturn = Updater.checkUpdate(false);
        String version = plugin.getDescription().getVersion();
        
        if(!serverReturn.equals("failed")){
            if(needUpdate(version, serverReturn)){
                if(Arrays.asList(args).contains("-y")) download(sender, "true");
                else if(Arrays.asList(args).contains("-n")) download(sender, "false");
                else download(sender, "ask");
            }
            else{
                if(Arrays.asList(args).contains("-force")){
                	if(Arrays.asList(args).contains("-y")) download(sender, "true");
                	else if(Arrays.asList(args).contains("-n")) download(sender, "false");
                	else download(sender, "ask");
                }
                else{
                    if(sender instanceof ConsoleCommandSender || sender.hasPermission(name.toLowerCase() + ".update") || sender.isOp()){
                        sender.sendMessage(" \n§a§l" + name + " is already updated.");
                        sender.sendMessage("§a§lCurrent version:§6 " + version);
                        sender.sendMessage("§a§lType §6/" + name.toLowerCase() + " update -force§a to force update !\n ");
                    }
                    else sender.sendMessage("§cYou don't have permission to do that.");
                }
            }
        }
        else sender.sendMessage("§c§lThe Updater cannot contact the update server.");
    }
    
    private static boolean needUpdate(String currVer, String upVer){
        String[] currentSplits = currVer.split("\\.");
        String[] updateSplits = upVer.split("\\.");
        int count = currentSplits.length;
        
        for(int i = 0; i < count; i++){
            if(Integer.valueOf(updateSplits[i]) > Integer.valueOf(currentSplits[i])) return true;
            else if(Integer.valueOf(updateSplits[i]) < Integer.valueOf(currentSplits[i])) return false;
        }
        return false;
    }
    
    private void askReload(CommandSender sender){
        if(sender instanceof Player){
            sender.sendMessage("§aDo you want to reload you server now? If no, the new version will be used on next restart.");
            sender.sendMessage("§aType §6Yes §aor §cNo§a.");
            PlayerChat.asking.put((Player) sender, true);
        }
        else sender.sendMessage("§aNow you need to reload/restart your server !");
    }
    
}
