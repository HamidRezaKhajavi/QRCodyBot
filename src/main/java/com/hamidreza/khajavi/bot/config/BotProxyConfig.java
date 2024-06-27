package com.hamidreza.khajavi.bot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;

/**
 * @author Hamidreza Khajavi
 * @since 6/20/2024
 */
@Configuration
public class BotProxyConfig {

    private final String proxyHost;
    private final Integer proxyPort;
    private final DefaultBotOptions.ProxyType proxyType;

    public BotProxyConfig(@Value("${bot.proxy.host:127.0.0.1}") String proxyHost
            , @Value("${bot.proxy.port:10808}") Integer proxyPort
            , @Value("${bot.proxy.type:SOCKS5}") DefaultBotOptions.ProxyType proxyType) {
        this.proxyHost = proxyHost;
        this.proxyPort = proxyPort;
        this.proxyType = proxyType;
    }

    @Bean
    public DefaultBotOptions defaultBotOptions() {
        DefaultBotOptions botOptions = new DefaultBotOptions();
        botOptions.setProxyHost(proxyHost);
        botOptions.setProxyPort(proxyPort);
        botOptions.setProxyType(proxyType);
        return botOptions;
    }
}
