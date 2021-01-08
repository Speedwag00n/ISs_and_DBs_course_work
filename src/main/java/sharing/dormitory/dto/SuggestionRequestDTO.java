package sharing.dormitory.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SuggestionRequestDTO implements Serializable {
    private static final long serialVersionUID = -5277159414005078198L;

    private String name;
    private String description;
    private Integer suggestionId;
    private Integer userId;
}
