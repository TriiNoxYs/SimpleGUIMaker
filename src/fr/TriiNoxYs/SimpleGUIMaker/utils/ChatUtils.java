package fr.TriiNoxYs.SimpleGUIMaker.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ChatUtils {
    
    public static String prefix = "§8[§4§lS§6imple§4§lGUI§6Maker§8]§r ";
	
	public static void broadcast(String msg){
		Bukkit.broadcastMessage(prefix + msg);
	}
	
	public static void sendMsg(Player player, String msg){
		player.sendMessage(prefix + msg);
	}
	
}