package alexa;

import com.amazon.speech.speechlet.SpeechletV2;
import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;
import com.google.common.collect.Sets;

import java.util.Set;

/**
 * Handler for AWS Lambda function.
 */
public class SlackCommunicationRequestStreamHandler extends SpeechletRequestStreamHandler {

    private static final Set<String> APPLICATION_IDS = Sets.newHashSet("amzn1.ask.skill.df5e9b7c-4b71-4442-80d7-4460f2c281f1");

    public SlackCommunicationRequestStreamHandler() {
        super(new SlackCommunicationSpeechlet(), APPLICATION_IDS);
    }

    public SlackCommunicationRequestStreamHandler(SpeechletV2 speechlet, Set<String> supportedApplicationIds) {
        super(speechlet, APPLICATION_IDS);
    }
}
