package org.pyrex.pyrexepkorlat.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.pyrex.pyrexepkorlat.PyrexEPKorlat;

public class ReloadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("pyrexepkorlat.reload")) {
            Plugin plugin = PyrexEPKorlat.getInstance();
            plugin.reloadConfig();
            sender.sendMessage("§aPyrexEPKorlat config reloadolva!");
            return true;
        } else {
            return true;
        }
    }
}
