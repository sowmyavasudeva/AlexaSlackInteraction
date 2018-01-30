package alexa;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.speechlet.*;
import com.amazon.speech.ui.*;
import lombok.extern.slf4j.Slf4j;
import slack.SlackWebhook;

/**
 * Handles Alexa Skill requests for finding sending messages to and receive messages from Slack.
 */
@Slf4j
public class SlackCommunicationSpeechlet implements SpeechletV2 {

    private static final String WEBHOOK_URL = "https://hooks.slack.com/services/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";
    private static final String STAGE = "stage";
    private static final Integer CHANNEL_NAME_STAGE = 1;
    private static final Integer MESSAGE_NAME_STAGE = 2;


    public void onSessionStarted(SpeechletRequestEnvelope<SessionStartedRequest> speechletRequestEnvelope) {
        log.info("Session Started with session id = {}, request = id = {}", speechletRequestEnvelope.getSession().getSessionId(),
                speechletRequestEnvelope.getRequest().getRequestId());
    }

    public SpeechletResponse onLaunch(SpeechletRequestEnvelope<LaunchRequest> speechletRequestEnvelope) {
        log.info("Session launched with session id = {}, request = id = {}", speechletRequestEnvelope.getSession().getSessionId(),
                speechletRequestEnvelope.getRequest().getRequestId());
        return null;
    }

    public SpeechletResponse onIntent(SpeechletRequestEnvelope<IntentRequest> speechletRequestEnvelope) {
        return null;
    }

    public void onSessionEnded(SpeechletRequestEnvelope<SessionEndedRequest> speechletRequestEnvelope) {
        log.info("Session ended with session id = {}, request = id = {}", speechletRequestEnvelope.getSession().getSessionId(),
                speechletRequestEnvelope.getRequest().getRequestId());
    }

    private SpeechletResponse handleGetChannelIntent(final Session session) {
        String speechOutput = "What's the channel name?";
        String repromptMessage = "Can you please say the slack channel name to which I need to send a message?";

        session.setAttribute(STAGE, CHANNEL_NAME_STAGE);

        SimpleCard simpleCard = new SimpleCard();
        simpleCard.setTitle("Send message to Slack");
        simpleCard.setContent(speechOutput);

        SpeechletResponse response = this.newAskResponse(speechOutput, false, repromptMessage, false);
        return response;
    }

    private SpeechletResponse newAskResponse(String stringOutput, boolean isOutputSsml,
                                             String repromptText, boolean isRepromptSsml) {
        OutputSpeech outputSpeech, repromptOutputSpeech;
        if (isOutputSsml) {
            outputSpeech = new SsmlOutputSpeech();
            ((SsmlOutputSpeech) outputSpeech).setSsml(stringOutput);
        } else {
            outputSpeech = new PlainTextOutputSpeech();
            ((PlainTextOutputSpeech) outputSpeech).setText(stringOutput);
        }

        if (isRepromptSsml) {
            repromptOutputSpeech = new SsmlOutputSpeech();
            ((SsmlOutputSpeech) repromptOutputSpeech).setSsml(repromptText);
        } else {
            repromptOutputSpeech = new PlainTextOutputSpeech();
            ((PlainTextOutputSpeech) repromptOutputSpeech).setText(repromptText);
        }
        Reprompt reprompt = new Reprompt();
        reprompt.setOutputSpeech(repromptOutputSpeech);
        return SpeechletResponse.newAskResponse(outputSpeech, reprompt);
    }

    private SpeechletResponse handleGetMessageIntent(final Session session) {
        String speechOutput, repromptText;

        if (session.getAttributes().containsKey(STAGE)) {
            if ((Integer) session.getAttribute(STAGE) == CHANNEL_NAME_STAGE) {
                speechOutput = "Please say the message";
                repromptText = "You can say the message to send to the slack channel";
                session.setAttribute(STAGE, MESSAGE_NAME_STAGE);
            } else {
                session.setAttribute(STAGE, CHANNEL_NAME_STAGE);
                speechOutput = "I didn't get the channel name. Can you say it again?";
                repromptText = "You can say the channel name as channel Project";
            }
        } else {
            speechOutput = "Sorry, I couldn't correctly retrieve the channel. You can say, send a message to slack";
            repromptText = "You can say, send a message to slack";
        }

        SpeechletResponse response = newAskResponse("<speak>" + speechOutput + "</speak>", true, repromptText, false);
        return response;
    }

    private SpeechletResponse handleMessageSentIntent(final Session session) {

        SimpleCard simpleCard = new SimpleCard();
        simpleCard.setTitle("Send message to Slack");
        String speechOutput, repromptText;

        if (session.getAttributes().containsKey(STAGE)) {
            if ((Integer) session.getAttribute(STAGE) == MESSAGE_NAME_STAGE) {
                speechOutput = "Message sent.";
                simpleCard.setContent(speechOutput);

                // Create the ssml text output
                SsmlOutputSpeech outputSpeech = new SsmlOutputSpeech();
                outputSpeech.setSsml("<speak>" + speechOutput + "</speak>");
                SlackWebhook slackWebhook = new SlackWebhook(WEBHOOK_URL);
//                slackWebhook.sendMessageToSlack();
                return SpeechletResponse.newTellResponse(outputSpeech, simpleCard);
            } else {
                session.setAttribute(STAGE, CHANNEL_NAME_STAGE);
                speechOutput = "I didn't get the channel name. Can you say it again?";
                repromptText = "You can say the channel name as channel Project";

                simpleCard.setContent("I didn't get the channel name. Can you say the channel name again?");

                // Create the ssml text output
                SsmlOutputSpeech outputSpeech = new SsmlOutputSpeech();
                outputSpeech.setSsml("<speak>" + speechOutput + "</speak>");
                PlainTextOutputSpeech repromptOutputSpeech = new PlainTextOutputSpeech();
                repromptOutputSpeech.setText(repromptText);
                Reprompt repromptSpeech = new Reprompt();
                repromptSpeech.setOutputSpeech(repromptOutputSpeech);

                // If the joke has to be restarted, then keep the session alive
                return SpeechletResponse.newAskResponse(outputSpeech, repromptSpeech, simpleCard);
            }
        } else {
            speechOutput = "Sorry, I couldn't correctly retrieve the message. You can say, send a message to slack";
            repromptText = "You can say, send a message to slack";
            simpleCard.setContent(speechOutput);
            SpeechletResponse response = newAskResponse(speechOutput, false,
                    repromptText, false);
            response.setCard(simpleCard);
            return response;
        }
    }
}
