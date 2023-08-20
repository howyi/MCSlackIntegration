package link.niwatori.slackchannelsrv;

import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import com.slack.api.bolt.socket_mode.SocketModeApp;
import com.slack.api.methods.SlackApiException;
import com.slack.api.model.event.MessageBotEvent;
import com.slack.api.model.event.MessageEvent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Objects;

public class SlackEventListener {
    SlackSender sender;
    FileConfiguration config;

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

        app.event(MessageEvent.class, (req, ctx) -> {
            try {
                this.onMessage(req.getEvent());
            } catch (SlackApiException|IOException e) {
                e.printStackTrace();
            }
            return ctx.ack();
        });

        app.event(MessageBotEvent.class, (payload, ctx) -> ctx.ack());

        SocketModeApp socketModeApp = null;
        try {
            socketModeApp = new SocketModeApp(appToken, app);
            socketModeApp.startAsync();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
        String message = MessageFormat.format(
                config.getString(ConfigKey.MESSAGE_FROM_SLACK_CHAT.getKey(), ""),
                name,
                event.getText()
        );
        Bukkit.broadcastMessage(message);
    }
}