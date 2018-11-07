package fo.project.library.service;

import fo.project.library.dto.BookDTO;
import fo.project.library.dto.RestMessageDTO;
import fo.project.library.enumeration.GenreEnum;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {

    RestMessageDTO createNewBook(BookDTO bookDTO);

    RestMessageDTO<BookDTO> getBookById(long id);

    RestMessageDTO deleteBookById(long id);

    RestMessageDTO updateBook(BookDTO bookDTO);

    RestMessageDTO<List<BookDTO>> getBooksWithAuthorHaveMoreThanOneBook();

    RestMessageDTO getCountBooksByGenre(GenreEnum genre);
}
