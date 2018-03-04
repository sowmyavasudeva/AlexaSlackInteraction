package slack;

import in.ashwanthkumar.slack.webhook.Slack;
import in.ashwanthkumar.slack.webhook.SlackMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * Handle sending message to slack.
 */
public class SlackWebhook {

    private final String webhookUrl;

    public SlackWebhook(String url) {
        this.webhookUrl = url;
    }

    public void sendMessageToSlack(String message) {
        try {
            new Slack(this.webhookUrl).sendToChannel("project")
                    .displayName("slack-java-client")
                    .push(new SlackMessage(message));
        } catch (final IOException e) {
            System.out.println("Unable to send message to slack");
        }
    }

    /**
     * For testing locally
     * @param args
     */
    public static void main(String[] args) {
        SlackWebhook slackWebhook = new SlackWebhook("https://hooks.slack.com/services/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");
        slackWebhook.sendMessageToSlack("hello");
    }
}
