package link.niwatori.slackchannelsrv;

import link.niwatori.slackchannelsrv.message.Info;
import link.niwatori.slackchannelsrv.message.Message;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.MessageFormat;
import java.util.Objects;


public final class SlackChannelSRV extends JavaPlugin {

    SlackSender sender;
    FileConfiguration config;

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        this.config = getConfig();
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
    }

    @Override
    public void onDisable() {
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
}
