package org.pyrex.pyrexepfix;

import org.bukkit.plugin.java.JavaPlugin;
import org.pyrex.pyrexepfix.listeners.EnderPearlListener;
import org.pyrex.pyrexepfix.listeners.DeathListener;
import org.pyrex.pyrexepfix.commands.ReloadCommand;

import java.util.Objects;

public class PyrexEPFix extends JavaPlugin {

    private static PyrexEPFix instance;
    private DeathListener deathListener;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new EnderPearlListener(), this);
        this.deathListener = new DeathListener(this);
        getServer().getPluginManager().registerEvents(this.deathListener, this);
        
        Objects.requireNonNull(this.getCommand("pyrexepfix")).setExecutor(new ReloadCommand());
        getLogger().info("PyrexEPFix Elindult fr");
    }

    @Override
    public void onDisable() {
        if (this.deathListener != null) {
            this.deathListener.clearcd();
        }
        getLogger().info("PyrexEPFix leall sigma!");
    }

    public static PyrexEPFix getInstance() {
        return instance;
    }
}