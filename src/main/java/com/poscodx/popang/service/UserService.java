package com.poscodx.popang.service;

import com.poscodx.popang.domain.User;
import com.poscodx.popang.domain.dto.UserDTO;
import com.poscodx.popang.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder ENCODER;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        UserDTO userDTO = new UserDTO();
        User user = userRepository.findUserById(id);

        if(user != null){
            userDTO.setByUserEntity(user);
        }

        return userDTO;
    }

    public Page<UserDTO> findAllByPage(Pageable pageable) {
        return userRepository.findAllUserByOrderBySignupDateDesc(pageable)
                .map(user -> {
                    UserDTO userDTO = new UserDTO();
                    userDTO.setByUserEntity(user);
                    return userDTO;
                });
    }

    @Transactional
    public void changeGrade(String id, Long grade){
        User user = userRepository.findUserById(id);
        user.setGrade(grade);
        userRepository.save(user);
    }

    @Transactional
    public void resetPassword(String id){
        User user = userRepository.findUserById(id);
        user.setPassword(ENCODER.encode("1234"));
        userRepository.save(user);
    }

    @Transactional
    public void deleteUserById(String id){
        userRepository.deleteById(id);
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
        LocalDateTime nowDateTime = LocalDateTime.now();
        Timestamp nowTimeStamp = Timestamp.valueOf(nowDateTime);

        user.setId(id);
        user.setGrade(grade);
        user.setPassword(ENCODER.encode(pw));
        user.setTel(tel);
        user.setRole(role);
        user.setName(name);
        user.setSignupDate(nowTimeStamp);
        userRepository.save(user);
    }
}
