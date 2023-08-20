package link.niwatori.slackchannelsrv;

import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import com.slack.api.methods.request.chat.ChatPostMessageRequest.ChatPostMessageRequestBuilder;
import com.slack.api.methods.request.conversations.ConversationsSetTopicRequest;
import com.slack.api.methods.request.users.UsersInfoRequest;
import com.slack.api.methods.request.users.profile.UsersProfileSetRequest;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import com.slack.api.methods.response.conversations.ConversationsSetTopicResponse;
import com.slack.api.methods.response.reactions.ReactionsListResponse;
import com.slack.api.methods.response.users.UsersInfoResponse;
import com.slack.api.methods.response.users.profile.UsersProfileSetResponse;
import com.slack.api.model.User;
import link.niwatori.slackchannelsrv.message.Message;

import java.io.IOException;
import java.util.Objects;

import com.slack.api.Slack;

public class SlackSender {

    private final MethodsClient client;
    private final String channel;

    public SlackSender(String token, String channel) {
        Slack slack = Slack.getInstance();
        this.client = slack.methods(token);
        this.channel = channel;
    }

    public void sendMessage(Message message) {
        try {
            ChatPostMessageRequestBuilder requestBuilder = ChatPostMessageRequest.builder()
                    .channel(this.channel)
                    .username(message.getUsername())
                    .text(message.getText())
                    .blocks(message.getBlocks());
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

    public void setTopic(String topic) {
        try {
            ConversationsSetTopicRequest request = ConversationsSetTopicRequest.builder()
                    .channel(this.channel)
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
