package fr.TriiNoxYs.SimpleGUIMaker.utils;

import java.lang.reflect.Field;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class TitleUtils{

    public static void sendTitle(Player player, int fadeIn, int stay, int fadeOut, String title, String subtitle){
        if(subtitle == null) subtitle = "";
        CraftPlayer craftplayer = (CraftPlayer) player;
        PlayerConnection connection = craftplayer.getHandle().playerConnection;
        IChatBaseComponent titleJSON = IChatBaseComponent.ChatSerializer.a("{'text': '"+ ChatColor.translateAlternateColorCodes('&', title)+ "'}");
        IChatBaseComponent subtitleJSON = IChatBaseComponent.ChatSerializer.a("{'text': '"+ ChatColor.translateAlternateColorCodes('&', subtitle)+ "'}");
        Packet<?> length = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, titleJSON, fadeIn,stay, fadeOut);
        Packet<?> titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleJSON, fadeIn,stay, fadeOut);
        Packet<?> subtitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, subtitleJSON,fadeIn, stay, fadeOut);
        connection.sendPacket(titlePacket);
        connection.sendPacket(length);
        connection.sendPacket(subtitlePacket);
    }
    
    public static void broadcastTitle(int fadeIn, int stay, int fadeOut, String title, String subtitle){
        for(Player pl : Bukkit.getOnlinePlayers()){
            sendTitle(pl, fadeIn, stay, fadeOut, title, subtitle);
        }
    }
    
    public static void sendActionBar(Player p, String msg){
      IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + msg + "\"}");
      PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte)2);
      ((CraftPlayer)p).getHandle().playerConnection.sendPacket(ppoc);
    }
    
    public static void broadcastActionBar(String msg){
        for(Player pl : Bukkit.getOnlinePlayers()){
            sendActionBar(pl, msg);
        }
    }
    
    public static void sendHeaderAndFooter(Player p, String head, String foot){
      CraftPlayer craftplayer = (CraftPlayer)p;
      PlayerConnection connection = craftplayer.getHandle().playerConnection;
      IChatBaseComponent header = IChatBaseComponent.ChatSerializer.a("{'color': '', 'text': '" + ChatColor.translateAlternateColorCodes('&', head) + "'}");
      IChatBaseComponent footer = IChatBaseComponent.ChatSerializer.a("{'color': '', 'text': '" + ChatColor.translateAlternateColorCodes('&', foot) + "'}");
      PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
      try{
        Field headerField = packet.getClass().getDeclaredField("a");
        headerField.setAccessible(true);
        headerField.set(packet, header);
        headerField.setAccessible(!headerField.isAccessible());
        
        Field footerField = packet.getClass().getDeclaredField("b");
        footerField.setAccessible(true);
        footerField.set(packet, footer);
        footerField.setAccessible(!footerField.isAccessible());
      }
      catch (Exception e){
        e.printStackTrace();
      }
      connection.sendPacket(packet);
    }
    
}