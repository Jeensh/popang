package com.poscodx.popang.service;

import com.poscodx.popang.domain.User;
import com.poscodx.popang.domain.dto.UserDTO;
import com.poscodx.popang.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder ENCODER;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        UserDTO userDTO = null;
        User user = userRepository.findUserById(id);

        if(user != null){
            userDTO = new UserDTO();
            userDTO.setByUserEntity(user);
        }

        return userDTO;
    }

    @Transactional
    public void register(UserDTO userDTO){
        User user = new User();
        String id = userDTO.getId();
        String pw = userDTO.getPassword();
        String tel = userDTO.getTel();
        Long grade = userDTO.getGrade();
        Long role = userDTO.getRole() == null ? 1L : userDTO.getRole();
        String name = userDTO.getName();

        user.setId(id);
        user.setGrade(grade);
        user.setPassword(ENCODER.encode(pw));
        user.setTel(tel);
        user.setRole(role);
        user.setName(name);
        userRepository.save(user);
    }
}
