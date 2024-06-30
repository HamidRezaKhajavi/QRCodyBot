package com.hamidreza.khajavi.bot.service;

import com.hamidreza.khajavi.bot.exception.InternalBotException;
import com.hamidreza.khajavi.bot.handler.RequestHandler;
import com.hamidreza.khajavi.bot.handler.factory.RequestHandlerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;

//@Component
public class QRCodeBot extends TelegramLongPollingBot {

    private static final Logger logger = LoggerFactory.getLogger(QRCodeBot.class);

    private final String token;
    private final String username;

    public QRCodeBot(DefaultBotOptions botOptions
            , @Value("${bot.token}") String token
            , @Value("${bot.username}") String username) {
        super(botOptions);
        this.token = token;
        this.username = username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public void onUpdateReceived(Update update) {
        RequestHandler handler = RequestHandlerFactory.getHandler(this, update);
        try {
            execute(handler.handle());
            logger.info("Sent message \"{}\" to {}", update.getMessage(), update.getMessage().getChatId());
        } catch (InternalBotException | TelegramApiException | NoSuchMethodException e) {
            logger.error(e.getMessage(), e);
            handleTelegramApiError(update);
        }
    }

    @PostConstruct
    public void start() {
        logger.info("username: {}, token: {}", username, token);
    }

    private void handleTelegramApiError(Update update) {
        try {
            execute(new SendMessage(update.getMessage().getChatId().toString(), "Sorry an Internal Error occurred , please try again"));
        } catch (TelegramApiException e) {
            logger.error("Failed to send message \"{}\" to {} due to error: {}", update.getMessage(), update.getMessage().getChatId(), e.getMessage());
        }
    }
}
