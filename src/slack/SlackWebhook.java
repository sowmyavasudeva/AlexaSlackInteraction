package slack;

import in.ashwanthkumar.slack.webhook.Slack;
import in.ashwanthkumar.slack.webhook.SlackMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * Handle sending message to slack.
 */
@RequiredArgsConstructor
@Slf4j
public class SlackWebhook {

    private final String webhookUrl;

    public void sendMessageToSlack(String message) {
        try {
            new Slack(this.webhookUrl).sendToUser("slackbot")
                    .displayName("slack-java-client")
                    .push(new SlackMessage("Text from my ").bold("Slack-Java-Client"));
        } catch (final IOException e) {
            log.error("Unable to send message to slack", e);
        }
    }
}
