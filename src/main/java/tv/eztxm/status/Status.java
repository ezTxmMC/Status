package tv.eztxm.status;

import com.github.lalyos.jfiglet.FigletFont;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import tv.eztxm.status.commands.StatusCommand;
import tv.eztxm.status.listeners.ChatListener;
import tv.eztxm.status.utils.FileManager;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static tv.eztxm.status.utils.FileManager.*;

public final class Status extends JavaPlugin {
    public static String prefix;
    private static final List<String> teamList = (List<String>) cfg.getList("Teamlist");

    @Override
    public void onEnable() {
        FileManager.setupFiles();
        Objects.requireNonNull(getCommand("status")).setExecutor(new StatusCommand());
        Bukkit.getPluginManager().registerEvents(new ChatListener(), this);
        tablistTeams();
        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(all -> {
                    setStatus(all);
                });
            }
        }.runTaskTimerAsynchronously(this, 10, 10);
        try {
            System.out.println(FigletFont.convertOneLine("StatusPlugin") + "\n by ezTxmMC                   Version: 1.0");
            System.out.println(" ");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setStatus(Player player) {
        if (!(playersCfg.getString(player.getUniqueId().toString()) == null)) {
            player.getScoreboard().getTeam(playersCfg.getString(player.getUniqueId().toString())).addEntry(player.getName());
        } else {
            player.getScoreboard().getEntryTeam(player.getName()).removeEntry(player.getName());
        }
    }

    private void tablistTeams() {
        for (int i = 0; i < teamList.size(); i++) {
            createTeam(teamList.get(i), cfg.getString("Teams." + teamList.get(i) + ".Prefix")
                    .replace('&', '§'), cfg.getString("Teams." + teamList.get(i) + ".Suffix")
                    .replace('&', '§'), ChatColor.valueOf(cfg.getString("Teams." + teamList.get(i) + ".Color")));
        }
    }

    private void createTeam(String name, String prefix, String suffix, ChatColor color) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        Team team = scoreboard.getTeam(name);
        if (team != null) team.unregister();
        team = scoreboard.registerNewTeam(name);
        if (prefix != null) team.setPrefix(prefix + " ");
        if (suffix != null) team.setSuffix(" " + suffix);
        if (color != null) team.setColor(color);
    }
}
