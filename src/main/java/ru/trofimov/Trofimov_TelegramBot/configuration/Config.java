package ru.trofimov.Trofimov_TelegramBot.configuration;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.trofimov.Trofimov_TelegramBot.commandProcessor.CommandProcessor;

import java.util.*;

/**
 * Конфигурация для работы с консолью
 */
@Configuration
public class Config {
    private final CommandProcessor commandProcessor;

    public Config(CommandProcessor commandProcessor) {
        this.commandProcessor = commandProcessor;
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