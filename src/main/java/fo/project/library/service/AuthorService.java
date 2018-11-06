package fo.project.library.service;

import fo.project.library.dto.AuthorDTO;
import fo.project.library.dto.ListBooksDTO;
import fo.project.library.dto.RestMessageDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface AuthorService {

    RestMessageDTO createNewAuthor(AuthorDTO authorDTO);

    RestMessageDTO<AuthorDTO> getAuthorById(long id);

    RestMessageDTO deleteAuthorById(long id);

    RestMessageDTO updateAuthor(AuthorDTO authorDTO);

    RestMessageDTO addBookForAuthor(long bookId, long authorId);

    RestMessageDTO deleteBookFromAuthor(long bookId, long authorId);

    RestMessageDTO addSetBooksForAuthor(ListBooksDTO listBooksDTO);

    RestMessageDTO deleteAllBooksFromAuthor(long authorId);
}
