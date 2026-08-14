package com.gerardo.thrive.testsupport;

import com.gerardo.thrive.auth.security.CustomPasswordEncoder;
import com.gerardo.thrive.common.enums.Role;
import com.gerardo.thrive.user.entities.UserModel;
import com.gerardo.thrive.user.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestUserFactory {
    private final UserRepository userRepository;
    private final CustomPasswordEncoder customPasswordEncoder;

    public UserModel createUserTest(String name, String email, String username, String password, Role role) {
        UserModel user = new UserModel();
        user.setName(name);
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword_hash(customPasswordEncoder.encode(password));
        user.setRole(role);
        return userRepository.save(user);
    }
}
