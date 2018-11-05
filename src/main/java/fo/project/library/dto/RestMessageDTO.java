package fo.project.library.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RestMessageDTO<T> {

    private T message;

    private boolean status;

    public static RestMessageDTO createFailureMessage(String message){
        return new RestMessageDTO(message, false);
    }

    public static RestMessageDTO createCorrectMessage(String message){
        return new RestMessageDTO(message,true);
    }
}
