package br.com.estoque.producer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PacienteService {

    private static final Logger logger = LoggerFactory.getLogger(PacienteService.class);

    @Value("${topic.paciente-uclinic}")
    private String topicPaciente;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String combinedMessage) {
        logger.info("Mensagem -> {}", combinedMessage);

        // Enviar a mensagem para o Kafka
        this.kafkaTemplate.send(topicPaciente, combinedMessage);
    }
}
