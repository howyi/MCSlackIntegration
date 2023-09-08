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
    Config config;

    public MessageListener(
            Config config,
            SlackSender sender
        ) {
        this.config = config;
        this.sender = sender;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onChat(AsyncPlayerChatEvent event) {
        if (this.config.chatSyncEnabled() && this.config.chatSyncMessagePlayerChatEnabled()) {
            InGameChat message = new InGameChat(this.config, event);
            this.sender.sendMessage(message, this.config.chatSyncSlackChannelId());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onJoin(PlayerJoinEvent event) {
        if (this.config.chatSyncEnabled()) {
            if (this.config.chatSyncSlackChannelTopicEnabled()) {
                String topic = MessageFormat.format(this.config.chatSyncSlackChannelTopicOnline(), Bukkit.getOnlinePlayers().size());
                this.sender.setTopic(topic, this.config.chatSyncSlackChannelId());
            }

            if (this.config.chatSyncMessagePlayerJoinEnabled()) {
                String text = MessageFormat.format(this.config.chatSyncMessagePlayerJoin(), event.getPlayer().getDisplayName());
                PlayerInfo message = new PlayerInfo(this.config, event.getPlayer(), text);
                this.sender.sendMessage(message, this.config.chatSyncSlackChannelId());
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent event) {
        if (this.config.chatSyncEnabled()) {
            if (this.config.chatSyncMessagePlayerQuitEnabled()) {
                String text = MessageFormat.format(this.config.chatSyncMessagePlayerQuit(), event.getPlayer().getDisplayName());
                PlayerInfo message = new PlayerInfo(this.config, event.getPlayer(), text);
                this.sender.sendMessage(message, this.config.chatSyncSlackChannelId());
            }

            if (this.config.chatSyncSlackChannelTopicEnabled()) {
                String topic = MessageFormat.format(this.config.chatSyncSlackChannelTopicOnline(), Bukkit.getOnlinePlayers().size() - 1);
                this.sender.setTopic(topic, this.config.chatSyncSlackChannelId());
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDeath(PlayerDeathEvent event) {
        if (this.config.chatSyncEnabled() && this.config.chatSyncMessagePlayerDeathEnabled()) {
            String text = MessageFormat.format(this.config.chatSyncMessagePlayerDeath(), event.getDeathMessage());
            PlayerInfo message = new PlayerInfo(this.config, event.getEntity().getPlayer(), text);
            this.sender.sendMessage(message, this.config.chatSyncSlackChannelId());
        }
    }
}