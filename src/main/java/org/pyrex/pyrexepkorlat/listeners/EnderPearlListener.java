package org.pyrex.pyrexepkorlat.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.projectiles.ProjectileSource;
import org.jetbrains.annotations.NotNull;
import org.pyrex.pyrexepkorlat.PyrexEPKorlat;

public class EnderPearlListener implements Listener {

    @EventHandler
    public void onProjectileHit(@NotNull ProjectileHitEvent event) {
        if (event.getHitBlock() == null) return;

        if (event.getEntity().getType() != EntityType.ENDER_PEARL) return;

        ProjectileSource projectileSource = event.getEntity().getShooter();
        if (!(projectileSource instanceof Player)) return;
        Player shooter = (Player) projectileSource;

        String worldName = event.getHitBlock().getLocation().getWorld().getName();
        int maxY = PyrexEPKorlat.getInstance().getConfig().getInt("worlds." + worldName + ".max-y-limit", 60); // Default to 60 if not found

        if (event.getHitBlock().getLocation().getY() > maxY) {
            event.getEntity().remove();
            event.setCancelled(true);
            shooter.sendMessage("§cNem mehetsz ilyen magasra ender pearlel!");
            return;
        }

        if (!event.getHitBlock().getLocation().getWorld().getWorldBorder().isInside(event.getHitBlock().getLocation())) {
            event.getEntity().remove();
            event.setCancelled(true);
        }
    }
}
