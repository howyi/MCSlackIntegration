package link.niwatori.slackintegration;


import com.slack.api.model.Attachment;
import com.slack.api.model.Message;
import com.slack.api.model.block.LayoutBlock;
import java.util.List;
import java.util.Objects;

import com.slack.api.model.event.Event;

// copy from com.slack.api.model.event.MessageBotEvent
public class MessageSlackbotResponseEvent implements Event {
    public static final String TYPE_NAME = "message";
    public static final String SUBTYPE_NAME = "slackbot_response";
    private final String type = "message";
    private final String subtype = "slackbot_response";
    private String botId;
    private String username;
    private Message.Icons icons;
    private String channel;
    private String text;
    private List<LayoutBlock> blocks;
    private List<Attachment> attachments;
    private String threadTs;
    private String ts;
    private String eventTs;
    private String channelType;

    public MessageSlackbotResponseEvent() {
    }

    public String getType() {
        Objects.requireNonNull(this);
        return "message";
    }

    public String getSubtype() {
        Objects.requireNonNull(this);
        return "slackbot_response";
    }

    public String getBotId() {
        return this.botId;
    }

    public String getUsername() {
        return this.username;
    }

    public Message.Icons getIcons() {
        return this.icons;
    }

    public String getChannel() {
        return this.channel;
    }

    public String getText() {
        return this.text;
    }

    public List<LayoutBlock> getBlocks() {
        return this.blocks;
    }

    public List<Attachment> getAttachments() {
        return this.attachments;
    }

    public String getThreadTs() {
        return this.threadTs;
    }

    public String getTs() {
        return this.ts;
    }

    public String getEventTs() {
        return this.eventTs;
    }

    public String getChannelType() {
        return this.channelType;
    }

