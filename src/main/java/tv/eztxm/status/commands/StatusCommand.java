package tv.eztxm.status.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import tv.eztxm.status.Status;
import tv.eztxm.status.utils.FileManager;
import java.util.Collections;
import java.util.List;

public class StatusCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player))return true;
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reset")) {
                FileManager.playersCfg.set(player.getUniqueId().toString(), null);
                FileManager.save();
            } else {
                if (!FileManager.cfg.getList("Teamlist").contains(args[0]))
                    player.sendMessage(Status.prefix + "Unbekannter Status");
                else {
                    FileManager.playersCfg.set(player.getUniqueId().toString(), args[0]);
                    FileManager.save();
                    player.sendMessage(Status.prefix + "Dein Status ist nun §b" + args[0]);
                }
            }
        } else {
            player.sendMessage(Status.prefix + "/status <status>");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        return Collections.emptyList();
    }
}
