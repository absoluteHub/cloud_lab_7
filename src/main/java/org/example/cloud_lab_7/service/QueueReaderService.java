package org.example.cloud_lab_7.service;

import org.example.cloud_lab_7.bot.AgentBot;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class QueueReaderService {

    private final RabbitTemplate rabbitTemplate;
    private final AgentBot agentBot;

    public QueueReaderService(RabbitTemplate rabbitTemplate, AgentBot agentBot) {
        this.rabbitTemplate = rabbitTemplate;
        this.agentBot = agentBot;
    }

    @Scheduled(cron = "0 * * * * ?")
    public void readMessagesFromQueue() {
        boolean hasMessages = true;

        while (hasMessages) {
            Message message = rabbitTemplate.receive("supportQueue");

            if (message != null) {
                String payload = new String(message.getBody());
                String[] parts = payload.split("\\|", 2);

                if (parts.length == 2) {
                    String username = parts[0];
                    String ticketText = parts[1];
                    String notification = "Користувач @" + username + " залишив запит: " + ticketText;

                    agentBot.sendToAdmin(notification);
                }
            } else {
                hasMessages = false;
            }
        }
    }
}