package fr.TriiNoxYs.SimpleGUIMaker.listeners.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import fr.TriiNoxYs.SimpleGUIMaker.handlers.Menus;


public class PlayerQuit implements Listener{
    
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e){
        Menus.creatingMenu.remove(e.getPlayer());
    }
    
}
