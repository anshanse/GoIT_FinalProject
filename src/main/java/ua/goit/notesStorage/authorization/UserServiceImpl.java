package ua.goit.notesStorage.authorization;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public abstract class UserServiceImpl implements UserService{

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private final UserRepository userRepository;

    protected UserServiceImpl(EntityManager em, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        System.out.println("UserServiceImpl loadUserByUsername");

        this.em = em;
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        System.out.println("UserServiceImpl loadUserByUsername");

        return userRepository.findAll();
    }

    @Override
    public <S extends User> S save(S user) {
        System.out.println("UserServiceImpl loadUserByUsername");
        userRepository.save(user);
        return userRepository.save(user);
    }
    public Optional<User> findById(String id) {
        return userRepository.findById(UUID.fromString(id));
    }

    public void deleteById(String id) {
        userRepository.deleteById(UUID.fromString(id));
    }
}
