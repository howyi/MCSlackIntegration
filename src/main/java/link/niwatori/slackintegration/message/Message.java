package link.niwatori.slackintegration.message;

import com.slack.api.model.Attachment;
import com.slack.api.model.block.LayoutBlock;
import link.niwatori.slackintegration.Config;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public abstract class Message {

    Config config;

    public Message(Config config) {
        this.config = config;
    }

    public String getIconUrl() {
        return this.config.slackBotIconUrl();
    }

    public String getIconEmoji() { return this.config.slackBotIconEmoji(); }

    public String getUsername() {
        return this.config.slackBotName();
    }

    public String getText() { return ""; }

    public List<LayoutBlock> getBlocks() {
        return new ArrayList<>();
    }

    public List<Attachment> getAttachments() {
        return new ArrayList<>();
    };

    public static String getPlayerIconUrl(Player player) {
        return "https://mc-heads.net/avatar/" + player.getPlayerProfile().getUniqueId();
    }
}
