package fo.project.library.entity;

import fo.project.library.enumeration.GenreEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(columnDefinition = "VARCHAR(32)")
    private String name;

    @Column(columnDefinition = "DATE")
    private LocalDate published;

    @Enumerated(EnumType.STRING)
    private GenreEnum genre;

    private int rating;

    @ManyToMany(mappedBy = "bookSet")
    private Set<Author> authorSet;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id &&
                Objects.equals(name, book.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", published=" + published +
                ", genre=" + genre +
                ", rating=" + rating +
                '}';
    }

    public Book(String name, LocalDate published, GenreEnum genre, int rating) {
        this.name = name;
        this.published = published;
        this.genre = genre;
        this.rating = rating;
    }
}
