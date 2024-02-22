package kz.dar.university.task.api.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${spring.kafka.task.topic}")
    private String topicName;

    public void sendMessage(String msg) {
        kafkaTemplate.send(topicName, msg);
    }

}
