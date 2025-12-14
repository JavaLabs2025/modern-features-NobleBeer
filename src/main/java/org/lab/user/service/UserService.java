package org.lab.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.itmo.hrtek.dlms.storage.postgres.PostgresStorageException;
import org.itmo.hrtek.dlms.storage.postgres.model.User;
import org.itmo.hrtek.dlms.storage.postgres.repository.UserRepository;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void save(User user) throws PostgresStorageException {
        userRepository.create(user);
        log.info("Создан пользователь с именем {}", user.getUsername());
    }
}
