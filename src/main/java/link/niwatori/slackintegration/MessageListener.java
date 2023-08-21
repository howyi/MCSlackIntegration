package link.niwatori.slackintegration;

import link.niwatori.slackintegration.message.InGameChat;
import link.niwatori.slackintegration.message.PlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.text.MessageFormat;
import java.util.Objects;

public class MessageListener implements Listener {
    SlackSender sender;
    FileConfiguration config;

    public MessageListener(
            FileConfiguration config,
            SlackSender sender
        ) {
        this.config = config;
        this.sender = sender;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChat(AsyncPlayerChatEvent event) {
        if (!Objects.equals(this.config.getString(ConfigKey.MESSAGE_PLAYER_CHAT.getKey()), "")) {
            InGameChat message = new InGameChat(this.config, event);
            this.sender.sendMessage(message);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event) {
        if (config.getBoolean(ConfigKey.SLACK_CHANNEL_TOPIC_ENABLED.getKey())) {
            String topic = MessageFormat.format(config.getString(ConfigKey.SLACK_CHANNEL_TOPIC_ONLINE.getKey(), ""), Bukkit.getOnlinePlayers().size());
            this.sender.setTopic(topic);
        }

        if (!Objects.equals(this.config.getString(ConfigKey.MESSAGE_PLAYER_JOIN.getKey()), "")) {
            String text = MessageFormat.format(this.config.getString(ConfigKey.MESSAGE_PLAYER_JOIN.getKey(), ""), event.getPlayer().getDisplayName());
            PlayerInfo message = new PlayerInfo(this.config, event.getPlayer(), text);
            this.sender.sendMessage(message);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent event) {
        if (!Objects.equals(this.config.getString(ConfigKey.MESSAGE_PLAYER_QUIT.getKey()), "")) {
            String text = MessageFormat.format(this.config.getString(ConfigKey.MESSAGE_PLAYER_QUIT.getKey(), ""), event.getPlayer().getDisplayName());
            PlayerInfo message = new PlayerInfo(this.config, event.getPlayer(), text);
            this.sender.sendMessage(message);
        }

        if (config.getBoolean(ConfigKey.SLACK_CHANNEL_TOPIC_ENABLED.getKey())) {
            String topic = MessageFormat.format(config.getString(ConfigKey.SLACK_CHANNEL_TOPIC_ONLINE.getKey(), ""), Bukkit.getOnlinePlayers().size() - 1);
            this.sender.setTopic(topic);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDeath(PlayerDeathEvent event) {
        if (!Objects.equals(this.config.getString(ConfigKey.MESSAGE_PLAYER_DEATH.getKey()), "")) {
            String text = MessageFormat.format(this.config.getString(ConfigKey.MESSAGE_PLAYER_DEATH.getKey(), ""), event.getDeathMessage());
            PlayerInfo message = new PlayerInfo(this.config, event.getEntity().getPlayer(), text);
            this.sender.sendMessage(message);
        }
    }
}