package com.hamidreza.khajavi.bot.handler.factory;

import com.hamidreza.khajavi.bot.handler.HelpCommandHandler;
import com.hamidreza.khajavi.bot.handler.QRCodeHandler;
import com.hamidreza.khajavi.bot.handler.RequestHandler;
import com.hamidreza.khajavi.bot.handler.StartCommandHandler;
import com.hamidreza.khajavi.bot.service.QRCodeBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * @author Hamidreza Khajavi
 * @since 6/30/2024
 */
public final class RequestHandlerFactory {

    private RequestHandlerFactory() {
    }

    public static RequestHandler getHandler(QRCodeBot qrCodeBot, Update update) {
        Message message = update.getMessage();
        RequestHandler requestHandler = null;
        if (message != null && message.hasText()) {
            requestHandler = switch (message.getText()) {
                case "/start" -> new StartCommandHandler(qrCodeBot, update);
                case "/help" -> new HelpCommandHandler(qrCodeBot, update);
                default -> new QRCodeHandler(qrCodeBot, update);
            };
        }
        return requestHandler;
    }
}
