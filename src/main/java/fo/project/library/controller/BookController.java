package fo.project.library.controller;

import fo.project.library.dto.BookDTO;
import fo.project.library.dto.RestMessageDTO;
import fo.project.library.enumeration.GenreEnum;
import fo.project.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public RestMessageDTO createBook(@RequestBody @Valid BookDTO bookDTO) {
        return bookService.createNewBook(bookDTO);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public RestMessageDTO<BookDTO> getBook(@PathVariable("id") long id) {
        return bookService.getBookById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public RestMessageDTO deleteBook(@PathVariable("id") long id) {
        return bookService.deleteBookById(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.PATCH)
    public RestMessageDTO updateBook(@RequestBody @Valid BookDTO bookDTO) {
        return bookService.updateBook(bookDTO);
    }

    @RequestMapping(value = "/more", method = RequestMethod.GET)
    public RestMessageDTO<List<BookDTO>> moreThanOneBook() {
        return bookService.getBooksWithAuthorHaveMoreThanOneBook();
    }

    @RequestMapping(value = "/by_genre_count/{genre}", method = RequestMethod.GET)
    public RestMessageDTO getCountBooksByGenre(@PathVariable("genre") GenreEnum genreEnum) {
        return bookService.getCountBooksByGenre(genreEnum);
    }
}
