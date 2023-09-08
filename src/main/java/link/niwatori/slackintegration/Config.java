package link.niwatori.slackintegration;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.Objects;

public class Config {
    FileConfiguration fileConfiguration;

    public Config(FileConfiguration fileConfiguration) {
        this.fileConfiguration = fileConfiguration;
    }

    public int version() {
        return this.fileConfiguration.getInt("Version", 1);
    }

    public boolean validateSlackConfig() {
        return !Objects.equals(this.slackToken(), "") && !Objects.equals(this.slackSocketToken(), "");
    }

    public String slackToken() {
        return this.fileConfiguration.getString("SlackToken", "");
    }

    public String slackSocketToken() {
        return this.fileConfiguration.getString("SlackSocketToken", "");
    }

    public String slackBotName() {
        return this.fileConfiguration.getString("SlackBotName", "");
    }

    public String slackBotIconEmoji() {
        return this.fileConfiguration.getString("SlackBotIconEmoji", "");
    }

    public String slackBotIconUrl() {
        return this.fileConfiguration.getString("SlackBotIconUrl", "");
    }

    public boolean chatSyncEnabled() {
        return !Objects.equals(this.chatSyncSlackChannelId(), "");
    }
    public String chatSyncSlackChannelId() {
        return this.fileConfiguration.getString("ChatSync.SlackChannelId", "");
    }
    public boolean chatSyncMessageServerStartEnabled() {
        return !Objects.equals(this.chatSyncMessageServerStart(), "");
    }
    public String chatSyncMessageServerStart() {
        return this.fileConfiguration.getString("ChatSync.Message.ServerStart", "");
    }
    public boolean chatSyncMessageServerEndEnabled() {
        return !Objects.equals(this.chatSyncMessageServerEnd(), "");
    }
    public String chatSyncMessageServerEnd() {
        return this.fileConfiguration.getString("ChatSync.Message.ServerEnd", "");
    }
    public boolean chatSyncMessagePlayerJoinEnabled() {
        return this.chatSyncMessagePlayerJoin() != "";
    }
    public String chatSyncMessagePlayerJoin() {
        return this.fileConfiguration.getString("ChatSync.Message.PlayerJoin", "");
    }
    public boolean chatSyncMessagePlayerQuitEnabled() {
        return !Objects.equals(this.chatSyncMessagePlayerQuit(), "");
    }
    public String chatSyncMessagePlayerQuit() {
        return this.fileConfiguration.getString("ChatSync.Message.PlayerQuit", "");
    }
    public boolean chatSyncMessagePlayerDeathEnabled() {
        return !Objects.equals(this.chatSyncMessagePlayerDeath(), "");
    }
    public String chatSyncMessagePlayerDeath() {
        return this.fileConfiguration.getString("ChatSync.Message.PlayerDeath", "");
    }
    public boolean chatSyncMessagePlayerChatEnabled() {
        return !Objects.equals(this.chatSyncMessagePlayerChat(), "");
    }
    public String chatSyncMessagePlayerChat() {
        return this.fileConfiguration.getString("ChatSync.Message.PlayerChat", "");
    }
    public boolean chatSyncMessageFromSlackChatEnabled() {
        return !Objects.equals(this.chatSyncMessageFromSlackChat(), "");
    }
    public String chatSyncMessageFromSlackChat() {
        return this.fileConfiguration.getString("ChatSync.Message.FromSlackChat", "");
    }
    public String chatSyncMessagePlayerChatName() {
        return this.fileConfiguration.getString("ChatSync.Message.PlayerChatName", "");
    }
    public boolean chatSyncMessageSlackMentionEnabled() {
        return !Objects.equals(this.chatSyncMessageSlackMentionPrefix(), "");
    }
    public String chatSyncMessageSlackMentionPrefix() {
        return this.fileConfiguration.getString("ChatSync.Message.SlackMentionPrefix", "");
    }
    public boolean chatSyncSlackChannelTopicEnabled() {
        return this.fileConfiguration.getBoolean("ChatSync.StatusChannelTopic.Enabled", false);
    }
    public String chatSyncSlackChannelTopicOnline() {
        return this.fileConfiguration.getString("ChatSync.StatusChannelTopic.Online", "");
    }
    public String chatSyncSlackChannelTopicOffline() {
        return this.fileConfiguration.getString("ChatSync.StatusChannelTopic.Offline", "");
    }
    public boolean appHomeOnlineUserCountEnabled() {
        return !Objects.equals(this.appHomeOnlineUserCount(), "");
    }
    public String appHomeOnlineUserCount() {
        return this.fileConfiguration.getString("AppHome.OnlineUserCount", "");
    }
    public boolean appHomeOnlineUserList() {
        return this.fileConfiguration.getBoolean("AppHome.OnlineUserList", false);
    }
    public boolean consoleEnabled() {
        return !Objects.equals(this.consoleSlackChannelId(), "");
    }
    public String consoleSlackChannelId() {
        return this.fileConfiguration.getString("Console.SlackChannelId", "");
    }
    public boolean consoleExecutable() {
        return this.fileConfiguration.getBoolean("Console.Executable", false);
    }
    public boolean consoleExecutableAllUser() {
        return this.consoleExecutableSlackUserNames().length == 0;
    }
    public String[] consoleExecutableSlackUserNames() {
        String value = this.fileConfiguration.getString("Console.ExecutableSlackUserNames", "");
        if (value.equals("")) {
            return new String[] {};
        }
        return value.split(",");
    }
}