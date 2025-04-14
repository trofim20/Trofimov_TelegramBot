package ru.trofimov.Trofimov_TelegramBot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.trofimov.Trofimov_TelegramBot.service.UserService;

/**
 * Контроллер для обработки регистрации новых пользователей
 */
@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(
            @RequestParam String username,
            @RequestParam String password,
            Model model) {

        try {
            userService.registerUser(username, password);
            return "redirect:/login?registered=true";
        } catch (Exception e) {
            model.addAttribute("message", "Username already exists");
            return "registration";
        }
    }
}
