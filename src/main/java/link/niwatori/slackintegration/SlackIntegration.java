package link.niwatori.slackintegration;

import link.niwatori.slackintegration.message.Info;
import org.bukkit.plugin.java.JavaPlugin;
import org.apache.logging.log4j.LogManager;

import java.text.MessageFormat;
import java.util.logging.Level;

public final class SlackIntegration extends JavaPlugin {

    SlackSender sender;
    SlackEventListener slackEventListener;
    Config config;

    int CURRENT_CONFIG_VERSION = 2;

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        this.config = new Config(getConfig());

        if (this.config.version() != CURRENT_CONFIG_VERSION) {
            this.getLogger().log(Level.WARNING, "The version of the configuration file is out of date. Please see the documentation (https://howyi.github.io/MCSlackIntegration/update_plugin/) to update it to the latest format.");
            return;
        }

        if (!this.config.validateSlackConfig()) {
            this.getLogger().log(Level.WARNING, "Slack app parameters not defined in config.yml");
            return;
        }

        this.sender = new SlackSender(this.config.slackToken());

        // minecraft -> server
        getServer().getPluginManager().registerEvents(new MessageListener(this.config, this.sender), this);

        // slack -> server
        this.slackEventListener = new SlackEventListener(this.config, this.sender);
        this.slackEventListener.connect(this);

        // minecraft log -> server
        LogAppender appender = new LogAppender(this.sender, this.config);
        logger.addAppender(appender);

        if (config.chatSyncEnabled()) {
            if (config.chatSyncSlackChannelTopicEnabled()) {
                String topic = MessageFormat.format(config.chatSyncSlackChannelTopicOnline(), 0);
                this.sender.setTopic(topic, config.chatSyncSlackChannelId());
            }
            if (this.config.chatSyncMessageServerStartEnabled()) {
                Info message = new Info(this.config, this.config.chatSyncMessageServerStart());
                this.sender.sendMessage(message, this.config.chatSyncSlackChannelId());
            }
        }
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
        if (config.chatSyncEnabled()) {
            if (this.config.chatSyncSlackChannelTopicEnabled()) {
                this.sender.setTopic(config.chatSyncSlackChannelTopicOffline(), config.chatSyncSlackChannelId());
            }
            if (this.config.chatSyncMessageServerEndEnabled()) {
                Info message = new Info(this.config, this.config.chatSyncMessageServerEnd());
                this.sender.sendMessage(message, this.config.chatSyncSlackChannelId());
            };
        }
    }

    private static final org.apache.logging.log4j.core.Logger logger = (org.apache.logging.log4j.core.Logger) LogManager.getRootLogger();
}
