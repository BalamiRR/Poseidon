package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final BidListRepository bidListRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void save(User user) {
        if(user != null){
             User newUser = new User();
             newUser.setUsername(user.getUsername());
             newUser.setPassword(encoder.encode(user.getPassword()));
             newUser.setFullName(user.getFullName());
             newUser.setRole("USER");
             userRepository.save(newUser);
        }

    }

    @Override
    public List<BidList> findAll() {
        return bidListRepository.findAll();
    }

}
