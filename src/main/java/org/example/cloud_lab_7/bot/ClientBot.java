package org.example.cloud_lab_7.bot;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class ClientBot extends TelegramLongPollingBot {

    private final RabbitTemplate rabbitTemplate;
    private final String botUsername;
    private final String botToken;

    public ClientBot(RabbitTemplate rabbitTemplate,
                     @Value("${bot.client.username}") String botUsername,
                     @Value("${bot.client.token}") String botToken) {
        this.rabbitTemplate = rabbitTemplate;
        this.botUsername = botUsername;
        this.botToken = botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();
            String username = update.getMessage().getFrom().getUserName();

            if (username == null) {
                username = "Anonymous";
            }

            if (messageText.startsWith("/ticket ")) {
                String ticketText = messageText.replace("/ticket ", "");
                String payload = username + "|" + ticketText;

                rabbitTemplate.convertAndSend("supportExchange", "supportRoutingKey", payload);
                sendMessage(chatId, "Ваш запит успішно відправлено в чергу.");
            }
        }
    }

    private void sendMessage(Long chatId, String text) {
        SendMessage message = new SendMessage(chatId.toString(), text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() { return botUsername; }

    @Override
    public String getBotToken() { return botToken; }
}