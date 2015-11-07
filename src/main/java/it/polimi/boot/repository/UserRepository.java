package it.polimi.boot.repository;



import it.polimi.boot.domain.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository    extends CrudRepository<User, Long> {
	
	public User findByUserId(Long userId);
	
	public User findByUsername(String username);
	
}
