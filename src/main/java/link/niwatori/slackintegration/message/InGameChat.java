package link.niwatori.slackintegration.message;

import com.slack.api.model.block.Blocks;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.composition.BlockCompositions;
import link.niwatori.slackintegration.Config;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.text.MessageFormat;
import java.util.List;

public class InGameChat extends Message {
    private final AsyncPlayerChatEvent event;
    private final String message;

    public InGameChat(
            Config config,
            AsyncPlayerChatEvent event,
            String message
    ) {
        super(config);
        this.event = event;
        this.message = message;
    }

    @Override
    public String getIconUrl() {
        return Message.getPlayerIconUrl(this.event.getPlayer());
    }

    @Override
    public String getUsername() {
        String nameFormat = config.chatSyncMessagePlayerChatName();
        return MessageFormat.format(nameFormat, event.getPlayer().getDisplayName());
    }

    @Override
    public String getText() {
        return this.message;
    }
}