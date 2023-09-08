package link.niwatori.slackintegration.message;

import com.slack.api.model.Attachment;
import com.slack.api.model.Attachments;
import org.apache.logging.log4j.Level;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class Log extends Message {

    private final String message;
    private final Level logLevel;

    public Log(
            FileConfiguration config,
            String message,
            Level logLevel
    ) {
        super(config);
        this.message = message;
        this.logLevel = logLevel;
    }

    @Override
    public List<Attachment> getAttachments() {
        return Attachments.asAttachments(
                Attachments.attachment(c -> c.color(levelToColor(this.logLevel)).text(this.message).fallback(this.message))
        );
    }

    static String levelToColor(Level level) {
        if (level.equals(Level.FATAL)) {
            return "#c71585";
        } else if (level.equals(Level.ERROR)) {
            return "#ff0000";
        } else if (level.equals(Level.WARN)) {
            return "#ff8c00";
        } else if (level.equals(Level.INFO)) {
            return "#00bfff";
        } else if (level.equals(Level.DEBUG)) {
            return "#a9a9a9";
        } else if (level.equals(Level.TRACE)) {
            return "#696969";
        }
        return "";
    }
}
