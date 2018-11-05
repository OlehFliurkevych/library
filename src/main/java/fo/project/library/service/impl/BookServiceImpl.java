package fo.project.library.service.impl;

import fo.project.library.dto.BookDTO;
import fo.project.library.dto.RestMessageDTO;
import fo.project.library.entity.Book;
import fo.project.library.repository.BookRepository;
import fo.project.library.service.BookService;
import fo.project.library.utils.ObjectModelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ObjectModelUtils modelMapper;

    @Override
    public RestMessageDTO createNewBook(BookDTO bookDTO) {
        if (isInncorrectFielInputedsExist(bookDTO)) {
            return RestMessageDTO.createFailureMessage("Failed to create book,please check fields");
        }
        Book book = bookRepository.findBookByName(bookDTO.getName());
        if (book != null) {
            return RestMessageDTO.createFailureMessage("Book already exist");
        }
        book = BookDTO.toBook(bookDTO);
        bookRepository.save(book);
        return RestMessageDTO.createCorrectMessage("Successfully created new book");
    }

    @Override
    public RestMessageDTO<BookDTO> getBookById(long id) {
        Book book = (Book) modelMapper.checkEntity(bookRepository, id);
        BookDTO bookDTO = modelMapper.map(book, BookDTO.class);
        return new RestMessageDTO(bookDTO, true);
    }

    @Override
    public RestMessageDTO deleteBookById(long id) {
        Book book = (Book) modelMapper.checkEntity(bookRepository, id);
        bookRepository.delete(book);
        return RestMessageDTO.createCorrectMessage("Successfully deleted book");
    }

    @Override
    public RestMessageDTO updateBook(BookDTO bookDTO) {
        if (isInncorrectFielInputedsExist(bookDTO)) {
            return RestMessageDTO.createFailureMessage("Failed to update book,please check fields");
        }
        Book book = bookRepository.findBookByName(bookDTO.getName());
        if (book != null) {
            return RestMessageDTO.createFailureMessage("Fail to update book, already exist book");
        }
        book = (Book) modelMapper.checkEntity(bookRepository, bookDTO.getId());
        book.setName(bookDTO.getName());
        book.setGenre(bookDTO.getGenre());
        book.setPublished(bookDTO.getPublished());
        book.setRating(bookDTO.getRating());
        bookRepository.save(book);
        return RestMessageDTO.createCorrectMessage("Successfully updated book");
    }

    private static boolean isInncorrectFielInputedsExist(BookDTO bookDTO) {
        return (bookDTO.getRating() < 0 || bookDTO.getRating() > 10 || bookDTO.getPublished().isAfter(LocalDate.now()));
    }
}
