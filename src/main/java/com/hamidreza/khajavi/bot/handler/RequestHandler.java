package com.hamidreza.khajavi.bot.handler;

import com.hamidreza.khajavi.bot.exception.InternalBotException;
import com.hamidreza.khajavi.bot.service.QRCodeBot;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.Serializable;

/**
 * @author Hamidreza Khajavi
 * @since 6/30/2024
 */
public abstract class RequestHandler {

    private final QRCodeBot qrCodeBot;
    private final Update update;

    protected RequestHandler(QRCodeBot qrCodeBot, Update update) {
        this.qrCodeBot = qrCodeBot;
        this.update = update;
    }

    public abstract <T extends Serializable> PartialBotApiMethod<T> handle() throws InternalBotException, NoSuchMethodException;

    protected QRCodeBot getTimeLoggerBot() {
        return qrCodeBot;
    }

    protected Update getUpdate() {
        return update;
    }

    protected String getChatId() {
        return getUpdate().getMessage().getChatId().toString();
    }
}
