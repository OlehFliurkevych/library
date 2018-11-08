package fo.project.library.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import fo.project.library.entity.Book;
import fo.project.library.enumeration.GenreEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

    private long id;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate published;

    @NotNull
    private GenreEnum genre;

    private int rating;

    private Set<Long> authorDtoIdSet = new HashSet<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookDTO bookDTO = (BookDTO) o;
        return id == bookDTO.id &&
                Objects.equals(name, bookDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "BookDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", published=" + published +
                ", genre=" + genre +
                ", rating=" + rating +
                '}';
    }

    public static Book toBook(BookDTO bookDTO) {
        return new Book(
                bookDTO.getName(),
                bookDTO.getPublished(),
                bookDTO.getGenre(),
                bookDTO.getRating()
        );
    }
}
