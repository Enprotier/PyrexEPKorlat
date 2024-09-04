package org.pyrex.pyrexepkorlat;

import org.bukkit.plugin.java.JavaPlugin;
import org.pyrex.pyrexepkorlat.listeners.EnderPearlListener;
import org.pyrex.pyrexepkorlat.commands.ReloadCommand;

public class PyrexEPKorlat extends JavaPlugin {

    private static PyrexEPKorlat instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new EnderPearlListener(), this);
        this.getCommand("pyrexepkorlatreload").setExecutor(new ReloadCommand());
        getLogger().info("PyrexEPKorlat Elindult #miert nem megy a kurva worldguard");
    }

    @Override
    public void onDisable() {
        getLogger().info("PyrexEPKorlat leall");
    }

    public static PyrexEPKorlat getInstance() {
        return instance;
    }
}
