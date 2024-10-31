package org.pyrex.pyrexepfix.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.pyrex.pyrexepfix.PyrexEPFix;

public class ReloadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (sender.hasPermission("pyrexepfix.admin")) {
            Plugin plugin = PyrexEPFix.getInstance();
            plugin.reloadConfig();
            sender.sendMessage("§aPyrexEPFix config reloadolva!");
        }
        return true;
    }
}
