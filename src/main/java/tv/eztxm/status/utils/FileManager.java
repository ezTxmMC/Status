package tv.eztxm.status.utils;

import org.bukkit.configuration.file.YamlConfiguration;
import tv.eztxm.status.Status;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileManager {
    public static File folder = new File("plugins/Status");
    public static File file = new File("plugins/Status/config.yml");
    public static File playersFile = new File("plugins/Status/playerstatus.yml");
    public static YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
    public static YamlConfiguration playersCfg = YamlConfiguration.loadConfiguration(playersFile);
    private static final List<String> teamList = new ArrayList<>();

    public static void save() {
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            playersCfg.save(playersFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Status.prefix = Objects.requireNonNull(cfg.getString("Prefix")).replace('&', '§');
    }

    public static void setupFiles() {
        if (!folder.exists())
            folder.mkdir();
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!playersFile.exists()) {
            try {
                playersFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!teamList.contains("Default")) {
            teamList.add("Default");
        }
        cfg.addDefault("Prefix", "&eStatus &8| &7");
        cfg.addDefault("Teamlist", teamList);
        cfg.addDefault("Teams.Default.Prefix", "&7Default");
        cfg.addDefault("Teams.Default.Suffix", "");
        cfg.addDefault("Teams.Default.Color", "GRAY");
        cfg.addDefault("Teams.Default.ChatFormat", "&7Default &8| %player &8>> &7%message");
        cfg.options().copyDefaults(true);
        save();
    }
}
