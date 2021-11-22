package ua.goit.notesStorage.authorization;

import ua.goit.notesStorage.EntityRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends EntityRepository<User, UUID> {

    @Query("SELECT u FROM #{#entityName} u WHERE u.username=?1")
    Optional<User> findByUsername(String username);



}
