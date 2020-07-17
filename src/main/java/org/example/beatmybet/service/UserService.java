package org.example.beatmybet.service;

import org.example.beatmybet.dto.BalanceDTO;
import org.example.beatmybet.entity.User;
import org.example.beatmybet.exception.NotFoundException;
import org.example.beatmybet.repository.PostingRepository;
import org.example.beatmybet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired UserRepository userRepository;
    @Autowired PostingRepository postingRepository;

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("user", id));
    }

    public BalanceDTO getBalance(User user){
        BalanceDTO balance = BalanceDTO.builder().balance(
                postingRepository.getBalance(user)
        ).amountOfBids(null).build();
        return balance;
    }
}
