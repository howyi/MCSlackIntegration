package link.niwatori.slackchannelsrv.message;

import com.slack.api.model.block.Blocks;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.composition.MarkdownTextObject;
import com.slack.api.model.block.element.ImageElement;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public class Info extends Message {

    private final String message;

    public Info(
            FileConfiguration config,
            String message
    ) {
        super(config);
        this.message = message;
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
                                MarkdownTextObject
                                        .builder()
                                        .text(this.getText())
                                        .build()
                        ))
                )
        );
    }
}
