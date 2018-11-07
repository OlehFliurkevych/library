package fo.project.library.comparator;

import fo.project.library.dto.AuthorDTO;

import java.util.Comparator;

public class AuthorDTOByBornComparatorASC implements Comparator<AuthorDTO> {

    @Override
    public int compare(AuthorDTO author1, AuthorDTO author2) {
        if (author1.getBorn().isBefore(author2.getBorn())) {
            return 1;
        } else if (author1.getBorn().isAfter(author2.getBorn())) {
            return -1;
        }
        return 0;
    }
}
