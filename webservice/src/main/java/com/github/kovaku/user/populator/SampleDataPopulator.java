package com.github.kovaku.user.populator;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.github.kovaku.user.persistence.UserRepository;
import com.github.kovaku.user.presentation.domain.User;

@Component
@ConditionalOnProperty(name = "service.generate.sample.data", havingValue = "true")
public class SampleDataPopulator implements CommandLineRunner {

    private final UserRepository userRepository;

    public SampleDataPopulator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {
        userRepository.save(generateTestUser("John Doe","john_doe@acme.com"));
        userRepository.save(generateTestUser("Jane Doe","jane_doe@acme.com"));
    }

    private User generateTestUser(String name, String email) {
        return User.builder()
            .withName(name)
            .withEmail(email)
            .build();
    }
}
