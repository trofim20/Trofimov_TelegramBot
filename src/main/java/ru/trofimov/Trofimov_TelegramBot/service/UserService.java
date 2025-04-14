package ru.trofimov.Trofimov_TelegramBot.service;

import jakarta.transaction.Transactional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.trofimov.Trofimov_TelegramBot.entity.Role;
import ru.trofimov.Trofimov_TelegramBot.entity.UserEntity;
import ru.trofimov.Trofimov_TelegramBot.repository.UserRepository;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Сервис для упаравления пользователем
 */
@Service
@Transactional
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity entity = userRepository.findByName(username);
        if (entity == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(entity.getName(), entity.getPassword(), extractRoles(entity));
    }

    private Collection<? extends GrantedAuthority> extractRoles(UserEntity entity) {
        return entity.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toSet());
    }

    public UserEntity registerUser(String username, String password) {
        if (userRepository.findByName(username) != null) {
            throw new UsernameNotFoundException("Username " + username + " already exists");
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setName(username);
        userEntity.setPassword(passwordEncoder.encode(password));
        userEntity.setRoles(Set.of(Role.USER));
        return userRepository.save(userEntity);
    }
}
