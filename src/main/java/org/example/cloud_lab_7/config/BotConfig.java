package org.example.cloud_lab_7.config;

import org.example.cloud_lab_7.bot.AgentBot;
import org.example.cloud_lab_7.bot.ClientBot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class BotConfig {

    @Bean
    public TelegramBotsApi telegramBotsApi(ClientBot clientBot, AgentBot agentBot) throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(clientBot);
        botsApi.registerBot(agentBot);
        return botsApi;
    }
}