package alexa;

import com.amazon.speech.speechlet.SpeechletV2;
import com.amazon.speech.speechlet.lambda.SpeechletRequestStreamHandler;
import com.google.common.collect.Sets;

import java.util.Set;

/**
 * Handler for AWS Lambda function.
 */
public class SlackCommunicationRequestStreamHandler extends SpeechletRequestStreamHandler {

    private static final Set<String> APPLICATION_IDS = Sets.newHashSet("amzn1.ask.skill.745a4c6e-7a07-413e-82eb-3415d9877ca8");

    public SlackCommunicationRequestStreamHandler(SpeechletV2 speechlet) {
        super(speechlet, APPLICATION_IDS);
    }
}
