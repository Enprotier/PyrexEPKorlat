package org.pyrex.pyrexepfix.listeners;

import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.pyrex.pyrexepfix.PyrexEPFix;

import java.util.HashMap;
import java.util.UUID;

public class DeathListener implements Listener {
    private final HashMap<UUID, Long> cd;
    private final long cIdo;

    public DeathListener(PyrexEPFix plugin) {
        this.cd = new HashMap<>();
        this.cIdo = plugin.getConfig().getLong("cooldown-time", 1) * 1000;
    }

    @EventHandler
    public void halalozas(PlayerDeathEvent event) {
        Player player = event.getEntity();
        UUID playerId = player.getUniqueId();

        player.getWorld().getEntitiesByClass(EnderPearl.class).stream()
                .filter(enderPearl -> enderPearl.getShooter() instanceof Player &&
                        ((Player) enderPearl.getShooter()).getUniqueId().equals(playerId))
                .forEach(EnderPearl::remove);

        cd.put(playerId, System.currentTimeMillis() + cIdo);
    }

    public void clearcd() {
        cd.clear();
    }
}