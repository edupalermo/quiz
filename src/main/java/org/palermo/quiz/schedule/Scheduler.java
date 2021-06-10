package org.palermo.quiz.schedule;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.palermo.quiz.service.QuestionService;
import org.palermo.quiz.service.RoomService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class Scheduler {

    private final RoomService roomService;
    private final QuestionService questionService;

    @Scheduled(fixedRateString = "60000", initialDelayString = "0")
    public void deleteIdleRooms() {
        roomService.deleteIdleRooms();
    }

    @Scheduled(fixedRateString = "60000", initialDelayString = "0")
    public void updatedQuestions() {
        questionService.update();
    }
}
