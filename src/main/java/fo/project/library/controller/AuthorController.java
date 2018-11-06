package fo.project.library.controller;

import fo.project.library.dto.AuthorDTO;
import fo.project.library.dto.ListBooksDTO;
import fo.project.library.dto.RestMessageDTO;
import fo.project.library.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public RestMessageDTO createAuthor(@RequestBody @Valid AuthorDTO authorDTO) {
        return authorService.createNewAuthor(authorDTO);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public RestMessageDTO<AuthorDTO> getAuthor(@PathVariable("id") long id) {
        return authorService.getAuthorById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public RestMessageDTO deleteAuthor(@PathVariable("id") long id) {
        return authorService.deleteAuthorById(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.PATCH)
    public RestMessageDTO updateAuthor(@RequestBody @Valid AuthorDTO authorDTO) {
        return authorService.updateAuthor(authorDTO);
    }

    @RequestMapping(value = "/{authorId}/books/{bookId}", method = RequestMethod.POST)
    public RestMessageDTO addBookForAuthor(@PathVariable("authorId") long authorId, @PathVariable("bookId") long bookId) {
        return authorService.addBookForAuthor(bookId, authorId);
    }

    @RequestMapping(value = "/{authorId}/books/{bookId}", method = RequestMethod.DELETE)
    public RestMessageDTO deleteBookFromAuthor(@PathVariable("authorId") long authorId, @PathVariable("bookId") long bookId) {
        return authorService.deleteBookFromAuthor(bookId, authorId);
    }

    @RequestMapping(value = "/{id}/books", method = RequestMethod.DELETE)
    public RestMessageDTO deleteAllBooksFromAuthor(@PathVariable("id") long id) {
        return authorService.deleteAllBooksFromAuthor(id);
    }

    @RequestMapping(value = "/{id}/books", method = RequestMethod.POST)
    public RestMessageDTO addSetBooksForAuthor(@RequestBody @Valid ListBooksDTO listBooksDTO) {
        return authorService.addSetBooksForAuthor(listBooksDTO);
    }


}

