package org.palermo.quiz.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.palermo.quiz.domain.Question;
import org.palermo.quiz.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@PropertySource("classpath:question-config.yml")
@Component
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Value( "${storage}" )
    private String storage;


    public void update() {
        log.info("Storage: " + storage);
        update(new File(storage));
    }

    private void update(File folder) {
        if (!folder.exists()) {
            throw new RuntimeException("Folder does not exist " + folder.getAbsolutePath());
        }

        if (!folder.isDirectory()) {
            throw new RuntimeException("Provided argument is not a directory " + folder.getAbsolutePath());
        }

        for (String filename : folder.list()) {
            File file = new File(folder, filename);

            if (file.isDirectory()) {
                update(file);
            }
            else {
                if (file.getName().endsWith(".json")) {
                    String fullPath = adjustFilename(file.getAbsolutePath());
                    if (!questionRepository.exists(fullPath)) {
                        questionRepository.create(Question.builder()
                                .fullPath(fullPath)
                                .creation(LocalDateTime.now())
                                .build());
                    }
                }
            }
        }
    }

    private String adjustFilename(String filename) {
        return filename.replace(storage, "")
                .replaceAll("\\\\", "/");
    }
}
