package me.gameisntover.kbffa.api;

import me.gameisntover.kbffa.KnockbackFFA;
import me.gameisntover.kbffa.customconfig.CosmeticConfiguration;
import me.gameisntover.kbffa.customconfig.Knocker;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class KnockbackFFAAPI {


    public boolean BungeeMode() {
        return KnockbackFFA.getInstance().getConfig().getBoolean("Bungee-Mode");
    }

    public String selectedCosmetic(Knocker knocker) {
        return knocker.getConfig().getString("selected-cosmetic");
    }

    public String selectedKit(Knocker knocker) {
        return knocker.getConfig().getString("selected-kit");
    }

    public void loadCosmetic(Player player, String cosmeticName) {
        if (cosmeticName == null) return;
            String cosmeticType = CosmeticConfiguration.get().getString(cosmeticName + ".type");
            if (cosmeticType == null) return;
                if (cosmeticType.equalsIgnoreCase("KILL_PARTICLE")) player.spawnParticle(Particle.valueOf(CosmeticConfiguration.get().getString(cosmeticName + ".effect-type")), player.getLocation(), CosmeticConfiguration.get().getInt(cosmeticName + ".amount"));
                if (CosmeticConfiguration.get().getString(CosmeticConfiguration.get().getString(cosmeticName + ".sound")) != null) player.playSound(player.getLocation(), Sound.valueOf(CosmeticConfiguration.get().getString(cosmeticName + ".sound")), CosmeticConfiguration.get().getInt(cosmeticName + ".volume"), CosmeticConfiguration.get().getInt(cosmeticName + ".pitch"));
                if (cosmeticType.equalsIgnoreCase("SOUND")) {
                    List<String> soundList = CosmeticConfiguration.get().getStringList(cosmeticName + ".sounds");
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < soundList.size(); i++) {
                                String sound = soundList.get(i).substring(0, soundList.get(i).indexOf(":"));
                                float pitch = Float.parseFloat(soundList.get(i).substring(soundList.get(i).indexOf(":") + 1, soundList.get(i).indexOf(",")));
                                float volume = Float.parseFloat(soundList.get(i).substring(soundList.get(i).indexOf(",") + 1));
                                player.getWorld().playSound(player.getLocation(), Sound.valueOf(sound), volume, pitch);
                                if (i == soundList.size() - 1) cancel();
                            }
                        }
                    }.runTaskTimer(KnockbackFFA.getInstance(), 0, CosmeticConfiguration.get().getInt(cosmeticName + ".delay"));
                }
    }

}
