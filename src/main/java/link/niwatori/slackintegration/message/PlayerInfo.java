package link.niwatori.slackintegration.message;

import com.slack.api.model.block.Blocks;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.composition.MarkdownTextObject;
import com.slack.api.model.block.element.ImageElement;
import link.niwatori.slackintegration.Config;
import org.bukkit.entity.Player;

import java.util.List;

public class PlayerInfo extends Message {
    private final Player player;
    private final String message;

    public PlayerInfo(
            Config config,
            Player player,
            String format
    ) {
        super(config);
        this.player = player;
        this.message = format;
    }

    @Override
    public String getText() {
        return this.message;
    }

    @Override
    public List<LayoutBlock> getBlocks() {
        return Blocks.asBlocks(
                Blocks.context(context -> context
                        .elements(List.of(
                                ImageElement
                                        .builder()
                                        .imageUrl(getPlayerIconUrl(player))
                                        .altText(player.getDisplayName())
                                        .build(),
                                MarkdownTextObject
                                        .builder()
                                        .text(this.getText())
                                        .build()
                        ))
                )
        );
    }
}
