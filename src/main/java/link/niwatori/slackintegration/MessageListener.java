package link.niwatori.slackintegration;

import com.slack.api.methods.SlackApiException;
import com.slack.api.model.User;
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

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public void onChat(AsyncPlayerChatEvent event) throws SlackApiException, IOException {
        if (this.config.chatSyncEnabled() && this.config.chatSyncMessagePlayerChatEnabled()) {
            String text = event.getMessage();
            if (this.config.chatSyncMessageSlackMentionEnabled()) {
                String prefix = this.config.chatSyncMessageSlackMentionPrefix();
                Pattern mentionPattern = Pattern.compile("^"+prefix+"(\\S+) *(.*)");
                Matcher mentionMatcher = mentionPattern.matcher(text);
                if (mentionMatcher.find()) {
                    if (Objects.equals(mentionMatcher.group(1), "here") ||
                            Objects.equals(mentionMatcher.group(1), "channel") ||
                            Objects.equals(mentionMatcher.group(1), "everyone")
                    ) {
                        text = text.replaceFirst("^"+prefix+mentionMatcher.group(1), "<!"+mentionMatcher.group(1)+"> ");
                    } else {
                        List<User> users = this.sender.getUsers();
                        for (User user : users) {
                            String name = user.getProfile().getDisplayNameNormalized();
                            if (Objects.equals(mentionMatcher.group(1), name)) {
                                text = text.replaceFirst("^"+prefix+mentionMatcher.group(1), "<@"+user.getId()+"> ");
                            }
                        }
                    }
                }
            }
            InGameChat message = new InGameChat(this.config, event, text);
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