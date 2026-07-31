package com.gerardo.thrive.user.services;

import com.gerardo.thrive.config.CustomPasswordEncoder;
import com.gerardo.thrive.config.UserPrincipal;
import com.gerardo.thrive.user.dtos.request.UserRequestDto;
import com.gerardo.thrive.user.dtos.response.UserResponseDto;
import com.gerardo.thrive.user.entities.UserModel;
import com.gerardo.thrive.user.mappers.UserMapper;
import com.gerardo.thrive.user.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;
    private final @Lazy CustomPasswordEncoder customPasswordEncoder;
    private final UserMapper userMapper;

    public UserResponseDto registerUser(UserRequestDto request) {
        String hash = customPasswordEncoder.encode(request.password());
        UserModel newUser = userRepository.save(userMapper.toModel(request, hash));

        return userMapper.toResponse(newUser);
    }

    public UserResponseDto findUserById(Long id) {
        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return userMapper.toResponse(user);
    }

    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    @Override
    public @NonNull UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        UserModel user = userRepository.findByEmail(username);
        if (user == null) throw new UsernameNotFoundException(username);

        return new UserPrincipal(user);
    }
}
