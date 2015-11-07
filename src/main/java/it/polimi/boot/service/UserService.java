package it.polimi.boot.service;

import it.polimi.boot.domain.User;
import it.polimi.boot.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	public User findUserById(Long userId)
	{
		User user =userRepository.findByUserId(userId); 
		return user;
	}
}
