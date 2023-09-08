package link.niwatori.slackintegration;

import com.slack.api.app_backend.slash_commands.response.SlashCommandResponse;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.socket_mode.SocketModeApp;
import com.slack.api.methods.SlackApiException;
import com.slack.api.model.block.Blocks;
import com.slack.api.model.block.LayoutBlock;
import com.slack.api.model.block.composition.MarkdownTextObject;
import com.slack.api.model.block.element.ImageElement;
import com.slack.api.model.event.*;
import link.niwatori.slackintegration.message.Message;
import org.bukkit.Bukkit;
import com.slack.api.model.view.View;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.*;
import static com.slack.api.model.view.Views.*;
import com.slack.api.methods.response.views.ViewsPublishResponse;
import org.bukkit.entity.Player;

public class SlackEventListener {
    SlackSender sender;
    Config config;
    SocketModeApp socketModeApp;

    public SlackEventListener(
            Config config,
            SlackSender sender
        ) {
        this.config = config;
        this.sender = sender;
    }

    public void connect(SlackIntegration plugin) {
        AppConfig appConfig = AppConfig.builder().singleTeamBotToken(this.config.slackToken()).build();
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
        app.event(MessageChannelJoinEvent.class, (payload, ctx) -> ctx.ack());
        app.event(MessageChannelLeaveEvent.class, (payload, ctx) -> ctx.ack());
        app.event(MessageFileShareEvent.class, (payload, ctx) -> ctx.ack());
        app.event(MessageSlackbotResponseEvent.class, (payload, ctx) -> ctx.ack());

        app.command("/mcserver", (req, ctx) -> {
            if (!this.config.consoleExecutable()) {
                return ctx.ack("mcserver command is not allowed");
            }
            String channelId = req.getPayload().getChannelId();
            if (!channelId.equals(this.config.consoleSlackChannelId())) {
                return ctx.ack("mcserver command is not allowed in this channel");
            }
            if (!this.config.consoleExecutableAllUser()) {
                String userName = req.getPayload().getUserName();
                if (!Arrays.asList(this.config.consoleExecutableSlackUserNames()).contains(userName)) {
                    return ctx.ack("mcserver command is not allowed user");
                }
            }

            String value = req.getPayload().getText();
            Bukkit.getServer().getScheduler().callSyncMethod(plugin, () -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), value));

            SlashCommandResponse.SlashCommandResponseBuilder builder =  SlashCommandResponse.builder();
            builder.responseType("in_channel");
            return ctx.ack(builder.build());
        });

        try {
            this.socketModeApp = new SocketModeApp(this.config.slackSocketToken(), app);
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
        if (!this.config.chatSyncMessageFromSlackChatEnabled()) {
            return;
        }
        if (!this.config.chatSyncSlackChannelId().equals(event.getChannel())) {
            return;
        }
        String name = this.sender.getUserName(event.getUser());
        if (Objects.equals(name, "")) {
            return;
        }
        String message = MessageFormat.format(
                this.config.chatSyncMessageFromSlackChat(),
                name,
                event.getText()
        );
        Bukkit.broadcastMessage(message);
    }

    public View onHomeOpen(AppHomeOpenedEvent event) throws SlackApiException, IOException {
        List<LayoutBlock> blocks = new ArrayList<>();

        if (this.config.appHomeOnlineUserCountEnabled()) {
            String text = MessageFormat.format(this.config.appHomeOnlineUserCount(), Bukkit.getOnlinePlayers().size(), Bukkit.getServer().getMaxPlayers());
            blocks.add(section(section -> section.text(markdownText(mt -> mt.text(text)))));
        }
        if (this.config.appHomeOnlineUserList()) {
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