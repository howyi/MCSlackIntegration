package link.niwatori.slackintegration;

public enum ConfigKey {

    SLACK_TOKEN("SlackToken"),
    SLACK_SOCKET_TOKEN("SlackSocketToken"),
    SLACK_BOT_NAME("SlackBotName"),
    SLACK_BOT_ICON_EMOJI("SlackBotIconEmoji"),
    SLACK_BOT_ICON_URL("SlackBotIconUrl"),
    SLACK_CHANNEL_ID("SlackChannelId"),
    MESSAGE_SERVER_START("Message.ServerStart"),
    MESSAGE_SERVER_END("Message.ServerEnd"),
    MESSAGE_PLAYER_JOIN("Message.PlayerJoin"),
    MESSAGE_PLAYER_QUIT("Message.PlayerQuit"),
    MESSAGE_PLAYER_DEATH("Message.PlayerDeath"),
    MESSAGE_PLAYER_CHAT("Message.PlayerChat"),
    MESSAGE_FROM_SLACK_CHAT("Message.FromSlackChat"),
    MESSAGE_PLAYER_CHAT_NAME("Message.PlayerChatName"),
    SLACK_CHANNEL_TOPIC_ENABLED("StatusChannelTopic.Enabled"),
    SLACK_CHANNEL_TOPIC_ONLINE("StatusChannelTopic.Online"),
    SLACK_CHANNEL_TOPIC_OFFLINE("StatusChannelTopic.Offline"),
    APP_HOME_ONLINE_USER_COUNT("AppHome.OnlineUserCount"),
    APP_HOME_ONLINE_USER_LIST("AppHome.OnlineUserList"),
    CONSOLE_CHANNEL_EXECUTABLE("ConsoleChannel.Executable"),
    CONSOLE_CHANNEL_EXECUTABLE_SLACK_USER_NAMES("ConsoleChannel.ExecutableSlackUserNames"),
    CONSOLE_CHANNEL_SLACK_CHANNEL_ID("ConsoleChannel.SlackChannelId");

    private final String key;

    private ConfigKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
}