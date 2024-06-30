package com.hamidreza.khajavi.bot.handler;

import com.hamidreza.khajavi.bot.exception.InternalBotException;
import com.hamidreza.khajavi.bot.service.QRCodeBot;
import com.hamidreza.khajavi.bot.service.QRCodeGeneratorService;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.text.MessageFormat;

/**
 * @author Hamidreza Khajavi
 * @since 6/30/2024
 */

public class QRCodeHandler extends RequestHandler {

    private final QRCodeGeneratorService qrCodeGeneratorService;

    public QRCodeHandler(QRCodeBot qrCodeBot, Update update) {
        super(qrCodeBot, update);
        this.qrCodeGeneratorService = new QRCodeGeneratorService();
    }

    @Override
    @SuppressWarnings("unchecked")
    public PartialBotApiMethod<Message> handle() throws InternalBotException, NoSuchMethodException {
        Message message = getUpdate().getMessage();
        String text = message.getText();
        Long chatId = message.getChatId();
        return SendPhoto.builder()
                .chatId(String.valueOf(chatId))
                .caption(MessageFormat.format("QR-Code for: {0}", text))
                .photo(qrCodeGeneratorService.getQRCode(text))
                .build();
    }
}
