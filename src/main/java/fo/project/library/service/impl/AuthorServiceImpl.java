package fo.project.library.service.impl;

import fo.project.library.dto.AuthorDTO;
import fo.project.library.dto.ListBooksDTO;
import fo.project.library.dto.RestMessageDTO;
import fo.project.library.entity.Author;
import fo.project.library.entity.Author_Book;
import fo.project.library.entity.Book;
import fo.project.library.repository.AuthorBookRepository;
import fo.project.library.repository.AuthorRepository;
import fo.project.library.repository.BookRepository;
import fo.project.library.service.AuthorService;
import fo.project.library.utils.ObjectModelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ObjectModelUtils modelMapper;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorBookRepository authorBookRepository;

    @Override
    public RestMessageDTO createNewAuthor(AuthorDTO authorDTO) {
        Author author = authorRepository.findAuthorByName(authorDTO.getName());
        if (author != null) {
            return RestMessageDTO.createFailureMessage("Author already exist");
        }
        author = AuthorDTO.toAuthor(authorDTO);
        authorRepository.save(author);
        return RestMessageDTO.createCorrectMessage("Successfully created new author");
    }

    @Override
    public RestMessageDTO<AuthorDTO> getAuthorById(long id) {
        Author author = (Author) modelMapper.checkEntity(authorRepository, id);
        AuthorDTO authorDTO = modelMapper.map(author, AuthorDTO.class);
        List<Author_Book> authorBookList = authorBookRepository.findAllByAuthorId(id);
        for (Author_Book author_book : authorBookList) {
            authorDTO.getBookDtoIdSet().add(author_book.getId());
        }
        return new RestMessageDTO(authorDTO, true);
    }

    @Override
    public RestMessageDTO deleteAuthorById(long id) {
        Author author = (Author) modelMapper.checkEntity(authorRepository, id);
        authorRepository.delete(author);
        return RestMessageDTO.createCorrectMessage("Successfully deleted author");
    }

    @Override
    public RestMessageDTO updateAuthor(AuthorDTO authorDTO) {
        Author author = authorRepository.findAuthorByName(authorDTO.getName());
        if (author != null) {
            return RestMessageDTO.createFailureMessage("Author already exist");
        }
        author = (Author) modelMapper.checkEntity(authorRepository, authorDTO.getId());
        author.setName(authorDTO.getName());
        author.setBorn(authorDTO.getBorn());
        author.setGender(authorDTO.getGender());
        authorRepository.save(author);
        return RestMessageDTO.createCorrectMessage("Successfully updated author");
    }

    @Override
    public RestMessageDTO addBookForAuthor(long bookId, long authorId) {
        Author author = (Author) modelMapper.checkEntity(authorRepository, authorId);
        Book book = (Book) modelMapper.checkEntity(bookRepository, bookId);
        Author_Book author_book = checkAuthorBook(bookId, authorId);
        if (author_book != null) {
            return RestMessageDTO.createFailureMessage("Author_book already exist");
        }
        author_book = new Author_Book();
        author_book.setAuthor(author);
        author_book.setBook(book);
        authorBookRepository.save(author_book);
        return RestMessageDTO.createCorrectMessage("Successfully added book for author");
    }

    @Override
    public RestMessageDTO deleteBookFromAuthor(long bookId, long authorId) {
        Author author = (Author) modelMapper.checkEntity(authorRepository, authorId);
        Author_Book author_book = checkAuthorBook(authorId, bookId);
        if (author_book == null) {
            return RestMessageDTO.createFailureMessage("Don't presented author_book");
        }
        author.getAuthorBookList().remove(author_book);
        authorBookRepository.delete(author_book);
        return RestMessageDTO.createCorrectMessage("Successfully deleted book from author");
    }

    @Override
    public RestMessageDTO addSetBooksForAuthor(ListBooksDTO listBooksDTO) {
        checkListBooksDTO(listBooksDTO);
        Author author = (Author) modelMapper.checkEntity(authorRepository, listBooksDTO.getAuthorId());
        List<Book> bookList = bookRepository.findAllById(listBooksDTO.getBooksIdList());
        Set<Book> bookSet = new HashSet<>(bookList);
        Author_Book author_book = new Author_Book();
        for (Book book : bookSet) {
            author_book = null;
            author_book = checkAuthorBook(book.getId(), author.getId());
            if (author_book != null) {
                return RestMessageDTO.createFailureMessage("Author_book already exist");
            }
            author_book = new Author_Book();
            author_book.setAuthor(author);
            author_book.setBook(book);
            authorBookRepository.save(author_book);
        }
        return RestMessageDTO.createCorrectMessage("Successfully added books for author");
    }

    @Override
    public RestMessageDTO deleteAllBooksFromAuthor(long authorId) {
        Author author = (Author) modelMapper.checkEntity(authorRepository, authorId);
        List<Author_Book> authorBookList = authorBookRepository.findAllByAuthorId(authorId);
        if (authorBookList.isEmpty()) {
            return RestMessageDTO.createFailureMessage("Books set is empty");
        }
        for (Author_Book author_book : authorBookList) {
            author.getAuthorBookList().remove(author_book);
        }
        authorRepository.save(author);
        return RestMessageDTO.createCorrectMessage("Successfully deleted all books from author");
    }

    private Author_Book checkAuthorBook(long bookId, long authorId) {
        if (bookId <= 0 || authorId <= 0) {
            throw new RuntimeException("Invalid id's value");
        }
        Author_Book author_book = authorBookRepository.findByAuthorIdAndBookId(authorId, bookId);
        return author_book;
    }

    private void checkListBooksDTO(ListBooksDTO listBooksDTO) {
        if (listBooksDTO.getBooksIdList().isEmpty()) {
            throw new RuntimeException("List of books is empty");
        }
        for (Long id : listBooksDTO.getBooksIdList()) {
            if (id <= 0) {
                throw new RuntimeException("Incorrect id value");
            }
        }
    }
}