    public void setBotId(String botId) {
        this.botId = botId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setIcons(Message.Icons icons) {
        this.icons = icons;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setBlocks(List<LayoutBlock> blocks) {
        this.blocks = blocks;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public void setThreadTs(String threadTs) {
        this.threadTs = threadTs;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public void setEventTs(String eventTs) {
        this.eventTs = eventTs;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof MessageSlackbotResponseEvent)) {
            return false;
        } else {
            MessageSlackbotResponseEvent other = (MessageSlackbotResponseEvent)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label167: {
                    Object this$type = this.getType();
                    Object other$type = other.getType();
                    if (this$type == null) {
                        if (other$type == null) {
                            break label167;
                        }
                    } else if (this$type.equals(other$type)) {
                        break label167;
                    }

                    return false;
                }

                Object this$subtype = this.getSubtype();
                Object other$subtype = other.getSubtype();
                if (this$subtype == null) {
                    if (other$subtype != null) {
                        return false;
                    }
                } else if (!this$subtype.equals(other$subtype)) {
                    return false;
                }

                label153: {
                    Object this$botId = this.getBotId();
                    Object other$botId = other.getBotId();
                    if (this$botId == null) {
                        if (other$botId == null) {
                            break label153;
                        }
                    } else if (this$botId.equals(other$botId)) {
                        break label153;
                    }

                    return false;
                }

                Object this$username = this.getUsername();
                Object other$username = other.getUsername();
                if (this$username == null) {
                    if (other$username != null) {
                        return false;
                    }
                } else if (!this$username.equals(other$username)) {
                    return false;
                }

                label139: {
                    Object this$icons = this.getIcons();
                    Object other$icons = other.getIcons();
                    if (this$icons == null) {
                        if (other$icons == null) {
                            break label139;
                        }
                    } else if (this$icons.equals(other$icons)) {
                        break label139;
                    }

                    return false;
                }

                Object this$channel = this.getChannel();
                Object other$channel = other.getChannel();
                if (this$channel == null) {
                    if (other$channel != null) {
                        return false;
                    }
                } else if (!this$channel.equals(other$channel)) {
                    return false;
                }

                label125: {
                    Object this$text = this.getText();
                    Object other$text = other.getText();
                    if (this$text == null) {
                        if (other$text == null) {
                            break label125;
                        }
                    } else if (this$text.equals(other$text)) {
                        break label125;
                    }

                    return false;
                }

                label118: {
                    Object this$blocks = this.getBlocks();
                    Object other$blocks = other.getBlocks();
                    if (this$blocks == null) {
                        if (other$blocks == null) {
                            break label118;
                        }
                    } else if (this$blocks.equals(other$blocks)) {
                        break label118;
                    }

                    return false;
                }

                Object this$attachments = this.getAttachments();
                Object other$attachments = other.getAttachments();
                if (this$attachments == null) {
                    if (other$attachments != null) {
                        return false;
                    }
                } else if (!this$attachments.equals(other$attachments)) {
                    return false;
                }

                label104: {
                    Object this$threadTs = this.getThreadTs();
                    Object other$threadTs = other.getThreadTs();
                    if (this$threadTs == null) {
                        if (other$threadTs == null) {
                            break label104;
                        }
                    } else if (this$threadTs.equals(other$threadTs)) {
                        break label104;
                    }

                    return false;
                }

                label97: {
                    Object this$ts = this.getTs();
                    Object other$ts = other.getTs();
                    if (this$ts == null) {
                        if (other$ts == null) {
                            break label97;
                        }
                    } else if (this$ts.equals(other$ts)) {
                        break label97;
                    }

                    return false;
                }

                Object this$eventTs = this.getEventTs();
                Object other$eventTs = other.getEventTs();
                if (this$eventTs == null) {
                    if (other$eventTs != null) {
                        return false;
                    }
                } else if (!this$eventTs.equals(other$eventTs)) {
                    return false;
                }

                Object this$channelType = this.getChannelType();
                Object other$channelType = other.getChannelType();
                if (this$channelType == null) {
                    if (other$channelType != null) {
                        return false;
                    }
                } else if (!this$channelType.equals(other$channelType)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof MessageSlackbotResponseEvent;
    }

    public int hashCode() {
        int PRIME = 1;
        int result = 1;
        Object $type = this.getType();
        result = result * 59 + ($type == null ? 43 : $type.hashCode());
        Object $subtype = this.getSubtype();
        result = result * 59 + ($subtype == null ? 43 : $subtype.hashCode());
        Object $botId = this.getBotId();
        result = result * 59 + ($botId == null ? 43 : $botId.hashCode());
        Object $username = this.getUsername();
        result = result * 59 + ($username == null ? 43 : $username.hashCode());
        Object $icons = this.getIcons();
        result = result * 59 + ($icons == null ? 43 : $icons.hashCode());
        Object $channel = this.getChannel();
        result = result * 59 + ($channel == null ? 43 : $channel.hashCode());
        Object $text = this.getText();
        result = result * 59 + ($text == null ? 43 : $text.hashCode());
        Object $blocks = this.getBlocks();
        result = result * 59 + ($blocks == null ? 43 : $blocks.hashCode());
        Object $attachments = this.getAttachments();
        result = result * 59 + ($attachments == null ? 43 : $attachments.hashCode());
        Object $threadTs = this.getThreadTs();
        result = result * 59 + ($threadTs == null ? 43 : $threadTs.hashCode());
        Object $ts = this.getTs();
        result = result * 59 + ($ts == null ? 43 : $ts.hashCode());
        Object $eventTs = this.getEventTs();
        result = result * 59 + ($eventTs == null ? 43 : $eventTs.hashCode());
        Object $channelType = this.getChannelType();
        result = result * 59 + ($channelType == null ? 43 : $channelType.hashCode());
        return result;
    }

    public String toString() {
        return "MessageSlackbotResponseEvent(type=" + this.getType() + ", subtype=" + this.getSubtype() + ", botId=" + this.getBotId() + ", username=" + this.getUsername() + ", icons=" + this.getIcons() + ", channel=" + this.getChannel() + ", text=" + this.getText() + ", blocks=" + this.getBlocks() + ", attachments=" + this.getAttachments() + ", threadTs=" + this.getThreadTs() + ", ts=" + this.getTs() + ", eventTs=" + this.getEventTs() + ", channelType=" + this.getChannelType() + ")";
    }
}
