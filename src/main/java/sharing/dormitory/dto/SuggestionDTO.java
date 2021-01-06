package sharing.dormitory.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SuggestionDTO implements Serializable {
    private static final long serialVersionUID = -2652802983568657047L;

    private String name;
    private String description;
    private Integer object;
    private Integer service;
}
