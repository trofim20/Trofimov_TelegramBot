package ru.trofimov.Trofimov_TelegramBot.configuration;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.trofimov.Trofimov_TelegramBot.entity.OrderEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * Конфигурация приложения
 */
@Configuration
public class AppConfig {
    @Value("${app.name}")
    private String appName;

    @Value("${app.version}")
    private String appVersion;

    @Bean
    public Map<Long, OrderEntity> orderContainer() {
        return new HashMap<>();
    }

    @PostConstruct
    public void appInfo() {
        String info = "Приложение: " + appName + ", Версия: " + appVersion;
        System.out.println(info);
    }
}
