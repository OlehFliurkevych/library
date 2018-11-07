package fo.project.library.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
public class DateUtils {

    public int getAgeByLocalDate(LocalDate birthDate) {
        if ((birthDate == null)) {
            throw new RuntimeException("Birthday date is null.");
        }
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

}
