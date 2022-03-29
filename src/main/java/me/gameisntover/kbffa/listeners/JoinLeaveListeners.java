package me.gameisntover.kbffa.listeners;

import me.gameisntover.kbffa.KnockbackFFA;
import me.gameisntover.kbffa.api.KnockbackFFAKit;
import me.gameisntover.kbffa.customconfig.Knocker;
import me.gameisntover.kbffa.util.Sounds;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinLeaveListeners implements Listener {
    @EventHandler
    public void playerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        Knocker knocker = KnockbackFFA.getInstance().getKnocker(player);
        if (knocker.getConfig().getString("deaths") != null) return;
        knocker.getConfig().set("deaths", 0);
        knocker.getConfig().set("kills", 0);
        knocker.getConfig().set("owned-kits", knocker.getConfig().getStringList("owned-kits").add("Default"));
        knocker.getConfig().set("selected-kit", "Default");
        knocker.saveConfig();
        if (!KnockbackFFA.getInstance().getAPI().BungeeMode()) knocker.setInGame(false);
        if (KnockbackFFA.getInstance().getConfig().getBoolean("joinsound"))
            player.playSound(player.getLocation(), Sound.valueOf(Sounds.PLAYER_JOIN.toString()), 1, 1);
        if (KnockbackFFA.getInstance().getTempArenaManager().getEnabledArena() == null) knocker.setInGame(false);
        else {
            if (KnockbackFFA.getInstance().getAPI().BungeeMode()) {
                KnockbackFFA.getInstance().getTempArenaManager().teleportPlayerToArena(player);
                KnockbackFFAKit kitManager = new KnockbackFFAKit();
                player.getInventory().clear();
                kitManager.lobbyItems(player);
                knocker.setInGame(KnockbackFFA.getInstance().getAPI().BungeeMode());
            } else {
                if (!knocker.isInGame()) {
                    KnockbackFFA.getInstance().getTempArenaManager().teleportToMainLobby(player);
                    knocker.setInGame(KnockbackFFA.getInstance().getAPI().BungeeMode());
                }
            }
        }
    }

    @EventHandler
    public void playerLeave(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        Knocker knocker = KnockbackFFA.getInstance().getKnocker(player);
        knocker.setInGame(false);
    }
}
