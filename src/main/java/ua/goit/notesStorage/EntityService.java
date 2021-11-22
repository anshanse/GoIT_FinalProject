package ua.goit.notesStorage;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Service;

@Service
@NoRepositoryBean
public interface EntityService<T extends BaseEntity<ID>, ID> extends EntityRepository<T, ID> {


}
