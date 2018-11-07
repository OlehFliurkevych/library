package fo.project.library.repository;

import fo.project.library.entity.Book;
import fo.project.library.enumeration.GenreEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Book findBookByName(String name);

    @Query("SELECT COUNT(b) FROM Book b WHERE b.genre=:genre")
    int findCountBooksByGenre(@Param("genre") GenreEnum genre);

}
