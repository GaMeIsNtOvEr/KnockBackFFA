package me.gameisntover.kbffa.knockbackffa.messages;

import me.clip.placeholderapi.PlaceholderAPI;
import me.gameisntover.kbffa.knockbackffa.CustomConfigs.ArenaConfiguration;
import me.gameisntover.kbffa.knockbackffa.CustomConfigs.MessageConfiguration;
import me.gameisntover.kbffa.knockbackffa.CustomConfigs.PlaySoundConfiguration;
import me.gameisntover.kbffa.knockbackffa.CustomConfigs.PlayerConfiguration;
import me.gameisntover.kbffa.knockbackffa.KnockbackFFA;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.Async;

import java.io.File;
import java.io.IOException;

public class JoinLeaveListeners  implements Listener {
    @EventHandler
    public void playerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if (ArenaConfiguration.get().getString("EnabledArena").equalsIgnoreCase("arena1")) {
            if(ArenaConfiguration.get().getString("arena1.world")!=null) {
                double x = ArenaConfiguration.get().getDouble("arena1.x");
                double y = ArenaConfiguration.get().getDouble("arena1.y");
                double z = ArenaConfiguration.get().getDouble("arena1.z");
                World world = Bukkit.getWorld(ArenaConfiguration.get().getString("arena1.world"));
                player.teleport(new Location(world, x, y, z));

            }else {
                ArenaConfiguration.get().set("EnabledArena","arena2");

                }
        } else         if (ArenaConfiguration.get().getString("EnabledArena").equalsIgnoreCase("arena2")) {
            if(ArenaConfiguration.get().getString("arena2.world")!=null) {
                double x = ArenaConfiguration.get().getDouble("arena2.x");
                double y = ArenaConfiguration.get().getDouble("arena2.y");
                double z = ArenaConfiguration.get().getDouble("arena2.z");
                World world = Bukkit.getWorld(ArenaConfiguration.get().getString("arena2.world"));
                player.teleport(new Location(world, x, y, z));

            }
            else {
                ArenaConfiguration.get().set("EnabledArena","arena3");
            }
        } else         if (ArenaConfiguration.get().getString("EnabledArena").equalsIgnoreCase("arena3")) {

            if(ArenaConfiguration.get().getString("arena3.world")!=null) {
                double x = ArenaConfiguration.get().getDouble("arena3.x");
                double y = ArenaConfiguration.get().getDouble("arena3.y");
                double z = ArenaConfiguration.get().getDouble("arena3.z");
                World world = Bukkit.getWorld(ArenaConfiguration.get().getString("arena3.world"));
                player.teleport(new Location(world, x, y, z));

            }
            else {
                ArenaConfiguration.get().set("EnabledArena","arena4");
            }
        } else        if (ArenaConfiguration.get().getString("EnabledArena").equalsIgnoreCase("arena4")) {

            if(ArenaConfiguration.get().getString("arena4.world")!=null) {
                double x = ArenaConfiguration.get().getDouble("arena4.x");
                double y = ArenaConfiguration.get().getDouble("arena4.y");
                double z = ArenaConfiguration.get().getDouble("arena4.z");
                World world = Bukkit.getWorld(ArenaConfiguration.get().getString("arena4.world"));
                player.teleport(new Location(world, x, y, z));

            }
            else {
                ArenaConfiguration.get().set("EnabledArena","arena1");
            }
        }
        ItemStack kbstick = new ItemStack(Material.STICK, 1);
        ItemMeta meta = kbstick.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + "Knocbkack Stick");
        meta.setUnbreakable(true);
        meta.addEnchant(Enchantment.KNOCKBACK, 3, true);
        kbstick.setItemMeta(meta);
        ItemStack enderpearl = new ItemStack(Material.ENDER_PEARL, 16);
        ItemMeta enderpearlmeta = enderpearl.getItemMeta();
        enderpearlmeta.setDisplayName(ChatColor.GREEN + "Ender Pearl");
        enderpearl.setItemMeta(enderpearlmeta);
        Inventory pinventory = player.getInventory();
        pinventory.clear();
        pinventory.addItem(kbstick, enderpearl);
        player.addPotionEffect(PotionEffectType.DAMAGE_RESISTANCE.createEffect(999999999, 255));
        PlayerConfiguration.create(player);
        PlayerConfiguration.get().addDefault("deaths",0);
        PlayerConfiguration.save();
        PlayerConfiguration.save();
        String joinText = MessageConfiguration.get().getString("joinmessage").replace("&", "§");
        joinText = PlaceholderAPI.setPlaceholders(e.getPlayer(), joinText);
        player.playSound(player.getLocation(),Sound.valueOf(PlaySoundConfiguration.get().getString("join")), 1, 1);
        e.setJoinMessage(joinText);
        double x = ArenaConfiguration.get().getDouble("arena1.x");
        double y = ArenaConfiguration.get().getDouble("arena1.y");;
        double z = ArenaConfiguration.get().getDouble("arena1.z");
        World world = Bukkit.getWorld(ArenaConfiguration.get().getString("arena1.world"));
        if (ArenaConfiguration.get().getString("arena1.x") == null ) {
            if(player.isOp()){
                player.sendMessage("§cSpawn is not set! Please set it in the config or /setspawn!");
        }else if (player.isOp()==false){
                player.sendMessage("§cAsk an admin to create the spawnpoint so i can get you there!");
            }
        }
    }
    @EventHandler
    public void playerLeave(PlayerQuitEvent e){
        String leaveText = MessageConfiguration.get().getString("leavemessage").replace("&", "§");        leaveText = PlaceholderAPI.setPlaceholders(e.getPlayer(), leaveText);
        e.setQuitMessage(leaveText);
    }
}