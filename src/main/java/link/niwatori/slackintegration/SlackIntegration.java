package link.niwatori.slackintegration;

import link.niwatori.slackintegration.message.Info;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.apache.logging.log4j.LogManager;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.logging.Level;

public final class SlackIntegration extends JavaPlugin {

    SlackSender sender;
    SlackEventListener slackEventListener;
    FileConfiguration config;

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        this.config = getConfig();

        String slackToken = config.getString(ConfigKey.SLACK_TOKEN.getKey());
        String slackSocketToken = config.getString(ConfigKey.SLACK_SOCKET_TOKEN.getKey());
        String slackChannelId = config.getString(ConfigKey.SLACK_CHANNEL_ID.getKey());

        if (Objects.equals(slackToken, "") || Objects.equals(slackSocketToken, "") || Objects.equals(slackChannelId, "")) {
            this.getLogger().log(Level.WARNING, "Slack app parameters not defined in config.yml");
            return;
        }

        this.sender = new SlackSender(
                config.getString(ConfigKey.SLACK_TOKEN.getKey()),
                config.getString(ConfigKey.SLACK_CHANNEL_ID.getKey())
        );
        getServer().getPluginManager().registerEvents(new MessageListener(this.config, this.sender), this);

        if (config.getBoolean(ConfigKey.SLACK_CHANNEL_TOPIC_ENABLED.getKey())) {
            String topic = MessageFormat.format(config.getString(ConfigKey.SLACK_CHANNEL_TOPIC_ONLINE.getKey(), ""), 0);
            this.sender.setTopic(topic);
        }

        String serverStartMessage = config.getString(ConfigKey.MESSAGE_SERVER_START.getKey());
        if (!Objects.equals(serverStartMessage, "")) {
            Info message = new Info(this.config, serverStartMessage);
            this.sender.sendMessage(message);
        }

        this.slackEventListener = new SlackEventListener(this.config, this.sender);
        this.slackEventListener.connect(this);

        LogAppender appender = new LogAppender(this.sender, this.config);
        logger.addAppender(appender);
    }

    @Override
    public void onDisable() {
        if (this.sender == null) {
            return;
        }

        try {
            this.slackEventListener.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Plugin shutdown logic
        if (config.getBoolean(ConfigKey.SLACK_CHANNEL_TOPIC_ENABLED.getKey())) {
            this.sender.setTopic(config.getString(ConfigKey.SLACK_CHANNEL_TOPIC_OFFLINE.getKey()));
        }

        String serverEndMessage = config.getString(ConfigKey.MESSAGE_SERVER_END.getKey());
        if (!Objects.equals(serverEndMessage, "")) {
            Info message = new Info(this.config, serverEndMessage);
            this.sender.sendMessage(message);
        };
    }

    private static final org.apache.logging.log4j.core.Logger logger = (org.apache.logging.log4j.core.Logger) LogManager.getRootLogger();
}
