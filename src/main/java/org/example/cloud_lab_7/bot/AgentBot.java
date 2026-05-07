package org.example.cloud_lab_7.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class AgentBot extends TelegramLongPollingBot {

    private final String botUsername;
    private final String botToken;
    private final Long adminChatId;

    public AgentBot(@Value("${bot.agent.username}") String botUsername,
                    @Value("${bot.agent.token}") String botToken,
                    @Value("${bot.agent.admin-chat-id}") Long adminChatId) {
        this.botUsername = botUsername;
        this.botToken = botToken;
        this.adminChatId = adminChatId;
    }

    @Override
    public void onUpdateReceived(Update update) {
    }

    public void sendToAdmin(String text) {
        SendMessage message = new SendMessage(adminChatId.toString(), text);
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