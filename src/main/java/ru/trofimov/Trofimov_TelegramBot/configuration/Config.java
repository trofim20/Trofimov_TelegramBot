package ru.trofimov.Trofimov_TelegramBot.configuration;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import ru.trofimov.Trofimov_TelegramBot.commandProcessor.CommandProcessor;
import ru.trofimov.Trofimov_TelegramBot.entity.OrderEntity;

import java.util.*;

/**
 * Конфигурация приложения
 */
@Configuration
public class Config {
    @Value("${app.name}")
    private String appName;

    @Value("${app.version}")
    private String appVersion;

    @Autowired
    private CommandProcessor commandProcessor;

    @Bean
    @Scope("singleton")
    public Map<Long, OrderEntity> orderContainer() {
        return new HashMap<>();
    }

    @PostConstruct
    public void appInfo() {
        String info = "Приложение: " + appName + ", Версия: " + appVersion;
        System.out.println(info);
    }

    @Bean
    public CommandLineRunner commandScanner() {
        return args -> {
            try (Scanner scanner = new Scanner(System.in)) {
                System.out.println("Доступны следующие команды:\n" +
                        "   сreate [id] [id пользователя] [заказ] [описание] [статус]\n" +
                        "   find [id]\n" +
                        "   delete [id]\n" +
                        "   update [id] [новый статус]\n" +
                        "   exit - Выход из программы...");
                while (true) {
                    System.out.println("> ");
                    String input = scanner.nextLine();
                    if ("exit".equalsIgnoreCase(input.trim())) {
                        System.out.println("Выход из программы...");
                        break;
                    }
                    commandProcessor.processCommand(input);
                }
            }
        };
    }
}