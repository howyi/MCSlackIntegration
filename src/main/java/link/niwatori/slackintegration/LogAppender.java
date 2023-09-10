package link.niwatori.slackintegration;

import link.niwatori.slackintegration.message.Log;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;

import java.util.regex.Pattern;

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
            String formattedMessage = log.getMessage().getFormattedMessage();
            this.sender.sendMessage(new Log(this.config, aggressiveStrip(formattedMessage), event.getLevel()), this.config.consoleSlackChannelId());
        }
    }

    private static final Pattern aggressiveStripPattern = Pattern.compile("\u001B(?:\\[0?m|\\[38;2(?:;\\d{1,3}){3}m|\\[([0-9]{1,2}[;m]?){3})");

    public static String aggressiveStrip(String text) {
        return aggressiveStripPattern.matcher(text).replaceAll("");
    }
}