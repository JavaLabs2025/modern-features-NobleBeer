package org.lab.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.lab.user.model.UserEntity;
import org.lab.user.persistence.repository.UserRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void save(UserEntity user) {
        userRepository.save(user);
        log.info("Создан пользователь с именем {}", user.getUsername());
    }
}
