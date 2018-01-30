package alexa;

import com.amazon.speech.json.SpeechletRequestEnvelope;
import com.amazon.speech.speechlet.*;
import lombok.extern.slf4j.Slf4j;

/**
 * Handles Alexa Skill requests for finding sending messages to and receive messages from Slack.
 */
@Slf4j
public class SlackCommunicationSpeechlet implements SpeechletV2 {
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
}
