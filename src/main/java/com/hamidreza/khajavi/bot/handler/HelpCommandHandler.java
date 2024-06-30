package com.hamidreza.khajavi.bot.handler;

import com.hamidreza.khajavi.bot.service.QRCodeBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * @author Hamidreza Khajavi
 * @since 6/30/2024
 */
public class HelpCommandHandler extends RequestHandler {

    public HelpCommandHandler(QRCodeBot qrCodeBot, Update update) {
        super(qrCodeBot, update);
    }

    @Override
    @SuppressWarnings("unchecked")
    public BotApiMethod<Message> handle() {
        return new SendMessage(getChatId(), "help message");
    }
}
