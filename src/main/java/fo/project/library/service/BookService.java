package fo.project.library.service;

import fo.project.library.dto.BookDTO;
import fo.project.library.dto.RestMessageDTO;
import org.springframework.stereotype.Service;

@Service
public interface BookService {

    RestMessageDTO createNewBook(BookDTO bookDTO);

    RestMessageDTO<BookDTO> getBookById(long id);

    RestMessageDTO deleteBookById(long id);

    RestMessageDTO updateBook(BookDTO bookDTO);
}
