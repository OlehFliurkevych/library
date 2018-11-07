package fo.project.library.repository;

import fo.project.library.entity.Author_Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorBookRepository extends JpaRepository<Author_Book, Long> {

    Author_Book findByAuthorIdAndBookId(long authorId, long bookId);

    List<Author_Book> findAllByAuthorId(long id);

    List<Author_Book> findAllByBookId(long id);
}
