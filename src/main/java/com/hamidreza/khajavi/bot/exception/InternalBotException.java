package com.hamidreza.khajavi.bot.exception;

/**
 * @author Hamidreza Khajavi
 * @since 6/30/2024
 */
public class InternalBotException extends Exception {

    public InternalBotException() {
    }

    public InternalBotException(String message) {
        super(message);
    }

    public InternalBotException(String message, Throwable cause) {
        super(message, cause);
    }

    public InternalBotException(Throwable cause) {
        super(cause);
    }
}
