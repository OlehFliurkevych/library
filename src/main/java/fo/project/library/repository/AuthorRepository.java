package fo.project.library.repository;

import fo.project.library.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    Author findAuthorByName(String name);

    List<Author> findAllByBornBefore(LocalDate localDate);

}
