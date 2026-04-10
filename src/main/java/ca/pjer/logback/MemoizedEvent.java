package ca.pjer.logback;

import jdk.internal.RequiresIdentity;
import software.amazon.awssdk.services.cloudwatchlogs.model.InputLogEvent;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class MemoizedEvent {
    // See http://docs.aws.amazon.com/AmazonCloudWatchLogs/latest/APIReference/API_PutLogEvents.html
    private static final int EVENT_SIZE_PADDING = 26;
    private static final Charset EVENT_SIZE_CHARSET = StandardCharsets.UTF_8;

    private final InputLogEvent event;
    private byte[] messageBytes;

    public MemoizedEvent(InputLogEvent event) {
        this.event = event;
    }

    public InputLogEvent event() {
        return event;
    }

    public byte[] getBytes() {
        if (messageBytes == null) {
            messageBytes = event.message().getBytes(EVENT_SIZE_CHARSET);
        }
        return messageBytes;
    }

    public int eventSize() {
        return getBytes().length + EVENT_SIZE_PADDING;
    }
}
