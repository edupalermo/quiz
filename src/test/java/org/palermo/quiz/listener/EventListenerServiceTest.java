package org.palermo.quiz.listener;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.palermo.quiz.listener.model.Info;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class EventListenerServiceTest {

    @InjectMocks
    private EventListenerService underTest;

    @Test
    void testGetInfo() {
        /*
        Info info = underTest.parseInfo("room: 12 user: eduardo").orElseThrow(IllegalStateException::new);
        assertThat(info.getRoomIdentifier()).isEqualTo("12");
        assertThat(info.getUserEmail()).isEqualTo("eduardo");

         */
    }
}
