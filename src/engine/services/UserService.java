package engine.services;

import engine.descriptors.User;
import engine.jpa.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public void registerUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public User getUser(int id) {
        return userRepository.findById(id).orElseThrow();
    }

    public User getByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean isRegistered(int id) {
        return userRepository.existsById(id);
    }

    public boolean isRegisteredEmail(String email) {
        return getByEmail(email) != null;
    }
}
