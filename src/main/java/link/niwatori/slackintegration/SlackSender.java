package link.niwatori.slackintegration;

import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.request.chat.ChatPostMessageRequest.ChatPostMessageRequestBuilder;
import com.slack.api.methods.request.conversations.ConversationsSetTopicRequest;
import com.slack.api.methods.request.users.UsersInfoRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.methods.response.conversations.ConversationsSetTopicResponse;
import com.slack.api.methods.response.users.UsersInfoResponse;
import link.niwatori.slackintegration.message.Message;

import java.io.IOException;
import java.util.Objects;

import com.slack.api.Slack;

public class SlackSender {

    private final MethodsClient client;

    public SlackSender(String token) {
        Slack slack = Slack.getInstance();
        this.client = slack.methods(token);
    }

    public void sendMessage(Message message, String channel) {
        if (Objects.equals(channel, "")) {
            return;
        }
        try {
            ChatPostMessageRequestBuilder requestBuilder = ChatPostMessageRequest.builder()
                    .channel(channel)
                    .username(message.getUsername())
                    .text(message.getText())
                    .blocks(message.getBlocks())
                    .attachments(message.getAttachments());
            if (!Objects.equals(message.getIconUrl(), "")) {
                requestBuilder.iconUrl(message.getIconUrl());
            } else if (!Objects.equals(message.getIconEmoji(), "")) {
                requestBuilder.iconEmoji(message.getIconEmoji());
            }
            ChatPostMessageResponse response = this.client.chatPostMessage(requestBuilder.build());
        } catch (IOException | SlackApiException e) {
            e.printStackTrace();
        }
    }

    public void setTopic(String topic, String channel) {
        if (Objects.equals(channel, "")) {
            return;
        }
        try {
            ConversationsSetTopicRequest request = ConversationsSetTopicRequest.builder()
                    .channel(channel)
                    .topic(topic)
                    .build();
            ConversationsSetTopicResponse response = this.client.conversationsSetTopic(request);
        } catch (IOException | SlackApiException e) {
            e.printStackTrace();
        }
    }

    public String getUserName(String userId) throws SlackApiException, IOException {
        try {
            UsersInfoRequest request = UsersInfoRequest.builder()
                    .user(userId)
                    .build();
            UsersInfoResponse response = this.client.usersInfo(request);
            return response.getUser().getProfile().getDisplayNameNormalized();
        } catch (IOException | SlackApiException e) {
            throw e;
        }
    }
}
