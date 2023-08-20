package link.niwatori.slackchannelsrv;

public enum ConfigKey {

    SLACK_TOKEN("SlackToken"),
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
    MESSAGE_PLAYER_CHAT_NAME("Message.PlayerChatName"),
    SLACK_CHANNEL_TOPIC_ENABLED("StatusChannelTopic.Enabled"),
    SLACK_CHANNEL_TOPIC_ONLINE("StatusChannelTopic.Online"),
    SLACK_CHANNEL_TOPIC_OFFLINE("StatusChannelTopic.Offline");

    private final String key;

    private ConfigKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
}