package link.niwatori.slackchannelsrv.message;

import com.slack.api.model.block.Blocks;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.composition.BlockCompositions;
import link.niwatori.slackchannelsrv.ConfigKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.text.MessageFormat;
import java.util.List;

public class InGameChat extends Message {
    private final AsyncPlayerChatEvent event;

    public InGameChat(
            FileConfiguration config,
            AsyncPlayerChatEvent event
    ) {
        super(config);
        this.event = event;
    }

    @Override
    public String getIconUrl() {
        return Message.getPlayerIconUrl(this.event.getPlayer());
    }

    @Override
    public String getUsername() {
        String nameFormat = config.getString("Message.PlayerChatName", "{0}");
        return MessageFormat.format(nameFormat, event.getPlayer().getDisplayName());
    }

    @Override
    public List<LayoutBlock> getBlocks() {
        String messageFormat = config.getString(ConfigKey.MESSAGE_PLAYER_CHAT.getKey(), "{0}");
        return Blocks.asBlocks(
                Blocks.section(section -> section.text(BlockCompositions.plainText(
                        MessageFormat.format(messageFormat, event.getMessage())
                )))
        );
    }
}