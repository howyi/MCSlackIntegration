package link.niwatori.slackintegration;

import link.niwatori.slackintegration.message.Log;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;

public class LogAppender extends AbstractAppender {

    SlackSender sender;
    Config config;

    public LogAppender(SlackSender sender, Config config) {
        super("LogAppender", null, null);
        this.sender = sender;
        this.config = config;
        start();
    }

    @Override
    public void append(LogEvent event) {
        if (this.config.consoleEnabled()) {
            LogEvent log = event.toImmutable();
            Log message = new Log(this.config, log.getMessage().getFormattedMessage(), event.getLevel());
            this.sender.sendMessage(message, this.config.consoleSlackChannelId());
        }
    }
}