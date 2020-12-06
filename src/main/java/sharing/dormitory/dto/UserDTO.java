package sharing.dormitory.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class UserDTO implements Serializable {
    private static final long serialVersionUID = -2652802983568657047L;

    private Integer id;
    private String username;
    private String name;
    private String surname;
    private String email;
    private String telephone;
    private String password;
    private Integer dormitory;
    private Float rating;
}
