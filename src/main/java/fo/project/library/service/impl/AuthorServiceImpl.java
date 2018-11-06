package fo.project.library.service.impl;

import fo.project.library.dto.AuthorDTO;
import fo.project.library.dto.ListBooksDTO;
import fo.project.library.dto.RestMessageDTO;
import fo.project.library.entity.Author;
import fo.project.library.entity.Book;
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
        author.getBookSet().add(book);
        authorRepository.save(author);
        return RestMessageDTO.createCorrectMessage("Successfully added book for author");
    }

    @Override
    public RestMessageDTO deleteBookFromAuthor(long bookId, long authorId) {
        Author author = (Author) modelMapper.checkEntity(authorRepository, authorId);
        Book book = (Book) modelMapper.checkEntity(bookRepository, bookId);
        author.getBookSet().remove(book);
        authorRepository.save(author);
        return RestMessageDTO.createCorrectMessage("Successfully deleted book from author");
    }

    @Override
    public RestMessageDTO addSetBooksForAuthor(ListBooksDTO listBooksDTO) {
        if (listBooksDTO.getBooksIdList().isEmpty()) {
            return RestMessageDTO.createFailureMessage("List of books is empty");
        }
        for (Long id : listBooksDTO.getBooksIdList()) {
            if (id <= 0){
                return RestMessageDTO.createFailureMessage("Incorrect id value");
            }
        }
        Author author = (Author) modelMapper.checkEntity(authorRepository, listBooksDTO.getAuthorId());
        List<Book> bookList = bookRepository.findAllById(listBooksDTO.getBooksIdList());
        Set<Book> bookSet = new HashSet<>(bookList);
        for (Book book : bookSet) {
            author.getBookSet().add(book);
        }
        authorRepository.save(author);
        return RestMessageDTO.createCorrectMessage("Successfully added books for author");
    }

    @Override
    public RestMessageDTO deleteAllBooksFromAuthor(long authorId) {
        Author author = (Author) modelMapper.checkEntity(authorRepository, authorId);
        Set<Book> bookSet = author.getBookSet();
        if (bookSet.isEmpty()) {
            return RestMessageDTO.createFailureMessage("Books set is empty");
        }
        author.getBookSet().removeAll(bookSet);
        authorRepository.save(author);
        return RestMessageDTO.createCorrectMessage("Successfully deleted all books from author");
    }
}

