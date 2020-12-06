package sharing.dormitory.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class DormitoryDTO implements Serializable {
    private static final long serialVersionUID = -9095800683359137225L;

    private Integer id;
    private String name;
    private String address;
    private String description;
}
