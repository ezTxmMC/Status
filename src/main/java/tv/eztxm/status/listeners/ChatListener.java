package tv.eztxm.status.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import tv.eztxm.status.utils.FileManager;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        event.setFormat(FileManager.cfg.getString("Teams." + FileManager.playersCfg.getString(player.getUniqueId().toString()) + ".ChatFormat")
                .replace('&', '§')
                .replace("%player", player.getName())
                .replace("%message", event.getMessage()));
    }
}
