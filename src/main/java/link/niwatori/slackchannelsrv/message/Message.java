package link.niwatori.slackchannelsrv.message;

import com.slack.api.model.block.LayoutBlock;
import link.niwatori.slackchannelsrv.ConfigKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class Message {

    FileConfiguration config;

    public Message(FileConfiguration config) {
        this.config = config;
    }

    public String getIconUrl() {
        return this.config.getString(ConfigKey.SLACK_BOT_ICON_URL.getKey(), "");
    }

    public String getIconEmoji() {
        return this.config.getString(ConfigKey.SLACK_BOT_ICON_EMOJI.getKey(), "");
    }

    public String getUsername() {
        return this.config.getString(ConfigKey.SLACK_BOT_NAME.getKey(), "bucket");
    }

    public String getText() { return ""; }

    abstract public List<LayoutBlock> getBlocks();

    public static String getPlayerIconUrl(Player player) {
        return "https://mc-heads.net/avatar/" + player.getPlayerProfile().getUniqueId();
    }
}
