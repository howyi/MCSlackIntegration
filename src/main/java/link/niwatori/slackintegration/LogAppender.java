package link.niwatori.slackintegration;

import link.niwatori.slackintegration.message.Log;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Objects;

public class LogAppender extends AbstractAppender {

    SlackSender sender;
    FileConfiguration config;

    public LogAppender(SlackSender sender, FileConfiguration config) {
        super("LogAppender", null, null);
        this.sender = sender;
        this.config = config;
        start();
    }

    @Override
    public void append(LogEvent event) {
        LogEvent log = event.toImmutable();
        Log message = new Log(this.config, log.getMessage().getFormattedMessage(), event.getLevel());
        String channelId = config.getString(ConfigKey.CONSOLE_CHANNEL_SLACK_CHANNEL_ID.getKey());
        if (!Objects.equals(channelId, "")) {
            this.sender.sendMessage(message, channelId);
        }
    }
}