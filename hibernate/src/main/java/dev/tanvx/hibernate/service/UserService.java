package dev.tanvx.hibernate.service;

import dev.tanvx.hibernate.model.User;
import dev.tanvx.hibernate.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  public User createUser(User user) {
    return userRepository.save(user);
  }

  public User getUserById(Long id) {
    return userRepository.findById(id).orElse(null);
  }

  public void deleteUser(Long id) {
    userRepository.deleteById(id);
  }
}
