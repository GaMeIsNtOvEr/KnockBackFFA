package me.gameisntover.kbffa.gui;

import lombok.Data;
import me.gameisntover.kbffa.KnockbackFFA;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.inventory.Inventory;

import java.util.Map;

@Data
public class GUI implements Listener {
    private Inventory inventory;
    private String name;
    private short layers;
    private Map<Integer, Button> buttons;
    public GUI(String name , short guiLayers){
    setInventory(Bukkit.createInventory(null,guiLayers*9,name));
    setName(name);
    setLayers(guiLayers);
    Bukkit.getPluginManager().registerEvents(this,KnockbackFFA.getInstance());
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        if (e.getInventory() == inventory){
            e.setCancelled(true);
        buttons.get(e.getSlot()).onClick(e);
        }
    }
    @EventHandler
    public void onInventoryItemMove(InventoryMoveItemEvent e){
        if (e.getSource() == inventory || e.getDestination() == inventory || e.getInitiator() == inventory) e.setCancelled(true);
    }
    public void add(Button button){
        getInventory().addItem(button.getItem());
        buttons.put(button.getSlot(),button);
    }
    public void add(Button button,int slot){
        getInventory().setItem(slot,button.getItem());
        buttons.put(slot,button);
    }
    public void open(Player player){
        player.openInventory(getInventory());
    }
}
