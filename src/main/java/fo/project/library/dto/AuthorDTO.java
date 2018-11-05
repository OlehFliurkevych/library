package fo.project.library.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import fo.project.library.entity.Author;
import fo.project.library.enumeration.GenderEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDTO {

    private long id;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    private GenderEnum gender;

    @NotNull
    @Past
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate born;

    private Set<Long> bookDtoIdSet;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorDTO authorDTO = (AuthorDTO) o;
        return id == authorDTO.id &&
                Objects.equals(name, authorDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "AuthorDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", born=" + born +
                '}';
    }

    public static Author toAuthor(AuthorDTO authorDTO){
        return new Author(
               authorDTO.getName(),
               authorDTO.getGender(),
                authorDTO.getBorn()
        );
    }
}
