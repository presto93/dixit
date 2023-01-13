package com.yoon.dixit.user.service;

import com.yoon.dixit.user.dto.SignUpDto;
import com.yoon.dixit.user.entity.UserEntity;
import com.yoon.dixit.user.enums.Role;
import com.yoon.dixit.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserValidationService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findByUsername(username);

        if (Objects.isNull(userEntity)) {
            return null;
        }

        return User.builder()
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .roles(userEntity.getRole().name())
                .build();
    }

    @Transactional
    public void saveUser(SignUpDto signupDto) {
        userRepository.save(UserEntity.builder()
                .username(signupDto.getUsername())
                .password(passwordEncoder.encode(signupDto.getPassword()))
                .role(Role.PLAYER)
                .build());
    }
}
