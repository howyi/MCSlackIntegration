package link.niwatori.slackchannelsrv;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.socket_mode.SocketModeApp;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.model.block.Blocks;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.composition.MarkdownTextObject;
import com.slack.api.model.block.element.ImageElement;
import com.slack.api.model.event.*;
import link.niwatori.slackchannelsrv.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import com.slack.api.model.view.View;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.*;
import static com.slack.api.model.view.Views.*;
import com.slack.api.methods.response.views.ViewsPublishResponse;
import org.bukkit.entity.Player;

public class SlackEventListener {
    SlackSender sender;
    FileConfiguration config;
    SocketModeApp socketModeApp;

    public SlackEventListener(
            FileConfiguration config,
            SlackSender sender
        ) {
        this.config = config;
        this.sender = sender;
    }

    public void connect() {
        String appToken = config.getString(ConfigKey.SLACK_SOCKET_TOKEN.getKey(), "");

        String botToken = config.getString(ConfigKey.SLACK_TOKEN.getKey(), "");
        AppConfig appConfig = AppConfig.builder().singleTeamBotToken(botToken).build();
        App app = new App(appConfig);

        app.event(MessageEvent.class, (payload, ctx) -> {
            try {
                this.onMessage(payload.getEvent());
            } catch (SlackApiException|IOException e) {
                e.printStackTrace();
            }
            return ctx.ack();
        });

        app.event(AppHomeOpenedEvent.class, (payload, ctx) -> {
            try {
                View appHomeView = this.onHomeOpen(payload.getEvent());

                if (payload.getEvent().getView() == null) {
                    ViewsPublishResponse res = ctx.client().viewsPublish(r -> r
                            .userId(payload.getEvent().getUser())
                            .view(appHomeView)
                    );
                } else {
                    ViewsPublishResponse res = ctx.client().viewsPublish(r -> r
                            .userId(payload.getEvent().getUser())
                            .hash(payload.getEvent().getView().getHash()) // To protect against possible race conditions
                            .view(appHomeView)
                    );
                }
            } catch (SlackApiException|IOException e) {
                e.printStackTrace();
            }
            return ctx.ack();
        });

        app.event(MessageBotEvent.class, (payload, ctx) -> ctx.ack());
        app.event(MessageChangedEvent.class, (payload, ctx) -> ctx.ack());
        app.event(MessageThreadBroadcastEvent.class, (payload, ctx) -> ctx.ack());

        try {
            this.socketModeApp = new SocketModeApp(appToken, app);
            this.socketModeApp.startAsync();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void disconnect() throws Exception {
        this.socketModeApp.close();
    }

    public void onMessage(MessageEvent event) throws SlackApiException, IOException {
        if (Objects.equals(event.getText(), "")) {
            return;
        }
        if (!config.getString(ConfigKey.SLACK_CHANNEL_ID.getKey(), "").equals(event.getChannel())) {
            return;
        }
        if (config.getString(ConfigKey.MESSAGE_FROM_SLACK_CHAT.getKey(), "").equals("")) {
            return;
        }
        String name = this.sender.getUserName(event.getUser());
        if (Objects.equals(name, "")) {
            return;
        }
        String message = MessageFormat.format(
                config.getString(ConfigKey.MESSAGE_FROM_SLACK_CHAT.getKey(), ""),
                name,
                event.getText()
        );
        Bukkit.broadcastMessage(message);
    }

    public View onHomeOpen(AppHomeOpenedEvent event) throws SlackApiException, IOException {
        List<LayoutBlock> blocks = new ArrayList<>();

        String onlineUserCount = config.getString(ConfigKey.APP_HOME_ONLINE_USER_COUNT.getKey(), "");
        if (!onlineUserCount.equals("")) {
            String text = MessageFormat.format(onlineUserCount, Bukkit.getOnlinePlayers().size(), Bukkit.getServer().getMaxPlayers());
            blocks.add(section(section -> section.text(markdownText(mt -> mt.text(text)))));
        }

        boolean onlineUserList = config.getBoolean(ConfigKey.APP_HOME_ONLINE_USER_LIST.getKey(), true);
        if (onlineUserList) {
            blocks.add(divider());
            blocks.add(section(section -> section.text(markdownText(mt -> mt.text("users")))));
            for (Player player : Bukkit.getOnlinePlayers()) {
                blocks.add(
                        Blocks.context(context -> context
                                .elements(List.of(
                                        ImageElement
                                                .builder()
                                                .imageUrl(Message.getPlayerIconUrl(player))
                                                .altText(player.getDisplayName())
                                                .build(),
                                        MarkdownTextObject
                                                .builder()
                                                .text(player.getDisplayName())
                                                .build()
                                ))
                        )
                );
            }
        }

        return view(view -> view
                .type("home")
                .blocks(blocks)
        );
    }
}