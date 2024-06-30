package com.hamidreza.khajavi.bot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import java.text.MessageFormat;

@Component
public class QRCodeBot extends TelegramLongPollingBot {

    private static final Logger logger = LoggerFactory.getLogger(QRCodeBot.class);

    private final String token;
    private final String username;
    private final QRCodeGeneratorService qrCodeGeneratorService;

    public QRCodeBot(DefaultBotOptions botOptions
            , @Value("${bot.token}") String token
            , @Value("${bot.username}") String username
            , QRCodeGeneratorService qrCodeGeneratorService) {
        super(botOptions);
        this.token = token;
        this.username = username;
        this.qrCodeGeneratorService = qrCodeGeneratorService;
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
        if (update.hasMessage()) {
            Message message = update.getMessage();
            String text = message.getText();
            Long chatId = message.getChatId();
            SendPhoto response = SendPhoto.builder()
                    .chatId(String.valueOf(chatId))
                    .caption(MessageFormat.format("QR-Code for: {0}", text))
                    .photo(qrCodeGeneratorService.getQRCode(text))
                    .build();
            try {
                execute(response);
                logger.info("Sent message \"{}\" to {}", text, chatId);
            } catch (TelegramApiException e) {
                logger.error("Failed to send message \"{}\" to {} due to error: {}", text, chatId, e.getMessage());
            }
        }
    }

    @PostConstruct
    public void start() {
        logger.info("username: {}, token: {}", username, token);
    }
}
