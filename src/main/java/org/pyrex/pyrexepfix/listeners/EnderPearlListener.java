package org.pyrex.pyrexepfix.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.pyrex.pyrexepfix.PyrexEPFix;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class EnderPearlListener implements Listener {

    private final Set<EnderPearl> aktivok = new HashSet<>();

    public EnderPearlListener() {
        global();
    }

    private void global() {
        new BukkitRunnable() {
            @Override
            public void run() {
                Set<EnderPearl> toRem = new HashSet<>();
                for (EnderPearl enderPearl : aktivok) {
                    if (enderPearl.isDead() || !enderPearl.isValid()) {
                        toRem.add(enderPearl);
                        continue;
                    }

                    check(enderPearl);
                }
                aktivok.removeAll(toRem);
            }
        }.runTaskTimer(PyrexEPFix.getInstance(), 1L, 1L);
    }

    private void check(EnderPearl enderPearl) {
        Location pearlLoc = enderPearl.getLocation();
        Vector irany = enderPearl.getVelocity().normalize();
        Player player = (Player) enderPearl.getShooter();

        for (int i = 0; i < 5; i++) {
            Location checkLoc = pearlLoc.clone().add(irany.clone().multiply(i * 0.1));
            if (checkLoc.getBlock().getType() == Material.TRIPWIRE) {
                Location tpLoc = checkLoc.clone();
                tpLoc.setDirection(Objects.requireNonNull(player).getLocation().getDirection());
                player.teleport(tpLoc, PlayerTeleportEvent.TeleportCause.ENDER_PEARL);

                enderPearl.remove();
                aktivok.remove(enderPearl);
                return;
            }
        }
    }

    @EventHandler
    public void enderPeal(@NotNull ProjectileLaunchEvent event) {
        if (event.getEntityType() != EntityType.ENDER_PEARL) return;
        if (!(event.getEntity().getShooter() instanceof Player)) return;

        EnderPearl enderPearl = (EnderPearl) event.getEntity();
        aktivok.add(enderPearl);
    }

    @EventHandler
    public void land(ProjectileHitEvent event) {
        if (event.getEntityType() != EntityType.ENDER_PEARL) return;
        if (!(event.getEntity().getShooter() instanceof Player)) return;

        EnderPearl ep = (EnderPearl) event.getEntity();
        Player player = (Player) ep.getShooter();
        Location loc = ep.getLocation();

        magassag(loc, player, ep);
        aktivok.remove(ep);
    }

    private void magassag(Location pearlLoc, Player player, EnderPearl enderPearl) {
        String worldName = pearlLoc.getWorld().getName();
        int maxY = PyrexEPFix.getInstance().getConfig().getInt("worlds." + worldName + ".max-y-limit", 60);

        if (pearlLoc.getY() > maxY) {
            enderPearl.remove();
            player.sendMessage("§cNem mehetsz ilyen magasra ender pearlel!");
            return;
        }

        if (!pearlLoc.getWorld().getWorldBorder().isInside(pearlLoc)) {
            enderPearl.remove();
        }
    }
}